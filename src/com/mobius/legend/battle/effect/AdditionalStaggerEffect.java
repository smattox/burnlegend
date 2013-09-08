package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.utilities.StringUtils;

public class AdditionalStaggerEffect extends AbstractCurrentRoundEffect {

	private int amount;
	
	public AdditionalStaggerEffect(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		original.getStatus().applyHit();
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new AdditionalStaggerEffect(amount);
	}
	
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " Stagger";
	}

}
