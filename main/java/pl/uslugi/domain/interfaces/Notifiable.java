package pl.uslugi.domain.interfaces;

import pl.uslugi.enums.NotificationDisplayStatus;
import pl.uslugi.domain.auction.Auction;

public interface Notifiable {
	public Auction getAuction();

	public NotificationDisplayStatus getDisplay_status();
}
