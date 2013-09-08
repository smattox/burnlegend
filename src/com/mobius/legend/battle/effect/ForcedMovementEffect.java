package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.Technique;

public class ForcedMovementEffect extends AbstractNextRoundEffect {

	private Movement movement;
	
	public ForcedMovementEffect(Movement movement) {
		this.movement = movement;
	}
	
	@Override
	public String[] preCompare(ICharacter original, Technique t1, ICharacter defender,
			Technique t2) {
		((MutableTechnique)t1).setMovement(movement);
		return null;
	}
	
	@Override
	public ISpecialEffect cloneEffect() {
		return new ForcedMovementEffect(movement);
	}
	
	@Override
	public String getStatusString() {
		return "All Techniques are " + movement;
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Next Technique counts as " + movement;
	}

}
