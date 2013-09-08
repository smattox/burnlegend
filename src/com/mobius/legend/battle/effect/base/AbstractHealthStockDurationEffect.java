package com.mobius.legend.battle.effect.base;

import com.mobius.legend.character.ICharacter;

public abstract class AbstractHealthStockDurationEffect extends AbstractEffect {
	private boolean exhausted = false;
	
	@Override
	public boolean isComplete() {
		return exhausted;
	}
	
	protected abstract String remove(ICharacter original);
	
	@Override
	public String[] onClosure(ICharacter original) {
		if (original.getStatus().hasLostHealthStockThisRound()) {
			exhausted = true;
			String log = remove(original);
			return log != null ? new String[] { log } : null;
		}
		return null;
	}
}
