package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;

public class OnVictoryCondition extends AbstractCondition {
	
	public OnVictoryCondition(ISpecialEffect[] effects) {
		super(effects);
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		return apply(original, null);
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnVictoryCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On Victory: ";
	}
}
