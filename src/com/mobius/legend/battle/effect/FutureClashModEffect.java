package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractLimitedUseEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Range;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class FutureClashModEffect extends AbstractLimitedUseEffect{

	private final String reason;
	private int amount;
	private final Range range;
	
	public FutureClashModEffect(String reason, int span, int amount, Range range) {
		super(span);
		this.amount = amount;
		this.reason = reason;
		this.range = range;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		if (range == null || t1.getRange() == range) {
			original.getStatus().applyClashMod(new Modifier(amount));
			apply();
			return reason != null ? new String[] { reason + ": " + original.getName() + " at " +
					StringUtils.getSignedInt(amount) + " to clash." } : null;
		}
		return null;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (effect instanceof FutureClashModEffect) {
			FutureClashModEffect clashEffect = (FutureClashModEffect) effect;
			if (clashEffect.reason.equals(reason) &&
				clashEffect.getSpan() == getSpan()) {
				this.amount += clashEffect.amount;
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String getLogApplyString(ICharacter character) {
		return character.getName() + " will receive " + StringUtils.getSignedInt(amount) + " to next " +
				(getSpan() > 1 ?  getSpan() + " clashes" : "clash");
	}
	
	@Override
	public String getStatusString() {
		return StringUtils.getSignedInt(amount) + " to next " + 
				(getSpan() > 1 ?  getSpan() + " clashes" : "clash");
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new FutureClashModEffect(reason, getSpan(), amount, range);
	}
	
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " next " + 
				(getSpan() > 1 ?  getSpan() + " clashes" : "clash");
	}

}
