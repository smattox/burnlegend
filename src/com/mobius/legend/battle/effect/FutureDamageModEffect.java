package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractLimitedUseEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class FutureDamageModEffect extends AbstractLimitedUseEffect{

	private final String reason;
	private int amount;
	
	public FutureDamageModEffect(String reason, int span, int amount) {
		super(span);
		this.amount = amount;
		this.reason = reason;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		original.getStatus().applyDamageMod(new Modifier(amount));
		return reason != null ? new String[] { reason + ": " + original.getName() + " at " +
				StringUtils.getSignedInt(amount) + " to damage." } : null;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (effect instanceof FutureDamageModEffect) {
			FutureDamageModEffect damageEffect = (FutureDamageModEffect) effect;
			if (damageEffect.reason.equals(reason) &&
				damageEffect.getSpan() == getSpan()) {
				this.amount += damageEffect.amount;
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		apply();
		return null;
	}
	
	@Override
	public String getLogApplyString(ICharacter character) {
		return character.getName() + " will receive " + StringUtils.getSignedInt(amount) + " to next " +
				(getSpan() > 1 ?  getSpan() + " attacks" : "attack");
	}
	
	@Override
	public String getStatusString() {
		return StringUtils.getSignedInt(amount) + " to next " + 
				(getSpan() > 1 ?  getSpan() + " attacks" : "attack");
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new FutureDamageModEffect(reason, getSpan(), amount);
	}
	
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " next " + 
				(getSpan() > 1 ?  getSpan() + " attacks" : "attack");
	}

}
