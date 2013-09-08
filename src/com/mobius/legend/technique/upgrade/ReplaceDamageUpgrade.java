package com.mobius.legend.technique.upgrade;

import com.mobius.legend.math.IFormula;
import com.mobius.legend.technique.MutableTechnique;

public class ReplaceDamageUpgrade implements ITechniqueUpgrade {

	private IFormula newDamage;
	
	public ReplaceDamageUpgrade(IFormula formula) {
		newDamage = formula;
	}
	
	@Override
	public void applyToTechnique(MutableTechnique technique) {
		technique.setDamageFormula(newDamage);
	}
}
