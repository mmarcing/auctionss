package pl.uslugi.domain.auction;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.uslugi.enums.AuctionObjectType;
import pl.uslugi.enums.AuctionCategory;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AuctionObject implements IAuctionObject {
	private Long id;
	private Auction auction;
	private AuctionObjectType type;
	protected AuctionCategory category;
	protected String name;
	protected String contents;
	private LocalDateTime createDate;

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
	@Column(name = "type_id")
	@Enumerated(EnumType.ORDINAL)
	public AuctionObjectType getType() {
		return type;
	}

	public void setType(AuctionObjectType type) {
		this.type = type;
	}

	@NotNull
	@Column(name = "category_id")
	@Enumerated(EnumType.ORDINAL)
	public AuctionCategory getCategory() {
		return category;
	}

	public void setCategory(AuctionCategory category) {
		this.category = category;
	}

	@NotNull
	@Column(length = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	@Column(name = "contents")
	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

}