package pl.uslugi.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pl.uslugi.bo.provision.Provision;
import pl.uslugi.bo.provision.ProvisionCalculator;
import pl.uslugi.bo.provision.ProvisionCalculatorAbstract;
import pl.uslugi.bo.provision.ProvisionCalculatorDecoratorPayU;
import pl.uslugi.bo.provision.ProvisionCalculatorDecoratorPromotion;
import pl.uslugi.component.Utils;
import pl.uslugi.domain.ConflictMessage;
import pl.uslugi.domain.Offer;
import pl.uslugi.domain.Pocket;
import pl.uslugi.domain.auction.Auction;
import pl.uslugi.domain.auction.AuctionObject;
import pl.uslugi.domain.auction.IAuctionObject;
import pl.uslugi.dto.NotificationDto;
import pl.uslugi.dto.NotificationMapper;
import pl.uslugi.enums.AuctionObjectType;
import pl.uslugi.enums.LogLevel;
import pl.uslugi.enums.OfferStatus;
import pl.uslugi.service.AuctionService;
import pl.uslugi.service.LoggerSerivce;
import pl.uslugi.service.OfferService;
import pl.uslugi.service.ProvisionService;
import pl.uslugi.service.SecurityService;
import pl.uslugi.service.UserService;
import pl.uslugi.validator.AuctionValidator;

@Controller
@RequestMapping("/aukcje")
public class AuctionController {
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private OfferService offerService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private LoggerSerivce loggerService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProvisionService provisionSerivce;


	final long FILE_SIZE_LIMIT = 10 * 1024 * 1024; // 10mb

	@GetMapping
	public String getMyAuctionsList(Model model) {
		List<Auction> auctions =  auctionService.findByUserId(securityService.getLoggedUserId());
		auctions.sort(Comparator.comparing(Auction::getCreateDate).reversed());
		model.addAttribute("auctions", auctions);
		return "myAuctions";
	}

	@Transactional
	@GetMapping("/aukcja/{id}")
	public String getAuction(@PathVariable long id, Model model) {
		Optional<Auction> auction = auctionService.findById(id);
		if (auction.isPresent()) {
			model.addAttribute("auction", auction.get());

			// **
			List<String> auctionObjDescriptions = new ArrayList<String>();
			auction.get().getAuctionObjects().forEach(a -> auctionObjDescriptions.add(a.createDescription()));
			// **

			return "selectedAuction";
		}
		return "protected";
	}

	private List<ConflictMessage> getConflictMessagesFromAuctionToWhichUserHasPermissions(Auction auction,
			Long userId) {
		List<ConflictMessage> confMessages = new ArrayList<>();
		if (userId != null) {
			auction.getOffers()
					.forEach(of -> of.getConflicts().forEach(conf -> confMessages.addAll(conf.getConflictMessages())));
		}
		return confMessages;
	}

	@PostMapping(value = "/dodaj")
	public String addAuction(@ModelAttribute("auctionForm") Auction auction, BindingResult bindingResult, Model model,
			HttpServletRequest request) {
		AuctionValidator auctionValidator = new AuctionValidator();
		auctionValidator.validate(auction, bindingResult);

		if (bindingResult.hasErrors()) {
			return "addAuction";
		} else {
			auction.setUser(userService.findById(securityService.getLoggedUserId()));
			Auction newAuction = auctionService.create(auction);

			return "redirect:/aukcje/aukcja/" + newAuction.getId();
		}
	}

	@Transactional
	@GetMapping("/zakoncz/{id}")
	public @ResponseBody String finishAuction(@PathVariable long id) {
		Optional<Auction> auction = auctionService.findById(id);
		if (auction.isPresent()) {
			// ...
			ProvisionCalculatorAbstract provCalc = provisionSerivce.createProvisionCalculator(AuctionObjectType.product); 
			provCalc = new ProvisionCalculatorDecoratorPayU(provCalc); 
			provCalc = new ProvisionCalculatorDecoratorPromotion(provCalc); 
			Provision provision = new Provision(provCalc);
			BigDecimal prov = provision.calculate(auction.get().getAmount());
			//..
			auctionService.finishAuction(auction.get());
			// ...
		}
		return "error";
	}

	@Transactional
	@GetMapping(value = "/noweaukcje/{username}", produces = "application/json")
	public @ResponseBody List<NotificationDto> getNewAuctions(@PathVariable String username) {
		if (securityService.getLoggedUserUserName().equals(username)) {
			List<NotificationDto> newOffers = NotificationMapper
					.map(offerService.findOtherThanReadedOffersForUser(securityService.getLoggedUserId()));

			if (newOffers.stream().filter(n -> n.getViewed() == false).findAny().isPresent()) {
				//..
				offerService.setDisplayStatusForNewUserOffers(NotificationDisplayStatus.VIEWED, securityService.getLoggedUserId());
			}
			return newOffers;
		} else {
			// ...
		}
		return new ArrayList<NotificationDto>();
	}

	@RequestMapping(value = "/dodajObraz", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> addImage(@RequestBody MultipartFile file) throws Exception {
		if (file.getSize() > FILE_SIZE_LIMIT) {
			throw new Exception("Maksymalny rozmiar pliku to " + String.valueOf(FILE_SIZE_LIMIT / 1024 / 1024) + " mb");
		}
		Map<String, String> result_map = new HashMap<String, String>();
		try {
			if (!file.isEmpty()) {
				String uploadsDir = "/uploads/auc/";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new File(realPathtoUploads).exists()) {
					new File(realPathtoUploads).mkdir();
				}

				String fileName = securityService.getLoggedUserId().toString() + System.currentTimeMillis();
				String extension = file.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
				String filePath = realPathtoUploads + fileName + "." + extension;
				File dest = new File(filePath);

				file.transferTo(dest);
				Utils.compresAndRotateUpImage(filePath, filePath);

				result_map.put("filename", fileName + "." + extension);
				return result_map;
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		result_map.put("filename", "");
		return result_map;
	}

}
