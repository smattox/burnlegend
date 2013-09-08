package com.mobius.legend.battle.effect.base;

public abstract class AbstractLimitedUseEffect extends AbstractEffect {

	private int lifespan;
	
	public AbstractLimitedUseEffect(int span) {
		this.lifespan = span;
	}
	
	protected int getSpan() {
		return lifespan;
	}
	
	protected void apply() {
		if (lifespan > 0) {
			lifespan--;
		}
	}
	
	@Override
	public boolean isComplete() {
		return lifespan == 0;
	}
	
}
