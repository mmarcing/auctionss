package pl.uslugi.bo.provision;

import java.math.BigDecimal;

public class ProvisionCalculatorProduct extends ProvisionCalculatorAbstract  {

	@Override
	public BigDecimal calculate(BigDecimal amount) {
		return  amount.multiply(BigDecimal.valueOf(1.07));
	}

}
