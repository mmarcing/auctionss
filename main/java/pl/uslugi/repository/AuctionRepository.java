package pl.uslugi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.uslugi.domain.auction.Auction;


@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>{
	Optional<Auction> findById(Long id);
	List<Auction> findByUserId(Long userId); 
	Page<Auction> findAll(Pageable pageable);
	Page<Auction> findByTopicContaining(String query, Pageable pageable);
	
}
