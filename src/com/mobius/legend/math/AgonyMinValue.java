package com.mobius.legend.math;

import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class AgonyMinValue implements IValue{

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		int amount = 1;
		CharacterStatus status = defender.getStatus();
		amount = status.getStartingStocks() - status.getStocks() >= 2 ? 2 : amount;
		amount = status.getStartingStocks() - status.getStocks() >= 4 ? 3 : amount;
		return Math.max(current, amount);
	}
	
	@Override
	public String toString() {
		return "(Min 1/2/3)";
	}

	@Override
	public IValue cloneValue() {
		return new AgonyMinValue();
	}

	@Override
	public boolean stacks(IValue amount) {
		return false;
	}
}
