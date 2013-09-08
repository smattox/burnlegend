package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundStackableEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.math.IValue;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class DamageModEffect extends AbstractCurrentRoundStackableEffect {
	private boolean applied = false;
	
	public DamageModEffect(IValue amount) {
		super(amount);
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		if (!applied) {
			original.getStatus().applyDamageMod(new Modifier(amount.evaluate(original, t1, opponent, 0)));
			applied = true;
		}
		return null;
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		if (!applied) {
			original.getStatus().applyDamageMod(new Modifier(amount.evaluate(original, technique, null, 0)));
			applied = true;
		}
		return null;
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount.evaluate(null,  null, null, 0)) + " Damage";
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new DamageModEffect(amount.cloneValue());
	}
}
