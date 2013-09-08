package com.mobius.legend.technique;

import com.mobius.legend.character.ICharacter;

public interface ITechniqueOptionPick {
	void apply(MutableTechnique technique);
	
	boolean isLegal(ICharacter character);
	
	String getButtonLabel();
}
