package com.mobius.legend.math;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class DevilJudgementMinValue implements IValue{

	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		return attacker.getAttribute(Attribute.Dexterity) + 3;
	}
	
	@Override
	public String toString() {
		return "(Min Dexterity + 3)";
	}

	@Override
	public IValue cloneValue() {
		return new DevilJudgementMinValue();
	}

	@Override
	public boolean stacks(IValue amount) {
		return false;
	}
}
