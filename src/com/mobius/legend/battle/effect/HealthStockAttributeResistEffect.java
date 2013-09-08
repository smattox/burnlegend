package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractHealthStockDurationEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class HealthStockAttributeResistEffect extends AbstractHealthStockDurationEffect {

	private final Attribute attribute;
	private final int amount;
	
	public HealthStockAttributeResistEffect(Attribute attribute, int amount) {
		this.attribute = attribute;
		this.amount = amount;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		if (t1.getOpponentDamageResistAttribute() == attribute) {
			defender.getStatus().applyDamageMod(new Modifier(-amount));
		}
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new HealthStockAttributeResistEffect(attribute, amount);
	}

	@Override
	protected String remove(ICharacter original) {
		return null;
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " to resist with " + attribute + " for Health Stock";
	}

}
