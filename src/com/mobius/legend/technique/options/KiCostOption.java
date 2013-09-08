package com.mobius.legend.technique.options;

import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.MutableTechnique;

public class KiCostOption implements ITechniqueOptionPick {

	private final int amount;
	
	public KiCostOption(int amount) {
		this.amount = amount;
	}
	
	@Override
	public void apply(MutableTechnique technique) {
		technique.addKiCost(amount);
	}

	@Override
	public String getButtonLabel() {
		return amount + " Ki";
	}

	@Override
	public boolean isLegal(ICharacter character) {
		return character.getStatus().getKi() > 0;
	}

}
