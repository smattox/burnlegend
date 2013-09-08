package com.mobius.legend.management;

import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;

public class CharacterManagement {
	public static CharacterData character;
	
	public static final String CHARACTER_EXTENSION = ".blc";
	
	public static final int CREATION_ATTRIBUTE_DOTS = 7;
	public static final int STARTING_ATTRIBUTE_RATING = 1;
	public static final int CREATION_BASIC_MA_DOTS = 7;
	public static final int STARTING_BASIC_MA_DOTS = 2;
	public static final int CREATION_OTHER_MA_DOTS = 15;
	public static final int STARTING_OTHER_MA_DOTS = 0;
	public static final int CREATION_TRAIT_MAX = 5;
	public static final String BASIC_STYLE = "Basic";
	
	public static final int ADVANCEMENT_TRAIT_MAX = 10;
	public static final int ATTRIBUTE_XP_MULTIPLIER = 4;
	public static final int BASIC_TECHNIQUE_XP_MULTIPLIER = 2;
	public static final int OTHER_TECHNIQUE_XP_MULTIPLIER = 3;
	public static final int NEW_TECHNIQUE = 4;
	
	public static final int ABSOLUTE_MAX_STOCKS = 5;
	public static final int ABSOLUTE_MAX_WILLPOWER = 5;
	
	public static boolean isCreationComplete() {
		return getSpentCreationAttributeCount() == CREATION_ATTRIBUTE_DOTS &&
			   getSpentCreationBasicStyleCount() == CREATION_BASIC_MA_DOTS &&
			   getSpentCreationOtherStyleCount() == CREATION_OTHER_MA_DOTS;
	}
	
	public static int getSpentCreationAttributeCount() {
		int total = 0;
		for (Attribute attribute : Attribute.values()) {
			total += character.getAttribute(attribute) - STARTING_ATTRIBUTE_RATING;
		}
		return total;
	}
	
	public static int getSpentCreationBasicStyleCount() {
		int total = 0;
		for (IKnownTechnique knownTechnique : character.getKnownTechniquesForStyle(TechniqueRegistry.getInstance().getStyle(BASIC_STYLE))) {
			total += knownTechnique.getRating() - STARTING_BASIC_MA_DOTS;
		}
		return total;
	}
	
	public static int getSpentCreationOtherStyleCount() {
		int total = 0;
		for (Style style : character.getKnownStyles()) {
			if (style.getName().equals(CharacterManagement.BASIC_STYLE)) {
				continue;
			}
			for (IKnownTechnique knownTechnique : character.getKnownTechniquesForStyle(style)) {
				total += knownTechnique.getRating() - STARTING_OTHER_MA_DOTS;
			}
		}
		return total;
	}
	
	public static int getTotalXPSpent() {
		return getTotalXPSpent(character);
	}
	
	public static int getTotalXPSpent(ICharacter character) {
		return  CharacterManagement.getXPSpentOnAttributes(character) +
				CharacterManagement.getXPSpentOnMartialArts(character) +
				CharacterManagement.getXPSpentOnRefinements(character);
	}
	
	public static int getXPSpentOnAttributes() {
		return getXPSpentOnAttributes(character);
	}
	
	public static int getXPSpentOnAttributes(ICharacter character) {
		int total = 0;
		for (Attribute attribute : Attribute.values()) {
			for (int i = character.getAttribute(attribute, true); i < character.getAttribute(attribute); i++) {
				total += getXPCostForNextAttributeDot(i);
			}
		}
		return total;
	}
	
	public static int getXPCostForNextAttributeDot(int currentDots) {
		return ATTRIBUTE_XP_MULTIPLIER * currentDots;
	}
	
	public static int getXPSpentOnMartialArts() {
		return getXPSpentOnBasicTechniques(character) +
			   getXPSpentOnOtherTechniques(character);
	}
	
	public static int getXPSpentOnMartialArts(ICharacter character) {
		return getXPSpentOnBasicTechniques(character) +
			   getXPSpentOnOtherTechniques(character);
	}
	
	public static int getXPSpentOnBasicTechniques() {
		return getXPSpentOnBasicTechniques(character);
	}
	
	public static int getXPSpentOnBasicTechniques(ICharacter character) {
		int total = 0;
		for (Style style : character.getKnownStyles()) {
			if (!style.getName().equals(CharacterManagement.BASIC_STYLE)) {
				continue;
			}
			for (IKnownTechnique knownTechnique : character.getKnownTechniquesForStyle(style)) {
				for (int i = knownTechnique.getRating(true); i < knownTechnique.getRating(); i++) {
					total += getXPCostForNextTechniqueDot(knownTechnique.getTechnique(), character, i);
				}
			}
		}
		return total;
	}
	
	public static int getXPSpentOnOtherTechniques() {
		return getXPSpentOnOtherTechniques(character);
	}
	
	public static int getXPSpentOnOtherTechniques(ICharacter character) {
		int total = 0;
		for (Style style : character.getKnownStyles()) {
			if (style.getName().equals(CharacterManagement.BASIC_STYLE)) {
				continue;
			}
			for (IKnownTechnique knownTechnique : character.getKnownTechniquesForStyle(style)) {
				for (int i = knownTechnique.getRating(true); i < knownTechnique.getRating(); i++) {
					total += getXPCostForNextTechniqueDot(knownTechnique.getTechnique(), character, i);
				}
			}
		}
		return total;
	}
	
	public static int getXPCostForNextTechniqueDot(Technique technique, ICharacter character, int currentDots) {
		if (technique.getStyle().getName().equals(BASIC_STYLE) || technique.isDiscounted(character)) {
			return BASIC_TECHNIQUE_XP_MULTIPLIER * currentDots;
		} else {
			return currentDots == 0 ? NEW_TECHNIQUE : OTHER_TECHNIQUE_XP_MULTIPLIER * currentDots;
		}
	}
	
	public static int getXPSpentOnRefinements() {
		return getXPSpentOnRefinements(character);
	}
	
	public static int getXPSpentOnRefinements(ICharacter character) {
		int total = 0;
		for (IKnownTechnique technique : character.getKnownTechniques()) {
			for (Refinement refinement : technique.getKnownRefinements()) {
				total += refinement.getCost();
			}
		}
		return total;
	}
}
