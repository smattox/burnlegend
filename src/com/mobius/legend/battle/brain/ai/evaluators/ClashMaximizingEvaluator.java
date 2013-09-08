package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;

public class ClashMaximizingEvaluator extends AbstractTechniqueEvaluator {

	private final ICharacter character;
	
	public ClashMaximizingEvaluator(double weight, ICharacter character) {
		super(weight);
		this.character = character;
	}

	@Override
	protected double[] getEvaluation(IKnownTechnique[] techniques) {
		int maxClash = 0;
		ICharacter opponent = BattleEngine.getInstance().getOpponent(character);
		for (IKnownTechnique technique : techniques) {
			if (technique.getTechnique().getClashPool() == null) {
				continue;
			}
			maxClash = Math.max(maxClash,
					technique.getTechnique().getClashPool().evaluate(character, technique.getTechnique(), opponent));
		}
		
		double[] evaluation = new double[techniques.length];
		for (int i = 0; i != techniques.length; i++) {
			if (techniques[i].getTechnique().getClashPool() == null) {
				continue;
			}
			double clash = techniques[i].getTechnique().getClashPool().evaluate(character, techniques[i].getTechnique(), opponent);
			double modifier = techniques[i].getTechnique().getDamagePool() != null ? 1 : .5;
			evaluation[i] = modifier * (clash / maxClash);
		}
		return evaluation;
	}

}
