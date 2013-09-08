package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.Range;

public class NotOutRangingEvaluator extends AbstractTechniqueEvaluator {

	private final ICharacter character;
	
	public NotOutRangingEvaluator(double weight, ICharacter character) {
		super(weight);
		this.character = character;
	}

	@Override
	protected double[] getEvaluation(IKnownTechnique[] techniques) {
		double[] evaluation = new double[techniques.length];
		boolean isDistant = BattleEngine.getInstance().isDistant(character,
				BattleEngine.getInstance().getOpponent(character)); 
		for (int i = 0; i != evaluation.length; i++) {
			if (isDistant) {
				evaluation[i] = techniques[i].getTechnique().getRange() == Range.Mid ||
								techniques[i].getTechnique().getMovement() == Movement.Advance ?
										1 : 0;
			} else {
				evaluation[i] = 0;
			}
		}
		return evaluation;
	}

}
