package pl.uslugi.domain.auction;

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

import pl.uslugi.domain.Offer;
import pl.uslugi.domain.User;
import pl.uslugi.enums.AuctionStatus;

@Entity
@Table(name = "auction")
public class Auction {
	private Long id;
	private User user;
	private AuctionStatus status;
	private String topic;
	private BigDecimal amount;
	private BigDecimal provision = BigDecimal.ZERO;
	private LocalDateTime createDate;
	private Boolean editable = true;
	private Set<AuctionObject> auctionObjects;
	@JsonIgnore
	private Set<Offer> offers;

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
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	@Column(name = "status_id")
	@Enumerated(EnumType.ORDINAL)
	public AuctionStatus getStatus() {
		return status;
	}

	public void setStatus(AuctionStatus status) {
		this.status = status;
	}

	@NotNull
	@Column(length = 255)
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@NotNull
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getProvision() {
		return provision;
	}

	public void setProvision(BigDecimal provision) {
		this.provision = provision;
	}

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	@OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
	public Set<AuctionObject> getAuctionObjects() {
		return auctionObjects;
	}

	public void setAuctionObjects(Set<AuctionObject> auctionObjects) {
		this.auctionObjects = auctionObjects;
	}

	@OneToMany(mappedBy = "auction", fetch = FetchType.LAZY)
	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

}