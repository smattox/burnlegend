package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;

public class OnRemoveHealthStockCondition extends AbstractCondition {

	public OnRemoveHealthStockCondition(ISpecialEffect[] effects) {
		super(effects);
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		if (defender.getStatus().hasLostHealthStockThisRound()) {
			return apply(defender, null);
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnRemoveHealthStockCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On remove Health Stock: ";
	}

}
