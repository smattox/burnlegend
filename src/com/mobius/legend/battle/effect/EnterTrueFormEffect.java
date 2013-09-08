package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class EnterTrueFormEffect extends AbstractEffect {
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.setOkamiForm(true);
		return null;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new EnterTrueFormEffect();
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Enters True Form Instantly";
	}
}
