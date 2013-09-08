package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Movement;

public class MovementEffect extends AbstractCurrentRoundEffect {
	
	private final Movement movement;
	
	public MovementEffect(Movement type) {
		this.movement = type;
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		if (original.getStatus().isUnconcious()) {
			return null;
		}
		boolean distant = BattleEngine.getInstance().isDistant(original, defender);
		if (!distant && movement == Movement.Back ||
			 distant && movement == Movement.Advance) {
			BattleEngine.getInstance().setDistant(original, defender, movement == Movement.Back);
			return new String[] { original.getName() + " moves " + 
					(movement == Movement.Advance ? "near." : "distant.") };
		}
		return null;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new MovementEffect(movement);
	}
	
	public String getTechniqueDisplayString() {
		return "Movement: " + movement;
	}
}
