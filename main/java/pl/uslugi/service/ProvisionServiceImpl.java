package pl.uslugi.service;

import org.springframework.stereotype.Service;

import pl.uslugi.bo.provision.ProvisionCalculatorAbstract;
import pl.uslugi.bo.provision.ProvisionCalculatorProduct;
import pl.uslugi.bo.provision.ProvisionCalculatorService;
import pl.uslugi.enums.AuctionObjectType;

@Service
public class ProvisionServiceImpl implements ProvisionService {

	@Override
	public ProvisionCalculatorAbstract createProvisionCalculator(AuctionObjectType auctionObjType) {
		switch (auctionObjType) {
		case product:
			return new ProvisionCalculatorProduct();
		case service:
			return new ProvisionCalculatorService();
		}
		return null;
	}

}
