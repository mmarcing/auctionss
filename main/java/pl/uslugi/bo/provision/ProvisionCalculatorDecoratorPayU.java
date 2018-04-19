package pl.uslugi.bo.provision;

import java.math.BigDecimal;

public class ProvisionCalculatorDecoratorPayU extends ProvisionCalculatorDecoratorAbstract {

	public ProvisionCalculatorDecoratorPayU(ProvisionCalculatorAbstract provisionCalculator) {
		super(provisionCalculator);
	}

	@Override
	public BigDecimal calculate(BigDecimal amount) {
		return provisionCalculator.calculate(amount).add(amount.multiply(BigDecimal.valueOf(0.02)));
	}
}
