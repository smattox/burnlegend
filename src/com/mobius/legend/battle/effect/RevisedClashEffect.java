package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.math.IFormula;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Technique;

public class RevisedClashEffect extends AbstractCurrentRoundEffect {

	private final IFormula newFormula;
	
	public RevisedClashEffect(IFormula newFormula) {
		this.newFormula = newFormula;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		MutableTechnique t1Mutable = (MutableTechnique)t1;
		t1Mutable.setClashFormula(newFormula);
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new RevisedClashEffect(newFormula);
	}
	
	public String getTechniqueDisplayString() {
		return "Clash becomes " + newFormula.toString();
	}

}
