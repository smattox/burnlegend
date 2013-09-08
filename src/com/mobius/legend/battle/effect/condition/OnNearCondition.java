package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;

public class OnNearCondition extends AbstractCondition {

	public OnNearCondition(ISpecialEffect[] effects) {
		super(effects);
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnNearCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "When Near: ";
	}

}
