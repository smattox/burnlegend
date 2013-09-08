package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class OpponentMovementCondition extends AbstractCondition {
	private final Movement[] types;
	
	public OpponentMovementCondition(Movement[] types, ISpecialEffect[] effects) {
		super(effects);
		this.types = types;
	}
	
	@Override
	public String[] preCompare(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		for (Movement movement : types) {
			if (t2.getMovement() == movement) {
				return apply(original, t1);
			}
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OpponentMovementCondition(types, cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "vs. " + StringUtils.join(types, " OR ") + ": ";
	}
}
