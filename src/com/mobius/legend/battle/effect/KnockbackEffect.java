package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;

public class KnockbackEffect extends AbstractCurrentRoundEffect {
	
	@Override
	public String[] onClosure(ICharacter original) {
		ICharacter opponent = BattleEngine.getInstance().getOpponent(original);
		if (!BattleEngine.getInstance().isDistant(original, opponent)) {
			BattleEngine.getInstance().setDistant(original, opponent, true);
			return new String[] { original.getName() + " is knocked back!" };
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new KnockbackEffect();
	}
	
	public String getTechniqueDisplayString() {
		return "Knockback";
	}
}
