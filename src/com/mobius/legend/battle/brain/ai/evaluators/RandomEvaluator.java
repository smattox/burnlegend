package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.utilities.RNG;

public class RandomEvaluator extends AbstractTechniqueEvaluator {

	public RandomEvaluator(double weight) {
		super(weight);
	}

	@Override
	protected double[] getEvaluation(IKnownTechnique[] techniques) {
		double[] evaluation = new double[techniques.length];
		for (int i = 0; i != techniques.length; i++) {
			evaluation[i] = RNG.randomRange();
		}
		return evaluation;
	}

}
