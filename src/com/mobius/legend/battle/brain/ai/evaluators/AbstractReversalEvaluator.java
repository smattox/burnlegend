package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.brain.ai.IEvaluator;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueConflictResult;

public abstract class AbstractReversalEvaluator implements IEvaluator {

	protected final ICharacter character;
	
	protected AbstractReversalEvaluator(ICharacter character) {
		this.character = character;
	}
	
	@Override
	public boolean hasOpinionOnTechniques() {
		return false;
	}

	@Override
	public double[] getWeightedTechniqueEvaluation(IKnownTechnique[] techniques) {
		return null;
	}

	@Override
	public boolean hasOpinionOnReversal() {
		return true;
	}
	
	protected boolean willBeDefeated() {
		ICharacter opponent = BattleEngine.getInstance().getOpponent(character);
		Technique myTechnique = BattleEngine.getInstance().getActionForCharacter(character).getTechnique();
		Technique yourTechnique = BattleEngine.getInstance().getActionForCharacter(opponent).getTechnique();
		boolean isDistant = BattleEngine.getInstance().isDistant(character, opponent);
		TechniqueConflictResult interaction = myTechnique.getInteraction(yourTechnique, isDistant);
		return interaction == TechniqueConflictResult.Defeated;
	}
}
