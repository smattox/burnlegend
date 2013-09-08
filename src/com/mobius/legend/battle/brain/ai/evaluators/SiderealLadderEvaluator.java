package com.mobius.legend.battle.brain.ai.evaluators;

import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueTag;

public class SiderealLadderEvaluator extends AbstractTechniqueEvaluator {

	private enum SiderealTechniqueType { OTHER, TERRESTRIAL, CELESTIAL, SIDEREAL, ASCENDED };
	
	private final ICharacter me;
	
	public SiderealLadderEvaluator(double weight, ICharacter character) {
		super(weight);
		me = character;
	}
	
	@Override
	public boolean hasOpinionOnTechniques() {
		return me.getType() == CharacterType.Tennin;
	}

	@Override
	protected double[] getEvaluation(IKnownTechnique[] techniques) {
		ChosenAction action = BattleEngine.getInstance().getPreviousRound().getActionForCharacter(me);
		SiderealTechniqueType targetType = getTargetType(action != null ? action.getTechnique() : null);
		
		double[] weights = new double[techniques.length];
		for (int i = 0; i != techniques.length; i++) {
			SiderealTechniqueType techniqueType = getTechniqueType(techniques[i]);
			weights[i] = targetType != SiderealTechniqueType.TERRESTRIAL && techniqueType == targetType ? 1 : 0;
		}
		return weights;
	}
	
	private SiderealTechniqueType getTechniqueType(IKnownTechnique knownTechnique) {
		Technique technique = knownTechnique.getTechnique();
		if (technique.hasTag(TechniqueTag.Sidereal)) {
			return SiderealTechniqueType.SIDEREAL;
		}
		if (technique.hasTag(TechniqueTag.Celestial)) {
			return SiderealTechniqueType.CELESTIAL;
		}
		if (technique.hasTag(TechniqueTag.Terrestrial)) {
			return SiderealTechniqueType.TERRESTRIAL;
		}
		if (technique.hasTag(TechniqueTag.Overdrive)) {
			return SiderealTechniqueType.ASCENDED;
		}
		if (technique.getName().equals("Prayer Binding")) {
			if (knownTechnique.isRefinementLearned("Thousandfold Prayer Binding")) {
				return SiderealTechniqueType.ASCENDED;
			}
			if (knownTechnique.isRefinementLearned("Eightfold Prayer Binding")) {
				return SiderealTechniqueType.SIDEREAL;
			}
			if (knownTechnique.isRefinementLearned("Threefold Prayer Binding")) {
				return SiderealTechniqueType.CELESTIAL;
			}
		}
		return SiderealTechniqueType.OTHER;
	}
	
	private SiderealTechniqueType getTargetType(Technique technique) {
		if (technique != null) {
			if (technique.hasTag(TechniqueTag.Sidereal)) {
				return SiderealTechniqueType.ASCENDED;
			}
			if (technique.hasTag(TechniqueTag.Celestial)) {
				return SiderealTechniqueType.SIDEREAL;
			}
			if (technique.hasTag(TechniqueTag.Terrestrial)) {
				return SiderealTechniqueType.CELESTIAL;
			}
		}
		return SiderealTechniqueType.TERRESTRIAL;
	}
	

}
