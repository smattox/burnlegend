package com.mobius.legend.battle.effect;

import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.technique.Technique;

public interface ISpecialEffect {
	public boolean allowTechnique(BattleCharacter attacker, Technique t1, BattleCharacter defender, Technique t2);
	
	public String[] onApply(ICharacter target, Technique technique);
	
	public String[] preCompare(ICharacter original, Technique t1, ICharacter defender, Technique t2);
	
	public String[] preClash(ICharacter original, Technique t1, ICharacter defender, Technique t2);
	
	public String[] onVictory(ICharacter original, ICharacter defender);
	
	public String[] onDefeat(ICharacter original, ICharacter defender);
	
	public String[] onClosure(ICharacter original);
	
	public boolean stacks(ISpecialEffect effect);
	
	public boolean isComplete();
	
	public String getStatusString();
	
	public String getLogApplyString(ICharacter character);
	
	public String getTechniqueDisplayString();
	
	public ISpecialEffect cloneEffect();
}
