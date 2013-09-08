package com.mobius.legend.battle.effect;

public class Modifier {
	final int amount;
	
	public Modifier(int amount) {
		this.amount = amount;
	}
	
	public int getMod() {
		return amount;
	}
}
