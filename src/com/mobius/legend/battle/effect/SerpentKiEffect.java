package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractHealthStockDurationEffect;
import com.mobius.legend.character.ICharacter;

public class SerpentKiEffect extends AbstractHealthStockDurationEffect {

	boolean damageTaken = false;
	final int initialRound;
	
	public SerpentKiEffect() {
		initialRound = BattleEngine.getInstance().getCurrentRoundNumber();
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new SerpentKiEffect();
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		if (BattleEngine.getInstance().getCurrentRoundNumber() == initialRound) {
			return null;
		}
		if (BattleEngine.getInstance().getCurrentRound().getDamageInflicted(original) > 0) {
			damageTaken = true;
			return null;
		}
		return BattleEngine.getInstance().applyAdditionalDamage("Bridge of the Black Serpent", 1, original);
	}
	
	@Override
	public boolean isComplete() {
		return super.isComplete() || damageTaken;
	}

	@Override
	protected String remove(ICharacter original) {
		return null;
	}
	
	@Override
	public String getStatusString() {
		return "1 Damage/Round for Health Stock";
	}
	
	public String getTechniqueDisplayString() {
		return "1 Damage/Round until Health Stock lost or damage dealt";
	}

}
