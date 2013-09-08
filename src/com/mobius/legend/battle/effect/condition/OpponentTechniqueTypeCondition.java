package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.Type;

public class OpponentTechniqueTypeCondition extends AbstractCondition {
	private final Type type;
	
	public OpponentTechniqueTypeCondition(Type type, ISpecialEffect[] effects) {
		super(effects);
		this.type = type;
	}
	
	@Override
	public String[] preCompare(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		if (t2.getType() == type) {
			return apply(original, t1);
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OpponentTechniqueTypeCondition(type, cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "vs. " + type + ": ";
	}
}
