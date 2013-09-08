package com.mobius.legend.math;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public interface IFormula extends Cloneable {
	public int evaluate(ICharacter attacker, ICharacter defender);
	
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender);

	public void addConstant(int amount);

	public Attribute getBaseAttribute();

	public Attribute getResistAttribute();
	
	public IFormula cloneFormula();
}
