package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueConflictResult;

public class MustClashEffect extends AbstractCurrentRoundEffect {

	@Override
	public boolean allowTechnique(BattleCharacter attacker, Technique t1,
			BattleCharacter defender, Technique t2) {
		boolean isDistant = BattleEngine.getInstance().isDistant(attacker, defender);
		return t1.getInteraction(t2, isDistant) == TechniqueConflictResult.Clash;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new MustClashEffect();
	}
}
