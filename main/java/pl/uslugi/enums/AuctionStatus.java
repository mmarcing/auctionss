package pl.uslugi.enums;

import java.util.Arrays;
import java.util.List;

public enum AuctionStatus implements IStandardEnum{
	NEW("Nowa"),
	FINISHED("Zakończona"),
	CANCELED("Anulowana");

	private String name;
	
	private AuctionStatus(String name)
	{
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getOrdinal() {
		return this.ordinal();
	}
	
	@Override
	public List<IStandardEnum> getValues() {
		return Arrays.asList(AuctionStatus.values());
	}
}
