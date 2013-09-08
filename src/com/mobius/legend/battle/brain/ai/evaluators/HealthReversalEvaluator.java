package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.utilities.RNG;

public class HealthReversalEvaluator extends AbstractReversalEvaluator {
	
	public HealthReversalEvaluator(ICharacter character) {
		super(character);
	}

	@Override
	public boolean wantsToUseReversal() {
		if (willBeDefeated()) {
			double stocksCount = character.getStatus().getStocks() - 1;
			double stocksVar = stocksCount / character.getStatus().getStartingStocks();
			double stocksImport = 1 - stocksVar;
			double healthCount = character.getStatus().getHealth() - 1;
			double healthVar = healthCount / CharacterStatus.MAX_HEALTH;
			double healthImport = 1 - healthVar;
			double totalImport = stocksImport * healthImport;
			return RNG.randomRange() < totalImport;
		}
		return false;
	}
}
