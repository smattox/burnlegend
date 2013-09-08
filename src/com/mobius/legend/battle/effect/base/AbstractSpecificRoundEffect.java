package com.mobius.legend.battle.effect.base;

import com.mobius.legend.battle.BattleEngine;

public abstract class AbstractSpecificRoundEffect extends AbstractEffect {

	private final int roundNumber;
	
	public AbstractSpecificRoundEffect(int round) {
		roundNumber = round;
	}
	
	protected int getRound() {
		return roundNumber;
	}
	
	@Override
	public boolean isComplete() {
		return BattleEngine.getInstance().getCurrentRoundNumber() >= roundNumber; 
	}
}
