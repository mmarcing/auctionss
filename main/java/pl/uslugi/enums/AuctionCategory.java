package pl.uslugi.enums;

import java.util.Arrays;
import java.util.List;

public enum AuctionCategory implements IStandardEnum {
	fashion("Moda"), beauty("Uroda");

	private String name;

	private AuctionCategory(String name) {
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
		return Arrays.asList(AuctionCategory.values());
	}
}
