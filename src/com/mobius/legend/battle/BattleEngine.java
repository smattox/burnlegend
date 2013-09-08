package com.mobius.legend.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.mobius.legend.battle.brain.IPreviewRoundCallback;
import com.mobius.legend.battle.brain.ITechniqueSelectionCallback;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.battle.effect.ModTraitEffect;
import com.mobius.legend.battle.effect.MustClashEffect;
import com.mobius.legend.battle.effect.StaggerCheckEffect;
import com.mobius.legend.battle.ui.RoundResultsActivity;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.TraitType;
import com.mobius.legend.management.CharacterManagement;
import com.mobius.legend.technique.Movement;
import com.mobius.legend.technique.TechniqueConflictResult;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueTag;
import com.mobius.legend.utilities.RNG;
import com.mobius.legend.utilities.StringUtils;

public class BattleEngine {
	private Context appContext;
	
	private boolean isRunning;
	private BattleCharacter[] characters;
	private Map<Pair<ICharacter, ICharacter>, Boolean> distanceMap = new HashMap<Pair<ICharacter, ICharacter>, Boolean>();
	private String results;
	
	private static BattleEngine instance;
	
	private BattleCharacter currentCharacter;
	
	private Map<Integer, GameRound> roundHistory = new HashMap<Integer, GameRound>(); 
	private int currentRound;
	
	public void setupBattle(BattleCharacter... characters) {
		this.characters = characters;
		
		distanceMap.clear();

		distanceMap.put(new Pair<ICharacter, ICharacter>(characters[0], characters[1]), false);
		
		roundHistory.clear();
		currentRound = 0;
		
		isRunning = true;
	}
	
	public void begin() {
		doRound();
	}
	
	public void setContext(Context ctx) {
		appContext = ctx;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public BattleCharacter getCurrentCharacter() {
		return currentCharacter;
	}
	
	public ICharacter[] getCharacters() {
		return characters;
	}
	
	public GameRound getPreviousRound() {
		return getRound(getCurrentRoundNumber() - 1);
	}
	
	public GameRound getCurrentRound() {
		return getRound(getCurrentRoundNumber());
	}
	
	public GameRound getRound(int number) {
		GameRound round = roundHistory.get(number);
		if (round == null) {
			round = new GameRound();
			roundHistory.put(number, round);
		}
		return round;
	}
	
	public int getCurrentRoundNumber() {
		return currentRound;
	}
	
	public boolean isDistant(ICharacter one, ICharacter two) {
		return distanceMap.get(getDistancePair(one, two));
	}
	
	public void setDistant(ICharacter one, ICharacter two, boolean distant) {
		distanceMap.put(getDistancePair(one, two), distant);
	}
	
	public int getOpponentCount() {
		int count = 0;
		for (BattleCharacter character : characters) {
			if (!character.getStatus().isUnconcious()) {
				count++;
			}
		}
		return count - 1;
	}
	
	public BattleCharacter getOpponent(ICharacter you) {
		for (BattleCharacter character : characters) {
			if (character != you) {
				return character;
			}
		}
		return null;
	}
	
	public boolean canReversal(BattleCharacter character) {
		CharacterStatus status = character.getStatus();
		if (!status.hasReversal() || status.getWillpower() == 0 &&
			!getActionForCharacter(getOpponent(character)).getTechnique().hasTag(TechniqueTag.ImmuneToReversal) &&
			!getActionForCharacter(getOpponent(character)).getTechnique().hasTag(TechniqueTag.Overdrive)) {
			return false;
		}
		return true;
	}
	
	public void doReversal(BattleCharacter character, ITechniqueSelectionCallback callback) {
		character.getStatus().applyEffect(new MustClashEffect());
		character.getStatus().setReversal(false);
		character.getStatus().getTrait(TraitType.Willpower).adjustValue(-1);
		character.getBrain().chooseAction(appContext, callback);
	}
	
	private Pair<ICharacter, ICharacter> getDistancePair(ICharacter one, ICharacter two) {
		Pair<ICharacter, ICharacter> pair = new Pair<ICharacter, ICharacter>(one, two);
		if (!distanceMap.containsKey(pair)) {
			pair = new Pair<ICharacter, ICharacter>(two, one);
		}
		return pair;
	}
	
	public boolean isLegalTechnique(BattleCharacter attacker, Technique t1, BattleCharacter defender, Technique t2) {
		CharacterStatus status = attacker.getStatus();
		if (t1.getKiCost() > status.getKi()) {
			return false;
		}
		if (t1.getWillpowerCost(status) > status.getWillpower()) {
			return false;
		}
		if (t1.hasTag(TechniqueTag.Overdrive) && status.getOverdrive() < CharacterStatus.MAX_OVERDRIVE) {
			return false;
		}
		ChosenAction lastAction = getPreviousRound().getActionForCharacter(attacker);
		Technique lastTechnique = lastAction != null ? lastAction.getTechnique() : null;
		if (t1.hasTag(TechniqueTag.Celestial) || t1.hasTag(TechniqueTag.Sidereal)) {
			if (lastTechnique == null ||
				!getPreviousRound().wasSuccessful(attacker) ||
	 			(t1.hasTag(TechniqueTag.Celestial) && !lastTechnique.hasTag(TechniqueTag.Terrestrial)) ||
	 			(t1.hasTag(TechniqueTag.Sidereal)  && !lastTechnique.hasTag(TechniqueTag.Celestial))) {
				return false;
	 		}
		}
		if (attacker.getType() == CharacterType.Okami &&
			((attacker.isTrueForm() && t1.getStyle().getType() == CharacterType.Mortal && !t1.getStyle().getName().equals(CharacterManagement.BASIC_STYLE)) ||
			 (!attacker.isTrueForm() && t1.getStyle().getType() == CharacterType.Okami))) {
				return false;
			}
		for (ISpecialEffect effect : status.getEffects()) {
			if (!effect.allowTechnique(attacker, t1, defender, t2)) {
				return false;
			}
		}
		return true;
	}
	
	public void doRound() {
		currentRound++;
		currentCharacter = characters[0];
		
		doSelection();
	}
	
	private void nextCharacter() {
		for (int i = 0; i != characters.length; i++) {
			if (characters[i] == currentCharacter) {
				if (i == characters.length - 1) {
					currentCharacter = null;
				} else {
					currentCharacter = characters[i + 1];
				}
				return;
			}
		}
	}
	
	private void doSelection() {
		if (currentCharacter == null) {
			currentCharacter = characters[0];
			doReviewRound();
			return;
		}
		currentCharacter.getBrain().chooseAction(appContext, new ITechniqueSelectionCallback() {

			@Override
			public void choose(ChosenAction action) {
				if (!action.getTechnique().isReflexive()) {
					getCurrentRound().setAction(currentCharacter, action);
					nextCharacter();
					doSelection();
				} else {
					for (ISpecialEffect effect : action.getTechnique().getEffects()) {
						effect.onApply(currentCharacter, action.getTechnique());
					}
					doSelection();
				}
			}
		});
	}
	
	private void doReviewRound() {
		if (currentCharacter == null) {
			currentCharacter = characters[0];
			computeResults();
			doProcessRound();
			return;
		}
		
		currentCharacter.getBrain().reviewRound(appContext, new IPreviewRoundCallback() {

			@Override
			public void choose(ChosenAction action) {
				if (action != null) {
					getCurrentRound().setAction(currentCharacter, action);
				}
				nextCharacter();
				doReviewRound();
			}
		});
	}
	
	private void doProcessRound() {
		
	}
	
	public String getRoundReview() {
		StringBuilder builder = new StringBuilder();
		for (Pair<BattleCharacter, BattleCharacter> matchup : getMatchups()) {
			Technique one = getCurrentRound().getActionForCharacter(matchup.first).getTechnique();
			Technique two = getCurrentRound().getActionForCharacter(matchup.second).getTechnique();
			builder.append("\n" + matchup.first.getName() + " will use " +
						   one.getName() + ".");
			builder.append("\n" + matchup.second.getName() + " will use " +
					   two.getName() + ".");
			builder.append("\n");
			TechniqueConflictResult result = one.getInteraction(two, isDistantForTechnique(matchup.first, one, matchup.second, two));
			switch (result) {
			case MutualOutOfRange:
				builder.append("Out of range!\n");
			case NonEvent:
				builder.append("Non-Event!");
				break;
			case Outranges:
				builder.append(one.getName() + " outranges " + two.getName() + "!");
				break;
			case Outranged:
				builder.append(two.getName() + " outranges " + one.getName() + "!");
				break;
			case Defeats:
				builder.append(one.getName() + " defeats " + two.getName() + "!");
				break;
			case Defeated:
				builder.append(two.getName() + " defeats " + one.getName() + "!");
				break;	
			case Clash:
				builder.append("Clash!");
				break;
			case HitTrade:
				builder.append("Hit Trade!");
				break;
			}
		}
		return builder.toString();
		
	}
	
	private boolean isDistantForTechnique(BattleCharacter one, Technique t1, BattleCharacter two, Technique t2) {
		return (isDistant(one, two)) &&
			   (t1.getMovement() != Movement.Advance && t2.getMovement() != Movement.Advance);
	}
	
	private void computeResults() {
		StringBuilder builder = new StringBuilder();
		for (Pair<BattleCharacter, BattleCharacter> matchup : getMatchups()) {
			if (getCurrentRound().getActionForCharacter(matchup.first).getReversal()) {
				builder.append("\n" + matchup.first.getName() + " uses Reversal!");
			}
			if (getCurrentRound().getActionForCharacter(matchup.second).getReversal()) {
				builder.append("\n" + matchup.second.getName() + " uses Reversal!");
			}
			BattleCharacter top = matchup.first;
			BattleCharacter bottom = matchup.second;
			Technique one = getCurrentRound().getActionForCharacter(matchup.first).getTechnique();
			Technique two = getCurrentRound().getActionForCharacter(matchup.second).getTechnique();
			builder.append("\n" + matchup.first.getName() + " uses " +
						   one.getName() + ".");
			builder.append("\n" + matchup.second.getName() + " uses " +
					   two.getName() + ".");
			
			top.getStatus().getTrait(TraitType.Ki).adjustValue(-one.getKiCost());
			top.getStatus().getTrait(TraitType.Willpower).adjustValue(-one.getWillpowerCost(top.getStatus()));
			top.getStatus().getTrait(TraitType.Overdrive).adjustValue(one.hasTag(TechniqueTag.Overdrive) ? -CharacterStatus.MAX_OVERDRIVE : 0);
			bottom.getStatus().getTrait(TraitType.Ki).adjustValue(-two.getKiCost());
			bottom.getStatus().getTrait(TraitType.Willpower).adjustValue(-two.getWillpowerCost(bottom.getStatus()));
			bottom.getStatus().getTrait(TraitType.Overdrive).adjustValue(two.hasTag(TechniqueTag.Overdrive) ? -CharacterStatus.MAX_OVERDRIVE : 0);
			
			TechniqueConflictResult result = one.getInteraction(two, isDistantForTechnique(matchup.first, one, matchup.second, two));
			int firstEffectiveDamage = 0;
			int secondEffectiveDamage = 0;
			boolean firstSuccess = false;
			boolean secondSuccess = false;
			
			for (ISpecialEffect effect : top.getStatus().getEffects()) {
				log(effect.preCompare(matchup.first, one, matchup.second, two), builder);
			}
			for (ISpecialEffect effect : bottom.getStatus().getEffects()) {
				log(effect.preCompare(matchup.second, two, matchup.first, one), builder);
			}
			
			one.applyEffects(matchup.first);
			two.applyEffects(matchup.second);
			
			if (!top.getStatus().isStaggered() && !bottom.getStatus().isStaggered()) {
				switch (result) {
				case MutualOutOfRange:
					builder.append("\nOut of range!");
				case NonEvent:
					builder.append("\nNon-Event!");
					break;
				case Outranges:
					builder.append("\n" + one.getName() + " outranges " + two.getName() + "!");
					firstEffectiveDamage = one.getEffectiveDamage(matchup.first, matchup.second);
					firstSuccess = true;
					break;
				case Defeats:
					builder.append("\n" + one.getName() + " defeats " + two.getName() + "!");
					firstEffectiveDamage = one.getEffectiveDamage(matchup.first, matchup.second);
					firstSuccess = true;
					break;
				case Outranged:
					builder.append("\n" + two.getName() + " outranges " + one.getName() + "!");
					secondEffectiveDamage = two.getEffectiveDamage(matchup.second, matchup.first);
					secondSuccess = true;
					break;	
				case Defeated:
					builder.append("\n" + two.getName() + " defeats " + one.getName() + "!");
					secondEffectiveDamage = two.getEffectiveDamage(matchup.second, matchup.first);
					secondSuccess = true;
					break;	
				case Clash:
					builder.append("\nClash!");
					for (int i = 0; i != top.getStatus().getEffects().length; i++) {
						log(top.getStatus().getEffects()[i].preClash(matchup.first, one, matchup.second, two), builder);
					}
					int firstDice = one.getEffectiveClash(matchup.first, matchup.second);
					for (int i = 0; i != bottom.getStatus().getEffects().length; i++) {
						log(bottom.getStatus().getEffects()[i].preClash(matchup.second, two, matchup.first, one), builder);
					}
					int secondDice = two.getEffectiveClash(matchup.second, matchup.first);
					int first = roll(firstDice, builder);
					builder.append("\nvs.");
					int second = roll(secondDice, builder);
					
					getCurrentRound().setClashSuccesses(matchup.first, first);
					getCurrentRound().setClashSuccesses(matchup.second, second);
					
					if (first > second) {
						builder.append("\n" + matchup.first.getName() + " is Victorious!");
						firstEffectiveDamage = one.getEffectiveDamage(matchup.first, matchup.second);
						firstSuccess = true;
						break;
					}
					if (second > first) {
						builder.append("\n" + matchup.second.getName() + " is Victorious!");
						secondEffectiveDamage = two.getEffectiveDamage(matchup.second, matchup.first);
						secondSuccess = true;
						break;
					}
				case HitTrade:
					builder.append("\nHit Trade!");
					firstEffectiveDamage = one.getEffectiveDamage(matchup.first, matchup.second);
					secondEffectiveDamage = two.getEffectiveDamage(matchup.second, matchup.first);
					firstSuccess = true;
					secondSuccess = true;
					break;
				}
			} else {
				if (top.getStatus().isStaggered() && bottom.getStatus().isStaggered()) {
					builder.append("\nBoth combatants are staggered!");
					builder.append("\nNon-event!");
				} else if (bottom.getStatus().isStaggered()) {
					builder.append("\n" + matchup.first.getName() + " is Victorious!");
					firstEffectiveDamage = one.getEffectiveDamage(matchup.first, matchup.second);
					firstSuccess = true;
				} else {
					builder.append("\n" + matchup.second.getName() + " is Victorious!");
					secondEffectiveDamage = two.getEffectiveDamage(matchup.second, matchup.first);
					secondSuccess = true;
				}
			}
			
			for (int i = 0; i != matchup.first.getStatus().getEffects().length; i++) {
				ISpecialEffect effect = matchup.first.getStatus().getEffects()[i];
				if (firstSuccess) {
					log(effect.onVictory(matchup.first, matchup.second), builder);
				} else {
					log(effect.onDefeat(matchup.first, matchup.second), builder);
				}
			}
			for (int i = 0; i != matchup.second.getStatus().getEffects().length; i++) {
				ISpecialEffect effect = matchup.second.getStatus().getEffects()[i];
				if (secondSuccess) {
					log(effect.onVictory(matchup.second, matchup.first), builder);
				} else {
					log(effect.onDefeat(matchup.second, matchup.first), builder);
				}
			}
			getCurrentRound().recordSuccess(matchup.first, firstSuccess);
			getCurrentRound().recordSuccess(matchup.second, secondSuccess);
			
			if (firstEffectiveDamage > 0) {
				getCurrentRound().setDamagePool(matchup.first, firstEffectiveDamage);
				applyDamage(matchup.first, matchup.first.getName(), firstEffectiveDamage, one, matchup.second, builder);
			}
			if (secondEffectiveDamage > 0) {
				getCurrentRound().setDamagePool(matchup.second, secondEffectiveDamage);
				applyDamage(matchup.second, matchup.second.getName(), secondEffectiveDamage, two, matchup.first, builder);
			}
			
			for (ISpecialEffect effect : top.getStatus().getEffects()) {
				log(effect.onClosure(top), builder);
			}
			for (ISpecialEffect effect : bottom.getStatus().getEffects()) {
				log(effect.onClosure(bottom), builder);
			}
		}
		
		for (BattleCharacter character : characters) {
			if (getCurrentRound().getDamageSuffered(character) > 0) {
				character.getStatus().applyHit();
				if (character.getStatus().getRoundsHit() >= CharacterStatus.ROUNDS_TO_STAGGER) {
					character.getStatus().applyEffect(new StaggerCheckEffect());
				}
			} else {
				character.getStatus().resetRoundsHit();
			}
			character.getStatus().finishRound();
		}
		
		BattleCharacter last = null;
		int count = 0;
		for (BattleCharacter character : characters) {
			if (!character.getStatus().isUnconcious()) {
				last = character;
				count++;
			}
		}
		if (count == 1) {
			builder.append("\n" + last.getName() + " wins the battle!");
			isRunning = false;
		}
		if (count == 0) {
			builder.append("\nDraw!");
			isRunning = false;
		}
		
		results = builder.toString();
		
		Intent activity = new Intent(appContext, RoundResultsActivity.class);
		appContext.startActivity(activity);
	}
	
	public String[] applyAdditionalDamage(String attackString, int amount, ICharacter defender) {
		StringBuilder builder = new StringBuilder();
		applyDamage(null, attackString, amount, null, defender, builder);
		return builder.toString().split("\n");
	}
	
	private void applyDamage(ICharacter attacker, String attackString, int amount, Technique technique, ICharacter defender, StringBuilder builder) {
		CharacterStatus status = defender.getStatus();
		if (technique != null && technique.hasTag(TechniqueTag.DestroyHealthStock)) {
			destroyHealthStock(defender, status, builder);
		}
		
		builder.append("\n" + attackString + "'s damage:");
		int damageDealt = roll(amount, builder);
		damageDealt += technique != null ? technique.getDamageMod() : 0;
		if (damageDealt > 0) {
			builder.append("\n" + attackString + " inflicts " + damageDealt +
				" damage to " + defender.getName() + "!");
			if (attacker != null) {
				attacker.getStatus().applyEffect(new ModTraitEffect(TraitType.Overdrive, 1));
			}
			
			getCurrentRound().recordDamageSuffered(defender, damageDealt);
			if (attacker != null) {
				getCurrentRound().recordDamageInflicted(attacker, damageDealt);
			}
		} else {
			builder.append("\nNo damage!");
		}
		while (!status.isUnconcious() && damageDealt > 0) {
			int inflict = Math.min(damageDealt, status.getHealth());
			damageDealt -= inflict;
			status.getTrait(TraitType.Health).adjustValue(-inflict);
			if (status.getHealth() == 0) {
				destroyHealthStock(defender, status, builder);
			}
		}
	}
	
	private void destroyHealthStock(ICharacter defender, CharacterStatus status, StringBuilder builder) {
		status.getTrait(TraitType.Stocks).adjustValue(-1);
		status.applyLostHealthStock();
		status.getTrait(TraitType.Health).adjustValue(CharacterStatus.MAX_HEALTH);
		builder.append("\n" + defender.getName() + " lost a Health Stock!");
		if (roll(status.getStocks(), builder) == 0) {
			builder.append("\n" + defender.getName() + " falls unconcious!");
			status.setUnconcious(true);
		} else {
			builder.append("\n" + defender.getName() + " remains standing!");
		}
	}
	
	private int roll(int diceCount, StringBuilder results) {
		if (diceCount > 0) {
			int[] dice = RNG.rollExalted(diceCount);
			results.append(" " + StringUtils.rollString(dice));
			return RNG.evaluateExalted(dice);
		} else {
			results.append(" No dice...");
			return 0;
		}
	}
	
	public String getResults() {
		return results;
	}
	
	public void log (String[] lines, StringBuilder builder) {
		if (lines == null || lines.length == 0) {
			return;
		}
		for (String line : lines) {
			builder.append("\n" + line);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Pair<BattleCharacter, BattleCharacter>[] getMatchups() {
		List<Pair<BattleCharacter, BattleCharacter>> list = new ArrayList<Pair<BattleCharacter, BattleCharacter>>();
		list.add(new Pair<BattleCharacter, BattleCharacter>(characters[0], characters[1]));
		return list.toArray(new Pair[0]);
	}
	
	public ChosenAction getActionForCharacter(ICharacter character) {
		return getCurrentRound().getActionForCharacter(character);
	}
	
	public static BattleEngine getInstance() {
		if (instance == null) {
			instance = new BattleEngine();
		}
		return instance;
	}
}
