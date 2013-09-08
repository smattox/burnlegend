package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class MoonCrescentSlashEffect extends AbstractNextRoundEffect {

	int initialRound;
	int damage;
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		initialRound = BattleEngine.getInstance().getCurrentRoundNumber();
		damage = BattleEngine.getInstance().getCurrentRound().getDamagePool(
				BattleEngine.getInstance().getOpponent(original));
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new MoonCrescentSlashEffect();
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		if (BattleEngine.getInstance().getCurrentRoundNumber() == initialRound) {
			return null;
		}
		return BattleEngine.getInstance().applyAdditionalDamage("Moon Crescent Slash", damage, original);
	}
	
	@Override
	public String getStatusString() {
		return "Will suffer " + damage + " additional damage dice";
	}
	
	public String getTechniqueDisplayString() {
		return "Repeats damage next round";
	}

}
