package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractHealthStockDurationEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.utilities.StringUtils;

public class HealthStockDamageBoostEffect extends AbstractHealthStockDurationEffect {

	private int amount;
	
	public HealthStockDamageBoostEffect(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		original.getStatus().applyDamageMod(new Modifier(amount));
		return null;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (effect instanceof HealthStockDamageBoostEffect) {
			this.amount = Math.max(amount, ((HealthStockDamageBoostEffect)effect).amount);
			return false;
		}
		return true;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new HealthStockDamageBoostEffect(amount);
	}

	@Override
	protected String remove(ICharacter original) {
		return null;
	}
	
	@Override
	public String getStatusString() {
		return StringUtils.getSignedInt(amount) + " to damage for Health Stock";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount) + " to damage for Health Stock";
	}

}
