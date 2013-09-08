package com.mobius.legend.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class StaticFormula implements IFormula {
	private List<IValue> values = new ArrayList<IValue>();
	
	public StaticFormula(IValue... values) {
		this.values.addAll(Arrays.asList(values));
	}

	@Override
	public int evaluate(ICharacter attacker, ICharacter defender) {
		return evaluate(attacker, null, defender);
	}
	
	@Override
	public int evaluate(ICharacter attacker, Technique technique,
			ICharacter defender) {
		int currentValue = 0;
		for (IValue value : values) {
			currentValue = value.evaluate(attacker, technique, defender, currentValue);
		}
		return currentValue;
	}
	
	@Override
	public void addConstant(int amount) {
		for (int i = 0; i != values.size(); i++) {
			if (values.get(i) instanceof Constant) {
				Constant newConstant = new Constant(values.get(i).evaluate(null, null, null, amount));
				values.remove(i);
				values.add(i, newConstant);
				return;
			}
		}
		IValue[] newValues = new IValue[values.size() + 1];
		newValues[0] = values.get(0);
		newValues[1] = new Constant(amount); 
		for (int i = 2; i < newValues.length; i++) {
			newValues[i] = values.get(i - 1);
		}
	}
	
	@Override
	public String toString() {
		String base = "";
		for (IValue element : values) {
			base += element.toString() + " ";
		}
		if (base.length() == 0) {
			return "None";
		}
		return base.substring(2).trim();
	}

	@Override
	public Attribute getBaseAttribute() {
		return ((AddAttribute)values.get(0)).getAttribute();
	}

	@Override
	public Attribute getResistAttribute() {
		for (IValue value : values) {
			if (value instanceof SubtractAttribute) {
				return ((SubtractAttribute)value).getAttribute();
			}
		}
		return null;
	}

	@Override
	public IFormula cloneFormula() {
		List<IValue> newFormulaValues = new ArrayList<IValue>();
		for (IValue value : values) {
			newFormulaValues.add(value.cloneValue());
		}
		return new StaticFormula(newFormulaValues.toArray(new IValue[0]));
	}
}
