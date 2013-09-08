package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractNextRoundEffect;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.Range;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.Type;

public class PreventTechniqueEffect extends AbstractNextRoundEffect {

	private final Movement movement;
	private final Range range;
	private final Type type;
	
	public PreventTechniqueEffect(Range range, Movement movement, Type type) {
		this.movement = movement;
		this.range = range;
		this.type = type;
	}
	
	@Override
	public boolean allowTechnique(BattleCharacter attacker, Technique t1,
			BattleCharacter defender, Technique t2) {
		if (movement != null && t1.getMovement() == movement) {
			return false;
		}
		if (range != null && t1.getRange() == range) {
			return false;
		}
		if (type != null && t1.getType() == type) {
			return false;
		}
		return true;
	}

	@Override
	public ISpecialEffect cloneEffect() {
		return new PreventTechniqueEffect(range, movement, type);
	}
	
	public String getTechniqueDisplayString() {
		String startString = "Victim cannot use ";
		if (movement != null) {
			startString += movement;
		}
		if (range != null) {
			startString += range;
		}
		if (type != null) {
			startString += type;
		}
		return startString + " techniques";
	}
}
