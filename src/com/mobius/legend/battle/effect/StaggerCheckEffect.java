package com.mobius.legend.battle.effect;

import java.util.ArrayList;
import java.util.List;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.RNG;
import com.mobius.legend.utilities.StringUtils;

public class StaggerCheckEffect extends AbstractNextRoundEffect {

	@Override
	public String[] preCompare(ICharacter original, Technique t1,
			ICharacter defender, Technique t2) {
		List<String> logs = new ArrayList<String>();
		if (BattleEngine.getInstance().getCurrentRoundNumber() == getRound()) {
			original.getStatus().resetRoundsHit();
			logs.add(original.getName() + " checks for stagger!");
			int roll[] = RNG.rollExalted(original.getAttribute(Attribute.Stamina));
			logs.add(StringUtils.rollString(roll));
			if (RNG.evaluateExalted(roll) < 2) {
				logs.add(original.getName() + " is staggered!");
				original.getStatus().applyStaggered();
			}
		}
		return logs.size() > 0 ? logs.toArray(new String[0]) : null;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		return !(effect instanceof StaggerCheckEffect);
	}

	@Override
	public String getStatusString() {
		return "Stagger check";
	}
	
	public String getTechniqueDisplayString() {
		return "Stagger check";
	}

	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		if (BattleEngine.getInstance().getCurrentRound().getDamageSuffered((BattleCharacter) defender) > 0) {
			defender.getStatus().applyEffect(
					new StaggerCheckEffect());
			return new String[] { "Sudden Comeback!" };
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new StaggerCheckEffect();
	}

}
