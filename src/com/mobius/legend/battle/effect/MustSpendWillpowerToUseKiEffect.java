package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class MustSpendWillpowerToUseKiEffect extends AbstractNextRoundEffect {

	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.getStatus().applyWillpowerForKiRound();
		original.getStatus().applyWillpowerForKiRound();
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new MustSpendWillpowerToUseKiEffect();
	}
	
	@Override
	public String getStatusString() {
		return "Must spend WP to use Ki";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Victim must spend Willpower to use Ki";
	}

}
