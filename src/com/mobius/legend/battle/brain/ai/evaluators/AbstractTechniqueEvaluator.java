package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.battle.brain.ai.IEvaluator;
import com.mobius.legend.character.technique.IKnownTechnique;

public abstract class AbstractTechniqueEvaluator implements IEvaluator {
	private final double weight;
	
	public AbstractTechniqueEvaluator(double weight) {
		this.weight = weight;
	}
	
	protected abstract double[] getEvaluation(IKnownTechnique[] techniques);

	@Override
	public double[] getWeightedTechniqueEvaluation(IKnownTechnique[] techniques) {
		double[] evaluation = getEvaluation(techniques);
		for (int i = 0; i != evaluation.length; i++) {
			evaluation[i] *= weight;
		}
		return evaluation;
	}
	
	@Override
	public boolean hasOpinionOnTechniques() {
		return true;
	}

	@Override
	public boolean hasOpinionOnReversal() {
		return false;
	}

	@Override
	public boolean wantsToUseReversal() {
		return false;
	}
	
}
