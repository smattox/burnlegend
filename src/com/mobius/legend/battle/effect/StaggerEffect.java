package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public class StaggerEffect extends AbstractNextRoundEffect {

	@Override
	public String[] preCompare(ICharacter original, Technique t1,
			ICharacter defender, Technique t2) {
		original.getStatus().applyStaggered();
		return new String[] { original.getName() + " is staggered!" };
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new StaggerEffect();
	}
	
	public String getTechniqueDisplayString() {
		return "Staggered";
	}

}
