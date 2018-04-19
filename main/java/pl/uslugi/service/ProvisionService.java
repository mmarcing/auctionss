package pl.uslugi.service;

import pl.uslugi.bo.provision.ProvisionCalculatorAbstract;
import pl.uslugi.enums.AuctionObjectType;

public interface ProvisionService {
	ProvisionCalculatorAbstract createProvisionCalculator(AuctionObjectType auctionObjType);
}
