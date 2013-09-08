package com.mobius.legend.math;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class AddTechnique implements IValue {

	private final String name;
	
	public AddTechnique(String name) {
		this.name = name;
	}
	
	@Override
	public int evaluate(ICharacter attacker, Technique technique, ICharacter defender,
			int current) {
		if (technique == null) {
			technique = BattleEngine.getInstance().getActionForCharacter(attacker).getTechnique();
		}
		return current + attacker.getTechniqueRating(technique);
	}
	
	@Override
	public String toString() {
		return "+ " + name;
	}

	@Override
	public IValue cloneValue() {
		return new AddTechnique(name);
	}

	@Override
	public boolean stacks(IValue check) {
		return false;
	}
}
