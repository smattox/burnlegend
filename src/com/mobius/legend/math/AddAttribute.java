package com.mobius.legend.math;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class AddAttribute implements IValue {

	private final Attribute attribute;
	
	public AddAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender, int current) {
		return current + attacker.getAttribute(attribute);
	}
	
	@Override
	public String toString() {
		return "+ " + attribute.toString();
	}

	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public IValue cloneValue() {
		return new AddAttribute(attribute);
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}
	
}
