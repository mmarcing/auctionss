package pl.uslugi.bo.provision;

import java.math.BigDecimal;

public class Provision {
	ProvisionCalculatorAbstract calculator = null;

	public Provision(ProvisionCalculatorAbstract calculator) {
		super();
		this.calculator = calculator;
	};
	
	public BigDecimal calculate(BigDecimal amount)
	{
		return calculator.calculate(amount);
	}
}
