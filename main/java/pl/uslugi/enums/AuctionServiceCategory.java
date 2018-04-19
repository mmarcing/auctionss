package pl.uslugi.enums;

import java.util.Arrays;
import java.util.List;

public enum AuctionServiceCategory implements IStandardEnum {
	it("Informatyka"),
	mechanics("Mechanika pojazdowa");

	private String name;

	private AuctionServiceCategory(String name) {
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
		return Arrays.asList(AuctionServiceCategory.values());
	}
}
