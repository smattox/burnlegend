package com.mobius.legend.technique.upgrade;

import com.mobius.legend.technique.MutableTechnique;

public class DamageUpgrade implements ITechniqueUpgrade {

	private final int amount;
	
	public DamageUpgrade(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void applyToTechnique(MutableTechnique technique) {
		technique.getDamagePool().addConstant(amount);
	}

}
