package com.mobius.legend.battle.brain.ai;

import com.mobius.legend.character.technique.IKnownTechnique;

public interface IEvaluator {
	boolean hasOpinionOnTechniques();
	
	double[] getWeightedTechniqueEvaluation(IKnownTechnique[] techniques);
	
	boolean hasOpinionOnReversal();
	
	boolean wantsToUseReversal();
}
