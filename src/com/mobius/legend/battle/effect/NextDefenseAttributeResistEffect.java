package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractSingleUseEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class NextDefenseAttributeResistEffect extends AbstractSingleUseEffect {

	private final Attribute[] attributes;
	private final int amount;
	
	public NextDefenseAttributeResistEffect(Attribute[] attributes, int amount) {
		this.attributes = attributes;
		this.amount = amount;
	}
	
	@Override
	public String[] onDefeat(ICharacter original, ICharacter defender) {
		Technique technique = BattleEngine.getInstance().getActionForCharacter(defender).getTechnique();
		for (Attribute attribute : attributes) {
			if (technique.getOpponentDamageResistAttribute() == attribute) {
				defender.getStatus().applyDamageMod(new Modifier(amount));
			}
		}
		apply();
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new NextDefenseAttributeResistEffect(attributes, amount);
	}
}
