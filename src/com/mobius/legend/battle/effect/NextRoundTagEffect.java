package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueTag;

public class NextRoundTagEffect extends AbstractNextRoundEffect {

	private final TechniqueTag tag;
	
	public NextRoundTagEffect(TechniqueTag tag) {
		this.tag = tag;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new NextRoundTagEffect(tag);
	}
	
	@Override
	public String[] preCompare(ICharacter original, Technique t1, ICharacter defender,
			Technique t2) {
		((MutableTechnique)t1).addTag(tag);
		return null;
	}

}
