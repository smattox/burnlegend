package com.mobius.legend.battle.effect.base;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public abstract class AbstractEffect implements ISpecialEffect {
	@Override
	public boolean allowTechnique(BattleCharacter attacker, Technique t1,
			BattleCharacter defender, Technique t2) {
		return true;
	}
	
	@Override
	public String[] onApply(ICharacter original, Technique technique) {
		return null;
	}

	@Override
	public String[] preCompare(ICharacter original, Technique t1, ICharacter defender,
			Technique t2) {
		return null;
	}

	@Override
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2) {
		return null;
	}
	
	@Override
	public String[] onVictory(ICharacter original, ICharacter defender) {
		return null;
	}
	
	@Override
	public String[] onDefeat(ICharacter original, ICharacter defender) {
		return null;
	}
	
	@Override
	public String[] onClosure(ICharacter original) {
		return null;
	}
	
	@Override
	public boolean stacks(ISpecialEffect effect) {
		return true;
	}
	
	@Override
	public String getStatusString() {
		return null;
	}
	
	@Override
	public String getLogApplyString(ICharacter character) {
		return null;
	}
	
	@Override
	public String getTechniqueDisplayString() {
		return null;
	}
	
	@Override
	public String toString() {
		return getTechniqueDisplayString();
	}
}
