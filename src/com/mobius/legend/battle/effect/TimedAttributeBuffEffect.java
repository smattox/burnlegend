package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractTimerEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class TimedAttributeBuffEffect extends AbstractTimerEffect {

	private final Attribute attribute;
	private final int amount;
	private final int duration;
	
	public TimedAttributeBuffEffect(Attribute attribute, int amount, int duration) {
		this.attribute = attribute;
		this.amount = amount;
		this.duration = duration;
		setTimer(duration);
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.addAttribute(attribute, amount);
		return new String[] { original.getName() + ": " + StringUtils.getSignedInt(amount) + " to " + attribute };
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		if (isComplete()) {
			original.addAttribute(attribute, -amount);
			return new String[] { original.getName() + ": " + StringUtils.getSignedInt(-amount) + " to " + attribute };
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new TimedAttributeBuffEffect(attribute, amount, duration);
	}
	
	@Override
	public String getStatusString() {
		return StringUtils.getSignedInt(amount) + " to " + attribute + " for " + (getTimeLeft() + 1) + " rounds";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " to " + attribute + " for " + duration + " rounds";
	}

}
