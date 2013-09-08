package com.mobius.legend.battle.effect.base;

import com.mobius.legend.battle.BattleEngine;

public abstract class AbstractTimerEffect extends AbstractEffect {

	private int completionRound = 0;
	
	protected void setTimer(int duration) {
		completionRound = BattleEngine.getInstance().getCurrentRoundNumber() + duration;
	}
	
	protected int getTimeLeft() {
		return completionRound - BattleEngine.getInstance().getCurrentRoundNumber();
	}
	
	@Override
	public boolean isComplete() {
		return completionRound > 0 && getTimeLeft() == 0;
	}
}
