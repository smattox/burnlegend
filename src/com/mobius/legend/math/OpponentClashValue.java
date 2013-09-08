package com.mobius.legend.math;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class OpponentClashValue implements IValue {

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		return BattleEngine.getInstance().getCurrentRound().getClashSuccesses(defender);
	}
	
	@Override
	public String toString() {
		return "  opponent's clash successes";
	}

	@Override
	public IValue cloneValue() {
		return new OpponentClashValue();
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}

}
