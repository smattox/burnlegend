package com.mobius.legend.battle.effect.base;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.math.IValue;

public abstract class AbstractCurrentRoundStackableEffect extends AbstractCurrentRoundEffect {
	protected IValue amount;
	
	protected AbstractCurrentRoundStackableEffect(IValue amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (getClass().isInstance(effect) && amount.stacks(((AbstractCurrentRoundStackableEffect)effect).amount)) {
			return false;
		}
		return true;
	}

}
