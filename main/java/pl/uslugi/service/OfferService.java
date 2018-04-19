package pl.uslugi.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OfferServiceImpl implements OfferService {
	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private AuctionService auctionService;

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	
	@Override
	public void setOffersAsViewedForUser(Long userId) {
		OfferFilters of = new OfferFilters.OfferFiltersBuilder().displayStatus(NotificationDisplayStatus.NEW)
				.taskUserId(userId).build();
		offerRepository.setDisplayStatus(NotificationDisplayStatus.VIEWED, of);
	}

	
}