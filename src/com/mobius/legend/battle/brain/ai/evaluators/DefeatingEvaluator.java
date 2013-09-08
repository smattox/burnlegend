package com.mobius.legend.battle.brain.ai.evaluators;

import java.util.Arrays;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.GameRound;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.Type;

public class DefeatingEvaluator extends AbstractTechniqueEvaluator {
	private final ICharacter character;
	
	public DefeatingEvaluator(double weight, ICharacter character) {
		super(weight);
		this.character = character;
	}

	@Override
	protected double[] getEvaluation(IKnownTechnique[] techniques) {
		double passedRounds = BattleEngine.getInstance().getCurrentRoundNumber() - 1;
		int[] typeCounts = getOpponentUsedCounts();
		double[] evaluation = new double[techniques.length];
		
		if (passedRounds == 0) {
			return evaluation;
		}
		
		for (int i = 0; i != techniques.length; i++) {
			Type[] techniqueDefeats = techniques[i].getTechnique().getDefeats();
			double techniqueEvaluation = 0;
			for (Type defeats : techniqueDefeats) {
				double count = typeCounts[Arrays.asList(Type.values()).indexOf(defeats)];
				techniqueEvaluation = Math.max(techniqueEvaluation, count / passedRounds);
			}
			evaluation[i] = techniqueEvaluation;
		}
		return evaluation;
	}
	
	private int[] getOpponentUsedCounts() {
		int[] typeCounts = new int[Type.values().length];
		int currentRound = BattleEngine.getInstance().getCurrentRoundNumber();
		ICharacter opponent = BattleEngine.getInstance().getOpponent(character);
		for (int i = 1; i < currentRound; i++) {
			GameRound round = BattleEngine.getInstance().getRound(i);
			Technique used = round.getActionForCharacter(opponent).getTechnique();
			typeCounts[Arrays.asList(Type.values()).indexOf(used.getType())]++;
		}
		return typeCounts;
	}
}
