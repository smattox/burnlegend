package com.mobius.legend.battle;

import com.mobius.legend.technique.Technique;

public class ChosenAction {
	private final Technique technique;
	private boolean reversal = false;
	
	public ChosenAction(Technique technique) {
		this.technique = technique;
	}
	
	public Technique getTechnique() {
		return technique;
	}
	
	public boolean getReversal() {
		return reversal;
	}
	
	public void useReversal() {
		reversal = true;
	}
}
