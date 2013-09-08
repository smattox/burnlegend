package com.mobius.legend.math;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class SubtractAttribute implements IValue {

	private Attribute attribute;
	
	public SubtractAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender, int current) {
		return current - defender.getAttribute(attribute);
	}
	
	@Override
	public String toString() {
		return "- opponent's " + attribute.toString();
	}

	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public IValue cloneValue() {
		return new SubtractAttribute(attribute);
	}

	@Override
	public boolean stacks(IValue amount) {
		return false;
	}
	
}
