package pl.uslugi.domain.auction;

import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class AuctionObjectService extends AuctionObject {
	private LocalDate dateFrom;
	private LocalDate dateTo;
	private String placeOfService;

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public String getPlaceOfService() {
		return placeOfService;
	}

	public void setPlaceOfService(String placeOfService) {
		this.placeOfService = placeOfService;
	}

	@Override
	public String createDescription() {
		return category.getName() + " - " + name + placeOfService != null ? " [" + placeOfService + "]" : "";
	}

}