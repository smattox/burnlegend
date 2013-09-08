package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractSingleUseEffect;
import com.mobius.legend.battle.effect.condition.OnVictoryCondition;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Technique;

public class NextRestoreBlockedEffect extends AbstractSingleUseEffect {

	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		@SuppressWarnings("unchecked")
		ISpecialEffect[] effectsToRemove = t1.getConditionalSubeffects(OnVictoryCondition.class,
			ModTraitEffect.class, StealTraitEffect.class);
		// TODO: Fix this to only specify the right traits
		if (effectsToRemove.length > 0) {
			((MutableTechnique)t1).removeEffects(effectsToRemove);
			apply();
		}
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new NextRestoreBlockedEffect();
	}
	
	@Override
	public String getStatusString() {
		return "Cannot restore Health or Ki";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Cannot restore Health or Ki";
	}

}
