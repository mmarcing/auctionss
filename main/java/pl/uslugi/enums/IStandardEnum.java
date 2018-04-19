package pl.uslugi.enums;

import java.util.List;

public interface IStandardEnum {
	String getName();
	int getOrdinal();
	List<IStandardEnum> getValues();
}
