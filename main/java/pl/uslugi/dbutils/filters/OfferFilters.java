package pl.uslugi.dbutils.filters;

import pl.uslugi.enums.NotificationDisplayStatus;

public class OfferFilters {
	private Long offerUserId;
	private Long auctionId;
	private NotificationDisplayStatus display_status;
	
	private OfferFilters(OfferFiltersBuilder builder)
	{
		this.offerUserId = builder.offerUserId;
		this.auctionId = builder.auctionId;
		this.display_status = builder.display_status;
	}
	
	public static class OfferFiltersBuilder
	{
		private Long offerUserId;
		private Long auctionId;
		private NotificationDisplayStatus display_status;
		
		public OfferFiltersBuilder offerUserId(Long offerUserId)
		{
			this.offerUserId = offerUserId;
			return this;
		}
		
		public OfferFiltersBuilder auctionId(Long auctionId)
		{
			this.auctionId =auctionId;
			return this;
		}	
		
		public OfferFiltersBuilder displayStatus(NotificationDisplayStatus displayStatus)
		{
			this.display_status = displayStatus;
			return this;
		}	
		
		public OfferFilters build()
		{
			return new OfferFilters(this);
		}
	}
	
	public Long getOfferUserId() {
		return offerUserId;
	}
	
	public Long getAuctionId() {
		return auctionid;
	}

	public NotificationDisplayStatus getDisplay_status() {
		return display_status;
	}
	
}
