package pl.uslugi.bo.provision;

import java.math.BigDecimal;

public abstract class ProvisionCalculatorAbstract {
	public abstract BigDecimal calculate(BigDecimal amount);
}
