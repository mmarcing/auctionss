package pl.uslugi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.uslugi.domain.auction.Auction;
import pl.uslugi.domain.interfaces.Notifiable;
import pl.uslugi.enums.NotificationDisplayStatus;
import pl.uslugi.enums.OfferStatus;

@Entity
@Table(name = "offer")
public class Offer implements Notifiable {

	private Long id;

	private Auction auction;

	private User user;

	private BigDecimal amount;

	private LocalDateTime createDate;

	private OfferStatus status;

	private NotificationDisplayStatus display_status = NotificationDisplayStatus.NEW;

	@JsonIgnore
	private Set<Solution> solutions;

	@JsonIgnore
	private Set<Transaction> transactions;

	
	@JsonIgnore
	private Set<Conflict> conflicts;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	@NotNull
	@Column(name = "status_id")
	@Enumerated(EnumType.ORDINAL)
	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
	public Set<Solution> getSolutions() {
		return solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@NotNull
	public NotificationDisplayStatus getDisplay_status() {
		return display_status;
	}

	public void setDisplay_status(NotificationDisplayStatus display_status) {
		this.display_status = display_status;
	}

	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	@OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
	public Set<Conflict> getConflicts() {
		return conflicts;
	}

	public void setConflicts(Set<Conflict> conflicts) {
		this.conflicts = conflicts;
	}
}