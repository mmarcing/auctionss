package pl.uslugi.domain.auction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.drew.lang.annotations.NotNull;

import pl.uslugi.enums.AuctionProductCondition;

@Entity
public class AuctionObjectProduct extends AuctionObject {
	private AuctionProductCondition condition;
	private Integer weight;
	private Integer sizeX;
	private Integer sizeY;
	private Integer sizeZ;

	@NotNull
	@Column(name = "condition_id")
	@Enumerated(EnumType.ORDINAL)
	public AuctionProductCondition getCondition() {
		return condition;
	}

	public void setCondition(AuctionProductCondition condition) {
		this.condition = condition;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getSizeX() {
		return sizeX;
	}

	public void setSizeX(Integer sizeX) {
		this.sizeX = sizeX;
	}

	public Integer getSizeY() {
		return sizeY;
	}

	public void setSizeY(Integer sizeY) {
		this.sizeY = sizeY;
	}

	public Integer getSizeZ() {
		return sizeZ;
	}

	public void setSizeZ(Integer sizeZ) {
		this.sizeZ = sizeZ;
	}

	@Override
	public String createDescription() {
		return category.getName() + " - " + name + "[" + condition.getName() + "]";
	}

}