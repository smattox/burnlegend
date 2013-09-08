package com.mobius.legend.technique.options;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.MutableTechnique;

public class BackMovement implements ITechniqueOptionPick {

	@Override
	public String getButtonLabel() {
		return "Back";
	}

	@Override
	public void apply(MutableTechnique technique) {
		technique.setMovement(Movement.Back);
	}

	@Override
	public boolean isLegal(ICharacter character) {
		return true;
	}

}
