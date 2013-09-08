package com.mobius.legend.technique.upgrade;

import com.mobius.legend.technique.MutableTechnique;

public class ClashUpgrade implements ITechniqueUpgrade {

	private final int amount;
	
	public ClashUpgrade(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void applyToTechnique(MutableTechnique technique) {
		technique.getClashPool().addConstant(amount);
	}

}
