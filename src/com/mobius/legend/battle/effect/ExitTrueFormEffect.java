package com.mobius.legend.battle.effect;

import com.mobius.legend.battle.effect.base.AbstractCurrentRoundEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.utilities.RNG;
import com.mobius.legend.utilities.StringUtils;

public class ExitTrueFormEffect extends AbstractCurrentRoundEffect {
	
	private static final int TRANSFORMATION_DIFFICULTY = 3;
	
	@Override
	public String[] onClosure(ICharacter original) {
		int dice = original.getAttribute(Attribute.Strength) + original.getAttribute(Attribute.Stamina);
		int[] results = RNG.rollExalted(dice);
		boolean success = RNG.evaluateExalted(results) >= TRANSFORMATION_DIFFICULTY;
		if (success) {
			original.setOkamiForm(false);
		}
		return new String[] { original.getName() + " attempts to transform...",
				StringUtils.rollString(results),
				(success ? "Success!" : "Failure!") };
		}

	@Override
	public ISpecialEffect cloneEffect() {
		return new ExitTrueFormEffect();
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return "Exits True Form with Strength + Stamina (difficulty 3)";
	}
}
