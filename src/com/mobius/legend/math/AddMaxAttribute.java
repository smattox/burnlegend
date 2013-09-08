package com.mobius.legend.math;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class AddMaxAttribute implements IValue {

	private final Attribute attribute1;
	private final Attribute attribute2;
	
	public AddMaxAttribute(Attribute attribute1, Attribute attribute2) {
		this.attribute1 = attribute1;
		this.attribute2 = attribute2;
	}
	
	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender, int current) {
		return current + Math.max(attacker.getAttribute(attribute1), attacker.getAttribute(attribute2));
	}
	
	@Override
	public String toString() {
		return "+ Max(" + attribute1.toString() + ", " + attribute2.toString() + ")";
	}

	public Attribute getAttribute() {
		return attribute1;
	}

	@Override
	public IValue cloneValue() {
		return new AddMaxAttribute(attribute1, attribute2);
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}
	
}
