package com.mobius.legend.technique.options;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.MutableTechnique;

public class StillMovement implements ITechniqueOptionPick {

	@Override
	public String getButtonLabel() {
		return "Still";
	}

	@Override
	public void apply(MutableTechnique technique) {
		technique.setMovement(Movement.Still);
	}

	@Override
	public boolean isLegal(ICharacter character) {
		return true;
	}

}
