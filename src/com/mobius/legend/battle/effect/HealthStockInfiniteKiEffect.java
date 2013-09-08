package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractHealthStockDurationEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class HealthStockInfiniteKiEffect extends AbstractHealthStockDurationEffect {
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		original.getStatus().setInfiniteKi(true);
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new HealthStockInfiniteKiEffect();
	}

	@Override
	protected String remove(ICharacter original) {
		original.getStatus().setInfiniteKi(false);
		return null;
	}
	
	@Override
	public String getStatusString() {
		return "Infinite Ki";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Infinite Ki for one Health Stock";
	}

}
