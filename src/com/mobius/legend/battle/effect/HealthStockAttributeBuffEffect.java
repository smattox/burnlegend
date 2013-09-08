package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractHealthStockDurationEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class HealthStockAttributeBuffEffect extends AbstractHealthStockDurationEffect {

	private final Attribute attribute;
	private final int amount;
	
	public HealthStockAttributeBuffEffect(Attribute attribute, int amount) {
		this.attribute = attribute;
		this.amount = amount;
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.addAttribute(attribute, amount);
		return new String[] { original.getName() + ": " + StringUtils.getSignedInt(amount) + " to " + attribute };
	}
	
	protected String remove(ICharacter original) {
		original.addAttribute(attribute, -amount);
		return original.getName() + ": " + StringUtils.getSignedInt(-amount) + " to " + attribute;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new HealthStockAttributeBuffEffect(attribute, amount);
	}
	
	@Override
	public String getStatusString() {
		return StringUtils.getSignedInt(amount) + " to " + attribute + " for Health Stock";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " to " + attribute + " for Health Stock";
	}
}
