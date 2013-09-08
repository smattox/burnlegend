package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundStackableEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.TraitType;
import com.mobius.legend.math.Constant;
import com.mobius.legend.math.IValue;
import com.mobius.legend.utilities.StringUtils;

public class ModTraitEffect extends AbstractCurrentRoundStackableEffect {
	private final TraitType type;
	
	public ModTraitEffect(TraitType type, int amount) {
		this(type, new Constant(amount));
	}
	
	public ModTraitEffect(TraitType type, IValue amount) {
		super(amount);
		this.type = type;
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		int added = original.getStatus().getTrait(type).adjustValue(amount.evaluate(original, null, null, 0));
		return added != 0 ? new String[] { original.getName() + 
				(added > 0 ? " gains " + added + " " + type + "." :
				             " loses " + added + " " + type + "!") } :
			null; 
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		if (effect instanceof ModTraitEffect && ((ModTraitEffect)effect).type.equals(type)) {
			return super.stacks(effect);
		}
		return true;
	}
	
	public String getTechniqueDisplayString() {
		return StringUtils.getSignedInt(amount.evaluate(null, null, null, 0)) + " " + type;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new ModTraitEffect(type, amount.cloneValue());
	}
}
