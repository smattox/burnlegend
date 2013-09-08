package com.mobius.legend.math;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public interface IValue {
	int evaluate(ICharacter attacker, Technique technique, ICharacter defender, int current);

	IValue cloneValue();

	boolean stacks(IValue check);
}
