package com.mobius.legend.technique;

import com.mobius.legend.character.ICharacter;

public class TechniqueOptions {
	private String label;
	private ITechniqueOptionPick[] picks;
	
	public TechniqueOptions(String label, ITechniqueOptionPick... picks) {
		this.label = label;
		this.picks = picks;
	}
	
	public String getLabel() {
		return label;
	}
	
	public ITechniqueOptionPick[] getPicks() {
		return picks;
	}
	
	public ITechniqueOptionPick getSingleLegalPick(ICharacter character) {
		ITechniqueOptionPick legalPick = null;
		if (picks.length == 1) {
			return null;
		}
		for (ITechniqueOptionPick pick : picks) {
			if (pick.isLegal(character)) {
				if (legalPick == null) {
					legalPick = pick;
				} else {
					return null;
				}
			}
		}
		return legalPick;
	}
	
	public String getTechniqueDisplayString() {
		if (picks.length > 1) {
			String start = "";
			for (ITechniqueOptionPick pick : picks) {
				if (pick != picks[0]) {
					start += " OR ";
				}
				start += pick.getButtonLabel();
			}
			return start.trim();
		} else {
			return "Optional: " + picks[0].getButtonLabel();
		}
	}
}
