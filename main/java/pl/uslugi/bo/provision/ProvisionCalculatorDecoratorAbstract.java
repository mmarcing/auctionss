package pl.uslugi.bo.provision;

import java.math.BigDecimal;

public abstract class ProvisionCalculatorDecoratorAbstract extends ProvisionCalculatorAbstract{
	protected ProvisionCalculatorAbstract provisionCalculator;
	
	public ProvisionCalculatorDecoratorAbstract(ProvisionCalculatorAbstract provisionCalculator) {
		this.provisionCalculator = provisionCalculator;
	}
	
	@Override
	public abstract BigDecimal calculate(BigDecimal amount);

}
