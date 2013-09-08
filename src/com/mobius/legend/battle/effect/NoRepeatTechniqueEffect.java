package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.technique.Technique;

public class NoRepeatTechniqueEffect extends AbstractNextRoundEffect {

	@Override
	public boolean allowTechnique(BattleCharacter attacker, Technique t1,
			BattleCharacter defender, Technique t2) {
		Technique priorTechnique = BattleEngine.getInstance().getPreviousRound().getActionForCharacter(attacker).getTechnique();
		return !priorTechnique.getName().equals(t1.getName());
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new NoRepeatTechniqueEffect();
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Victim cannot repeat defeated technique next round";
	}

}
