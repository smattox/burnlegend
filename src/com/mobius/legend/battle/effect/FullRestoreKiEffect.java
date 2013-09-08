package com.mobius.legend.battle.effect;

import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.TraitType;

public class FullRestoreKiEffect extends ModTraitEffect {

	public FullRestoreKiEffect() {
		super(TraitType.Ki, CharacterStatus.MAX_KI);
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new FullRestoreKiEffect();
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Fully Restore Ki";
	}
}
