package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.battle.GameRound;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueTag;

public class FollowsTagCondition extends AbstractCondition {
	private final TechniqueTag tag;
	
	public FollowsTagCondition(TechniqueTag tag, ISpecialEffect[] effects) {
		super(effects);
		this.tag = tag;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		GameRound round = BattleEngine.getInstance().getPreviousRound();
		ChosenAction action = round.getActionForCharacter(original);
		if (action == null) {
			return null;
		}
		if (action.getTechnique().hasTag(tag)) {
			return apply(original, t1);
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new FollowsTagCondition(tag, cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "Following " + tag + " technique: ";
	}
}
