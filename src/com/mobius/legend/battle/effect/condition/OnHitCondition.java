package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;

public class OnHitCondition extends AbstractCondition {
	
	public OnHitCondition(ISpecialEffect[] effects) {
		super(effects);
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		return apply(defender, null);
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnHitCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On Hit: ";
	}
}
