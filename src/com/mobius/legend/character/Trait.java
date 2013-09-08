package com.mobius.legend.character;

public class Trait implements ITrait {
	private final int maxValue;
	private int value;
	
	public Trait(int maxValue) {
		this(0, maxValue);
	}
	
	public Trait(int value, int maxValue) {
		this.value = value;
		this.maxValue = maxValue;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int adjustValue(int amount) {
		int amountToAdd = amount < 0 ? Math.max(-value, amount) : Math.min(maxValue - value, amount);
		value += amountToAdd;
		return amountToAdd;
	}
	
	
}
