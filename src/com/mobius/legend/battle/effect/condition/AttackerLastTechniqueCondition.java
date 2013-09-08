package com.mobius.legend.battle.effect.condition;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.battle.GameRound;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class AttackerLastTechniqueCondition extends AbstractCondition {
	private final String techniqueName;
	
	public AttackerLastTechniqueCondition(String techniqueName, ISpecialEffect[] effects) {
		super(effects);
		this.techniqueName = techniqueName;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		GameRound round = BattleEngine.getInstance().getPreviousRound();
		ChosenAction action = round.getActionForCharacter(original);
		if (action == null) {
			return null;
		}
		Technique technique = action.getTechnique(); 
		if (technique.getName().equals(techniqueName)) {
			return apply(original, t1);
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new AttackerLastTechniqueCondition(techniqueName, cloneEffects());
	}

	@Override
	protected String getTechniqueDisplayStringHeader() {
		return "Following " + techniqueName + ": ";
	}
}
