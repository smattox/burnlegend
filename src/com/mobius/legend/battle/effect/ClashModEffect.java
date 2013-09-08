package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundStackableEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.math.IValue;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class ClashModEffect extends AbstractCurrentRoundStackableEffect {
	
	public ClashModEffect(IValue amount) {
		super(amount);
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		original.getStatus().applyClashMod(new Modifier(amount.evaluate(original, t1, opponent, 0)));
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new ClashModEffect(amount.cloneValue());
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount.evaluate(null,  null, null, 0)) + " Clash";
	}
}
