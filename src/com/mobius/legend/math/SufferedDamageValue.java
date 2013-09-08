package com.mobius.legend.math;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class SufferedDamageValue implements IValue {

	@Override
	public int evaluate(ICharacter attacker, Technique technique,
			ICharacter defender, int current) {
		return BattleEngine.getInstance().getCurrentRound().getDamageSuffered(attacker);
	}

	@Override
	public IValue cloneValue() {
		return new SufferedDamageValue();
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}

}
