package com.mobius.legend.technique.options;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.MutableTechnique;

public class EffectOption implements ITechniqueOptionPick {

	private final ISpecialEffect[] effects;
	
	public EffectOption(ISpecialEffect[] effects) {
		this.effects = effects;
	}
	
	@Override
	public void apply(MutableTechnique technique) {
		for (ISpecialEffect effect : effects) {
			technique.addEffect(effect);
		}
	}

	@Override
	public String getButtonLabel() {
		String label = "";
		for (ISpecialEffect effect : effects) {
			String technique = effect.getTechniqueDisplayString();
			if (technique != null) {
				if (effect != effects[0]) {
					label += ", ";
				}
				label += technique;
			}
		}
		return label;
	}

	@Override
	public boolean isLegal(ICharacter character) {
		return true;
	}

}
