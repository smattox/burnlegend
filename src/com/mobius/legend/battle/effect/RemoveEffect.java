package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class RemoveEffect extends AbstractCurrentRoundEffect {

	private String className;
	
	public RemoveEffect(String name) {
		this.className = name;
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		for (ISpecialEffect effect : original.getStatus().getEffects()) {
			if (effect.getClass().getName().equals(className)) {
				original.getStatus().removeEffect(effect);
				return null;
			}
		}
		return null;
	}

	
	@Override
	public ISpecialEffect cloneEffect() {
		return new RemoveEffect(className);
	}

}
