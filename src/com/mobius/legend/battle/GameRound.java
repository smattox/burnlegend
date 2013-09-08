package com.mobius.legend.battle;

import java.util.HashMap;
import java.util.Map;

import com.mobius.legend.character.ICharacter;

public class GameRound {
	private Map<ICharacter, ChosenAction> actionList = new HashMap<ICharacter, ChosenAction>();
	private Map<ICharacter, Integer> clashSuccesses = new HashMap<ICharacter, Integer>();
	private Map<ICharacter, Integer> damagePool = new HashMap<ICharacter, Integer>();
	private Map<ICharacter, Integer> sufferedDamage = new HashMap<ICharacter, Integer>();
	private Map<ICharacter, Integer> inflictedDamage = new HashMap<ICharacter, Integer>();
	private Map<ICharacter, Boolean> successful = new HashMap<ICharacter, Boolean>();
	
	public ChosenAction getActionForCharacter(ICharacter character) {
		return actionList.get(character);
	}
	
	public void setAction(ICharacter currentCharacter, ChosenAction action) {
		actionList.put(currentCharacter, action);
	}
	
	public void recordDamageSuffered(ICharacter character, int amount) {
		sufferedDamage.put(character, amount);
	}
	
	public void recordDamageInflicted(ICharacter character, int amount) {
		inflictedDamage.put(character, amount);
	}
	
	public int getDamageSuffered(ICharacter character) {
		Integer damage = sufferedDamage.get(character);
		return damage != null ? damage : 0;
	}
	
	public int getDamageInflicted(ICharacter character) {
		Integer damage = inflictedDamage.get(character);
		return damage != null ? damage : 0;
	}
	
	public void recordSuccess(ICharacter character, boolean success) {
		successful.put(character, success);
	}

	public boolean wasSuccessful(ICharacter character) {
		return successful.get(character);
	}

	public int getClashSuccesses(ICharacter character) {
		Integer val = clashSuccesses.get(character);
		return val != null ? val : 0;
	}
	
	public void setClashSuccesses(ICharacter character, int amount) {
		clashSuccesses.put(character, amount);
	}
	
	public int getDamagePool(ICharacter character) {
		Integer val = damagePool.get(character);
		return val != null ? val : 0;
	}
	
	public void setDamagePool(ICharacter character, int amount) {
		damagePool.put(character, amount);
	}
	
}
