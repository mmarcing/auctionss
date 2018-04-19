package pl.uslugi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.uslugi.domain.Offer;


@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>, OfferRepositoryCustom{
	Optional<Offer> findById(Long id);
	List<Offer> findByAuction_id(Long id);
	
	@Transactional(readOnly = true)
	@Query("select count(o.id) from Offer o where o.auction_id = ?1 and o.display_status = 0") 
	Long getNewOffersCountForAuction(Long auctionId);
}
