package com.mobius.legend.character;

import java.util.Arrays;

import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.character.technique.UnlinkedKnownTechnique;
import com.mobius.legend.management.CharacterManagement;
import com.mobius.legend.namegenerator.INameGenerator;
import com.mobius.legend.namegenerator.ThresholdNameGenerator;
import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.StyleFilterType;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;
import com.mobius.legend.utilities.RNG;

public class RandomCharacterGenerator {
	
	private final static double MUNDANE_STYLE_WEIGHT = 1;
	private final static double EXALTED_STYLE_WEIGHT = 5;
	
	public static CharacterData getRandomCharacter(int xp) {
		CharacterData data = new CharacterData();
		
		INameGenerator generator = new ThresholdNameGenerator();
		data.setName(generator.createNames(1)[0]);
		
		data.setType(RNG.choose(CharacterType.values()));
		//data.setType(CharacterType.Tennin);
		data.setSubtype(RNG.choose(RyuujinType.values()));
		
		int attributeProbability = RNG.random(40);
		int upgradeExistingProbability = 30 + RNG.random(50); // vs improve existing
		int newStyleProbability = RNG.random(10); // vs existing style
		
		applyCreationPoints(data, upgradeExistingProbability, newStyleProbability);
		applyXP(data, xp, attributeProbability, upgradeExistingProbability, newStyleProbability);
		
		data.cleanupData();
		
		return data;
	}
	
	public static String getFileNameForCharacter(CharacterData data) {
		return data.getName().replaceAll(" ", "") + CharacterManagement.CHARACTER_EXTENSION;
	}
	
	private static void applyCreationPoints(CharacterData data,
			int upgradeExistingProbability, int newStyleProbability) {
		int attributePoints = CharacterManagement.CREATION_ATTRIBUTE_DOTS;
		int basicStylePoints = CharacterManagement.CREATION_BASIC_MA_DOTS;
		int otherStylePoints = CharacterManagement.CREATION_OTHER_MA_DOTS;
		Technique[] basicStyleTechniques = TechniqueRegistry.getInstance().getStyle("Basic").getTechniques();
		while (basicStylePoints > 0) {
			Technique technique = basicStyleTechniques[RNG.random(basicStyleTechniques.length)];
			int current = data.getTechniqueRating(technique);
			int maxAdd = Math.min(basicStylePoints, CharacterManagement.CREATION_TRAIT_MAX - current);
			if (maxAdd == 0) {
				continue;
			}
			int toAdd = RNG.random(maxAdd) + 1;
			data.addTechniqueRating(technique, toAdd);
			basicStylePoints -= toAdd;
		}
		
		Style[] newStyles = TechniqueRegistry.getInstance().getNewStyles(data.getKnownStyles(), data.getType(), data.getSubtype(), StyleFilterType.Mundane);
		
		//mundane
		Style mundaneStyle = RNG.choose(newStyles);
		learnTechniqueFromStyle(mundaneStyle, data);
		
		//exalted
		if (data.getType() != CharacterType.Mortal) {
			Style[] exaltedStyles = TechniqueRegistry.getInstance().getNewStyles(data.getKnownStyles(), data.getType(), data.getSubtype(), StyleFilterType.ExaltedOnly);
			learnTechniqueFromStyle(RNG.choose(exaltedStyles), data);
		}
		
		while (otherStylePoints > 0) {
			IKnownTechnique techniqueToUpgrade = null;
			if (RNG.random(100) <= upgradeExistingProbability) {
				techniqueToUpgrade = RNG.choose(data.getKnownTechniques());
			} else {
				Style drawFrom;
				if (RNG.random(100) <= newStyleProbability) {
					newStyles = TechniqueRegistry.getInstance().getNewStyles(data.getKnownStyles(), data.getType(), data.getSubtype(), StyleFilterType.ExaltedOnly);
					if (newStyles.length == 0) {
						newStyles = TechniqueRegistry.getInstance().getNewStyles(data.getKnownStyles(), data.getType(), data.getSubtype(), StyleFilterType.Mundane);
						if (newStyles.length == 0) {
							continue;
						}
					}
					drawFrom = RNG.weightedChoose(newStyles, getStyleWeights(newStyles));
				} else {
					drawFrom = RNG.weightedChoose(data.getKnownStyles(), getStyleWeights(data.getKnownStyles()));
				}
				techniqueToUpgrade = learnTechniqueFromStyle(drawFrom, data);
				if (techniqueToUpgrade == null) {
					continue;
				}
			}
			if (techniqueToUpgrade.getTechnique().getStyle().getName().equals(CharacterManagement.BASIC_STYLE)) {
				continue;
			}
			int current = techniqueToUpgrade.getRating();
			int maxAdd = Math.min(otherStylePoints, CharacterManagement.CREATION_TRAIT_MAX - current);
			if (maxAdd == 0) {
				continue;
			}
			int toAdd = RNG.random(maxAdd) + 1;
			techniqueToUpgrade.setRating(current + toAdd, false);
			otherStylePoints -= toAdd;
		}
		
		double[] attributeWeights = getAttributeWeights(data.getKnownTechniques());
		
		while (attributePoints > 0) {
			Attribute attribute = RNG.weightedChoose(Attribute.values(), attributeWeights);
			if (data.getAttribute(attribute) == CharacterManagement.CREATION_TRAIT_MAX) {
				continue;
			}
			data.addAttribute(attribute, 1, false);
			attributePoints--;
		}
		
		data.setOkamiAttribute(RNG.weightedChoose(Attribute.values(), attributeWeights));
		data.setOkamiForm(true);
	}
	
	private static double[] getStyleWeights(Style[] styles) {
		double[] weights = new double[styles.length];
		for (int i = 0; i != styles.length; i++) {
			weights[i] = styles[i].getType() == CharacterType.Mortal ? MUNDANE_STYLE_WEIGHT : EXALTED_STYLE_WEIGHT;
		}
		return weights;
	}
	
	private static double[] getAttributeWeights(IKnownTechnique[] knownTechniques) {
		double[] weights = new double[Attribute.values().length];
		for (IKnownTechnique technique : knownTechniques) {
			Attribute clashAttribute = technique.getTechnique().getClashAttribute();
			weights[clashAttribute.ordinal()] += technique.getRating();
			if (technique.getTechnique().getDamagePool() != null) {
				Attribute damageAttribute = technique.getTechnique().getDamageAttribute();
				weights[damageAttribute.ordinal()] += technique.getRating();
			}
			
		}
		return weights;
	}
	
	private static IKnownTechnique learnTechniqueFromStyle(Style style, CharacterData data) {
		Technique[] available = TechniqueRegistry.getInstance().getNewTechniquesForStyle(data, style);
		if (available.length == 0) {
			return null;
		}
		Technique newTechnique = RNG.choose(available);
		IKnownTechnique techniqueToUpgrade = new UnlinkedKnownTechnique(newTechnique, 0);
		data.addKnownTechnique(techniqueToUpgrade);
		return techniqueToUpgrade;
	}
	
	private static void applyXP(CharacterData data, int xp, int attributeProbability,
			int upgradeExistingProbability, int newStyleProbability) {
		
		final int MAX_TRIES = 20;
		int tries = 0;
		
		while (xp > 0 && tries < MAX_TRIES) {
			tries++;
			if (RNG.random(100) <= attributeProbability) {
				double[] attributeWeights = getAttributeWeights(data.getKnownTechniques());
				Attribute attribute = RNG.weightedChoose(Attribute.values(), attributeWeights);
				if (data.getAttribute(attribute) == CharacterManagement.ADVANCEMENT_TRAIT_MAX) {
					continue;
				}
				int cost = CharacterManagement.getXPCostForNextAttributeDot(data.getAttribute(attribute));
				if (cost <= xp) {
					data.addAttribute(attribute, 1, true);
					xp -= cost;
					tries = 0;
				}
			} else {
				IKnownTechnique techniqueToUpgrade;
				if (RNG.random(100) <= upgradeExistingProbability) {
					techniqueToUpgrade = RNG.choose(data.getKnownTechniques());
				} else {
					Style drawFrom;
					if (RNG.random(100) <= newStyleProbability) {
						Style[] newStyles = TechniqueRegistry.getInstance().getNewStyles(data.getKnownStyles(), data.getType(), data.getSubtype(), StyleFilterType.Mundane);
						if (newStyles.length == 0) {
							continue;
						}
						drawFrom = RNG.weightedChoose(newStyles, getStyleWeights(newStyles));
					} else {
						drawFrom = RNG.weightedChoose(data.getKnownStyles(), getStyleWeights(data.getKnownStyles()));
					}
					Technique[] available = TechniqueRegistry.getInstance().getNewTechniquesForStyle(data, drawFrom);
					if (available.length == 0) {
						continue;
					}
					Technique newTechnique = RNG.choose(available);
					techniqueToUpgrade = new UnlinkedKnownTechnique(newTechnique, 0);
					
				}
				Refinement[] learnableRefinements = techniqueToUpgrade.getLearnableRefinements();
				if (learnableRefinements.length > 0) {
					Refinement refinementToLearn = RNG.choose(learnableRefinements);
					if (refinementToLearn.getCost() <= xp) {
						techniqueToUpgrade.learnRefinement(refinementToLearn);
						xp -= refinementToLearn.getCost();
					}
				} else {
					if (techniqueToUpgrade.getRating() == CharacterManagement.ADVANCEMENT_TRAIT_MAX) {
						continue;
					}
					int cost = CharacterManagement.getXPCostForNextTechniqueDot(techniqueToUpgrade.getTechnique(), data,
							techniqueToUpgrade.getRating());
					if (cost <= xp) {
						techniqueToUpgrade.setRating(techniqueToUpgrade.getRating() + 1, true);
						xp -= cost;
						tries = 0;
						
						if (!Arrays.asList(data.getKnownTechniques()).contains(techniqueToUpgrade)) {
							data.addKnownTechnique(techniqueToUpgrade);
						}
					}
				}
			}
		}
	}
}
