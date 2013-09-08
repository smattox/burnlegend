package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;

public class OnDamageCondition extends AbstractCondition {
	
	private final int amount;
	
	public OnDamageCondition(int amount, ISpecialEffect[] effects) {
		super(effects);
		this.amount = amount;
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		if (BattleEngine.getInstance().getCurrentRound().getDamageSuffered(defender) >= amount) {
			return apply(defender, null);
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnDamageCondition(amount, cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On " + amount + "+ Damage: ";
	}
}
