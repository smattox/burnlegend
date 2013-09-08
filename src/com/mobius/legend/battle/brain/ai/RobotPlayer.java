package com.mobius.legend.battle.brain.ai;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.battle.brain.AbstractBrain;
import com.mobius.legend.battle.brain.IPreviewRoundCallback;
import com.mobius.legend.battle.brain.ITechniqueSelectionCallback;
import com.mobius.legend.battle.brain.ai.evaluators.ClashMaximizingEvaluator;
import com.mobius.legend.battle.brain.ai.evaluators.DefeatingEvaluator;
import com.mobius.legend.battle.brain.ai.evaluators.HealthReversalEvaluator;
import com.mobius.legend.battle.brain.ai.evaluators.RandomEvaluator;
import com.mobius.legend.battle.brain.ai.evaluators.NotOutRangingEvaluator;
import com.mobius.legend.battle.brain.ai.evaluators.SiderealLadderEvaluator;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.TechniqueOptions;
import com.mobius.legend.utilities.RNG;

public class RobotPlayer extends AbstractBrain {

	private IEvaluator[] evaluators;
		
	private void setupEvaluators() {
		List<IEvaluator> list = new ArrayList<IEvaluator>();
		list.add(new RandomEvaluator(RNG.randomRange()));
		list.add(new DefeatingEvaluator(RNG.randomRange(), me));
		list.add(new NotOutRangingEvaluator(RNG.randomRange(), me));
		list.add(new ClashMaximizingEvaluator(RNG.randomRange(), me));
		list.add(new HealthReversalEvaluator(me));
		list.add(new SiderealLadderEvaluator(RNG.randomRange(),me));
		//list.add(new DefeatMinimizingEvaluator(RNG.randomRange(), me));
		evaluators = list.toArray(new IEvaluator[0]);
	}
	
	@Override
	public void chooseAction(Context ctx, ITechniqueSelectionCallback callback) {
		if (evaluators == null) {
			setupEvaluators();
		}
		
		determineLegalTechniques();
		
		double[] finalEvaluation = new double[legalTechniqueList.length];
		for (IEvaluator evaluator : evaluators) {
			if (!evaluator.hasOpinionOnTechniques()) continue;
			double[] evaluation = evaluator.getWeightedTechniqueEvaluation(legalTechniqueList);
			for (int i = 0; i != legalTechniqueList.length; i++) {
				finalEvaluation[i] += evaluation[i];
			}
		}
		IKnownTechnique favorite = null;
		double highest = 0;
		for (int i = 0; i != legalTechniqueList.length; i++) {
			if (finalEvaluation[i] > highest) {
				highest = finalEvaluation[i];
				favorite = legalTechniqueList[i];
			}
		}
		MutableTechnique technique = new MutableTechnique(favorite.getTechnique());
		while (technique.getOptions().length > 0) {
			TechniqueOptions options = technique.getOptions()[0];
			ITechniqueOptionPick legalPick = options.getSingleLegalPick(BattleEngine.getInstance().getCurrentCharacter());
	    	if (legalPick != null) {
	    		technique.applyOptionPick(legalPick);
	    	} else {
				int pick = RNG.random(options.getPicks().length);
				technique.applyOptionPick(options.getPicks()[pick]);
	    	}
		}
		callback.choose(new ChosenAction(technique));
	}

	@Override
	public void reviewRound(Context ctx, final IPreviewRoundCallback callback) {
		if (BattleEngine.getInstance().canReversal(me)) {
			int reversalVotes = 0;
			int reversalTotalVotes = 0;
			for (IEvaluator evaluator : evaluators) {
				if (evaluator.hasOpinionOnReversal()) {
					reversalTotalVotes++;
					if (evaluator.wantsToUseReversal()) {
						reversalVotes++;
					}
				}
			}
			double result = reversalVotes / reversalTotalVotes;
			if (result > .5) {
				BattleEngine.getInstance().doReversal(me, new ITechniqueSelectionCallback() {
					@Override
					public void choose(ChosenAction action) {
						action.useReversal();
						callback.choose(action);
					}
				});
				return;
			}
		}
		callback.choose(null);
	}

}
