package pl.uslugi.repository;

import java.util.List;

import pl.uslugi.databaseutils.filters.OfferFilters;
import pl.uslugi.databaseutils.orders.OfferOrder;
import pl.uslugi.domain.Offer;
import pl.uslugi.enums.NotificationDisplayStatus;

public interface OfferRepositoryCustom {

	List<Offer> findOffers(OfferFilters filters, OfferOrder orders);

	int setDisplayStatus(NotificationDisplayStatus status, OfferFilters filters);
}
