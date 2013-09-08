package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractSingleUseEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class NextClashNoAttributeEffect extends AbstractSingleUseEffect {

	@Override
	public ISpecialEffect cloneEffect() {
		return new NextClashNoAttributeEffect();
	}
	
	@Override
	public String[] preCompare(ICharacter original, Technique t1, ICharacter defender,
			Technique t2) {
		int amount = -original.getAttribute(t1.getClashAttribute());
		original.getStatus().applyClashMod(new Modifier(amount));
		apply();
		return null;
	}
	
	@Override
	public String getStatusString() {
		return "No Attribute to clash";
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "No Attribute to clash";
	}

}
