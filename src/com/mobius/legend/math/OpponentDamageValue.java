package com.mobius.legend.math;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class OpponentDamageValue implements IValue {

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		ChosenAction action = BattleEngine.getInstance().getActionForCharacter(defender);
		return action.getTechnique().getDamagePool().evaluate(defender, attacker);
	}
	
	@Override
	public String toString() {
		return "  opponent's damage";
	}

	@Override
	public IValue cloneValue() {
		return new OpponentDamageValue();
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}

}
