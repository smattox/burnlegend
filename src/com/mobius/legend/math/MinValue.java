package com.mobius.legend.math;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class MinValue implements IValue{
	private final int amount;
	
	public MinValue(int amount) {
		this.amount = amount;
	}

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		return Math.max(current, amount);
	}
	
	@Override
	public String toString() {
		return "(Min " + amount + ")";
	}

	@Override
	public IValue cloneValue() {
		return new MinValue(amount);
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}
}
