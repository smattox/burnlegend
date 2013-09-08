package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;

public class OnEnemyVictoryCondition extends AbstractCondition {
	
	public OnEnemyVictoryCondition(ISpecialEffect[] effects) {
		super(effects);
	}
	
	@Override
	public String[] onDefeat(ICharacter original, ICharacter defender) {
		return apply(defender, null);
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new OnEnemyVictoryCondition(cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "On Enemy Victory: ";
	}
}
