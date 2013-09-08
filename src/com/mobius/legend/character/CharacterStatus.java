package com.mobius.legend.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobius.legend.battle.effect.Modifier;
import com.mobius.legend.battle.effect.ISpecialEffect;

public class CharacterStatus {
	public static final int MAX_HEALTH = 7;
	public static final int MAX_KI = 5;
	public static final int MAX_WILLPOWER = 5;
	public static final int MAX_OVERDRIVE = 10;
	
	public static final int ROUNDS_TO_STAGGER = 3;

	private int startingStocks;
	
	private int willpowerForKiRounds = 0;
	private int willpowerToUseProjectilesRounds = 0;
	private boolean hasInfiniteKi=false;

	private boolean unconcious = false;
	private boolean lostHealthStockThisRound = false;
	private boolean hasReversal = true;
	
	private int roundsHit = 0;
	private boolean isStaggered = false;
	
	private Map<TraitType, ITrait> traits = new HashMap<TraitType, ITrait>();
	
	private List<ISpecialEffect> statusEffects = new ArrayList<ISpecialEffect>();
	private List<Modifier> clashMods = new ArrayList<Modifier>();
	private List<Modifier> damageMods = new ArrayList<Modifier>();
	
	public CharacterStatus(int stocks) {
		this.startingStocks = stocks;
		
		traits.put(TraitType.Health, new Trait(MAX_HEALTH, MAX_HEALTH));
		traits.put(TraitType.Stocks, new Trait(stocks, stocks));
		traits.put(TraitType.Ki, new Trait(MAX_KI, MAX_KI));
		traits.put(TraitType.Willpower, new Trait(MAX_WILLPOWER, MAX_WILLPOWER));
		traits.put(TraitType.Overdrive, new Trait(MAX_OVERDRIVE));
		traits.put(TraitType.Stagger, new Trait(ROUNDS_TO_STAGGER));
	}
	
	public int getHealth() {
		return getTrait(TraitType.Health).getValue();
	}
	
	public int getStocks() {
		return getTrait(TraitType.Stocks).getValue();
	}
	
	public int getStartingStocks() {
		return startingStocks;
	}
	
	public int getKi() {
		return getTrait(TraitType.Ki).getValue();
	}
	
	public int getWillpower() {
		return getTrait(TraitType.Willpower).getValue();
	}
	
	public int getOverdrive() {
		return getTrait(TraitType.Overdrive).getValue();
	}
	
	public ITrait getTrait(TraitType type) {
		return traits.get(type);
	}
	
	public boolean isUnconcious() {
		return unconcious;
	}
	
	public boolean hasReversal() {
		return hasReversal;
	}
	
	public int modTrait(TraitType trait, int amount) {
		return getTrait(trait).adjustValue(amount);
	}
	
	public void setUnconcious(boolean val) {
		unconcious = val;
	}
	
	public void setReversal(boolean val) {
		hasReversal = val;
	}
	
	public void applyEffect(ISpecialEffect newEffect) {
		for (ISpecialEffect effect : statusEffects) {
			if (!effect.stacks(newEffect)) {
				return;
			}
		}
		statusEffects.add(newEffect);
	}
	
	public ISpecialEffect[] getEffects() {
		return statusEffects.toArray(new ISpecialEffect[0]);
	}
	
	public void applyClashMod(Modifier mod) {
		clashMods.add(mod);
	}
	
	public void applyDamageMod(Modifier mod) {
		damageMods.add(mod);
	}
	
	public Modifier[] getClashMods(boolean allowPenalties) {
		if (!allowPenalties) {
			List<Modifier> toRemove = new ArrayList<Modifier>();
			for (Modifier mod : clashMods) {
				if (mod.getMod() < 0) {
					toRemove.add(mod);
				}
			}
			clashMods.removeAll(toRemove);
		}
		return clashMods.toArray(new Modifier[0]);
	}
	
	public Modifier[] getDamageMods(boolean allowPenalties) {
		if (!allowPenalties) {
			List<Modifier> toRemove = new ArrayList<Modifier>();
			for (Modifier mod : damageMods) {
				if (mod.getMod() < 0) {
					toRemove.add(mod);
				}
			}
			damageMods.removeAll(toRemove);
		}
		return damageMods.toArray(new Modifier[0]);
	}
	
	public int getRoundsHit() {
		return roundsHit;
	}
	
	public void resetRoundsHit() {
		roundsHit = 0;
	}
	
	public void applyHit() {
		roundsHit++;
	}
	
	public boolean isStaggered() {
		return isStaggered;
	}
	
	public void applyStaggered() {
		isStaggered = true;
	}
	
	public boolean hasLostHealthStockThisRound() {
		return lostHealthStockThisRound;
	}
	
	public void applyLostHealthStock() {
		lostHealthStockThisRound = true;
	}
	
	public void applyWillpowerForKiRound() {
		willpowerForKiRounds++;
	}
	
	public boolean isWillpowerForKi() {
		return willpowerForKiRounds > 0;
	}
	
	public void applyWillpowerToUseProjectilesRound() {
		willpowerForKiRounds++;
	}
	
	public boolean isWillpowerToUseProjectiles() {
		return willpowerToUseProjectilesRounds > 0;
	}
	
	public void setInfiniteKi(boolean val) {
		hasInfiniteKi = val;
	}
	
	public boolean hasInfiniteKi() {
		return hasInfiniteKi;
	}
	
	public void finishRound() {
		List<ISpecialEffect> remove = new ArrayList<ISpecialEffect>();
		for (ISpecialEffect effect : statusEffects) {
			if (effect.isComplete()) {
				remove.add(effect);
			}
		}
		statusEffects.removeAll(remove);
		clashMods.clear();
		damageMods.clear();
		
		isStaggered = false;
		lostHealthStockThisRound = false;
		
		if (isWillpowerForKi()) {
			willpowerForKiRounds--;
		}
		if (isWillpowerToUseProjectiles()) {
			willpowerToUseProjectilesRounds--;
		}
	}

	public void removeEffect(ISpecialEffect effect) {
		statusEffects.remove(effect);
	}
	
	public String toString() {
		return "HP: " + getHealth() + "/" + MAX_HEALTH + "; HS: " + getStocks() + "/" + startingStocks
		   + "; KI: " + getKi()     + "/" + MAX_KI + "; WP: " + getWillpower() + "/" + MAX_WILLPOWER
		   + "; OD: " + getOverdrive() + "/" + MAX_OVERDRIVE + "; ST: " + roundsHit + "/" + ROUNDS_TO_STAGGER;
	}
}
