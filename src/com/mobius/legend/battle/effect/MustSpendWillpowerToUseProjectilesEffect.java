package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class MustSpendWillpowerToUseProjectilesEffect extends AbstractNextRoundEffect {

	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.getStatus().applyWillpowerToUseProjectilesRound();
		original.getStatus().applyWillpowerToUseProjectilesRound();
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new MustSpendWillpowerToUseProjectilesEffect();
	}
	
	@Override
	public String getStatusString() {
		return "Must spend WP to use Projectiles";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Victim must spend Willpower to use Projectiles";
	}

}
