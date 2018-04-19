package pl.uslugi.enums;

import java.util.Arrays;
import java.util.List;

public enum AuctionProductCondition implements IStandardEnum {
	NEW("Nowy"), USED("Używany");

	private String name;

	private AuctionProductCondition(String name) {
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
		return Arrays.asList(AuctionProductCondition.values());
	}
}
