package pl.uslugi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.uslugi.databaseutils.filters.OfferFilters;
import pl.uslugi.databaseutils.orders.OfferOrder;
import pl.uslugi.domain.Offer;
import pl.uslugi.enums.NotificationDisplayStatus;

public class OfferRepositoryImpl implements OfferRepositoryCustom {
	@PersistenceContext
	private EntityManager em;

	@Override
	public int setDisplayStatus(NotificationDisplayStatus status, OfferFilters filters) {
		Query query = em
				.createQuery("UPDATE Offer o SET o.display_status = " + status.getOrdinal() + buildWhere(filters));
		return query.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Offer> findOffers(OfferFilters filters, OfferOrder orders) {
		return (List<Offer>) em.createQuery("SELECT o FROM Offer o " + buildWhere(filters) + buildOrder(orders))
				.getResultList();
	}

}
