package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;

public class OnDistantCondition extends AbstractCondition {

	public OnDistantCondition(ISpecialEffect[] effects) {
		super(effects);
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnDistantCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On Distant: ";
	}

}
