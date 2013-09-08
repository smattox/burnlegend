package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.technique.Technique;

public class BlockSpendKiEffect extends AbstractNextRoundEffect {

	@Override
	public boolean allowTechnique(BattleCharacter attacker, Technique t1,
			BattleCharacter defender, Technique t2) {
		return t1.getKiCost() == 0;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new BlockSpendKiEffect();
	}
	
	@Override
	public String getStatusString() {
		return "Cannot spend Ki";
	}
	
	public String getTechniqueDisplayString() {
		return "Victim cannot spend Ki next round";
	}

}
