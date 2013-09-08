package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class OpponentDamageModEffect extends AbstractCurrentRoundEffect {

	private final int amount;
	
	public OpponentDamageModEffect(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1,
			ICharacter opponent, Technique t2) {
		opponent.getStatus().applyDamageMod(new Modifier(amount));
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new OpponentDamageModEffect(amount);
	}
	
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " Opponent Damage";
	}
}
