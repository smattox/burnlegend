package com.mobius.legend.technique.options;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.MutableTechnique;

public class WillpowerCostOption implements ITechniqueOptionPick {

	private final int amount;
	
	public WillpowerCostOption(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void apply(MutableTechnique technique) {
		technique.addWillpowerCost(amount);
	}

	@Override
	public String getButtonLabel() {
		return amount + " Willpower";
	}

	@Override
	public boolean isLegal(ICharacter character) {
		return character.getStatus().getWillpower() > 0;
	}

}
