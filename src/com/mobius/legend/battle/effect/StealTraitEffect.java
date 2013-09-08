package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.TraitType;
import com.mobius.legend.math.IValue;

public class StealTraitEffect extends AbstractCurrentRoundEffect {
	
	private final TraitType type;
	private IValue amount;
	
	public StealTraitEffect(TraitType type, IValue amount) {
		this.type = type;
		this.amount = amount;
	}
	
	@Override
	public String[] onDefeat(ICharacter original, ICharacter defender) {
		int stolen = original.getStatus().getTrait(type).adjustValue(-amount.evaluate(original, null, defender, 0));
		int added = defender.getStatus().getTrait(type).adjustValue(stolen);
		if (stolen > 0 && added > 0) {
			return new String[] { original.getName() + " loses " + (-stolen) + " " + type + "!",
								  defender.getName() + " gains " + added + " " + type };
		}
		if (stolen > 0) {
			return new String[] { original.getName() + " loses " + (-stolen) + " " + type + "!" };
		}
		return null;
	}
	
	public String getTechniqueDisplayString() {
		return "Steal " + amount + " " + type;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new StealTraitEffect(type, amount.cloneValue());
	}
}
