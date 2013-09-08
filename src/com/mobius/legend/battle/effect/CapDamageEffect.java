package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;

public class CapDamageEffect extends AbstractCurrentRoundEffect {

	private int amount;
	
	public CapDamageEffect(int amount) {
		this.amount = amount;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new CapDamageEffect(amount);
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		int damage = BattleEngine.getInstance().getActionForCharacter(original).getTechnique().getDamagePool().evaluate(original, defender);
		if (damage > amount) {
			int difference = amount - damage;
			original.getStatus().applyDamageMod(new Modifier(difference));
		}
		return null;
	}

}
