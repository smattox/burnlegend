package com.mobius.legend.battle.effect.base;

import com.mobius.legend.battle.BattleEngine;

public abstract class AbstractNextRoundEffect extends AbstractSpecificRoundEffect {

	public AbstractNextRoundEffect() {
		super(BattleEngine.getInstance().getCurrentRoundNumber() + 1);
	}
}
