package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.math.IFormula;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Technique;

public class RevisedDamageEffect extends AbstractCurrentRoundEffect {

	private final IFormula newFormula;
	
	public RevisedDamageEffect(IFormula newFormula) {
		this.newFormula = newFormula;
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique t1) {
		MutableTechnique t1Mutable = (MutableTechnique)t1;
		t1Mutable.setDamageFormula(newFormula);
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new RevisedDamageEffect(newFormula);
	}
	
	public String getTechniqueDisplayString() {
		return "Damage becomes " + newFormula.toString();
	}

}
