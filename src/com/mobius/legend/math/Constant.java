package com.mobius.legend.math;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class Constant implements IValue {
	private int amount;
	
	public Constant(int amount) {
		this.amount = amount;
	}

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		return current + amount;
	}
	
	@Override
	public String toString() {
		return (amount >= 0 ? "+ " : "- ") + Math.abs(amount);
	}

	@Override
	public IValue cloneValue() {
		return new Constant(amount);
	}

	@Override
	public boolean stacks(IValue check) {
		if (check instanceof Constant) {
			amount += ((Constant)check).amount;
			return true;
		}
		return false;
	}
}
