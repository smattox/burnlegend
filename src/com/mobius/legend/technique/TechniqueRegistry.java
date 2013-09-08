package com.mobius.legend.technique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.ICharacter;
import com.mobius.legend.character.RyuujinType;
import com.mobius.legend.character.technique.IKnownTechnique;

public class TechniqueRegistry {
	private static TechniqueRegistry instance;
	private final List<Style> list = new ArrayList<Style>();
		
	public static TechniqueRegistry getInstance() {
		if (instance == null) {
			instance = new TechniqueRegistry();
		}
		return instance;
	}
	
	public Technique[] getAll() {
		List<Technique> techniques = new ArrayList<Technique>();
		for (Style style : list) {
			techniques.addAll(Arrays.asList(style.getTechniques()));
		}
		return techniques.toArray(new Technique[0]);
	}
	
	public Style getStyle(String name) {
		for (Style style : list) {
			if (style.getName().equals(name)) {
				return style;
			}
		}
		return null;
	}
	
	public Technique[] getTechniquesForStyle(String name) {
		return getStyle(name).getTechniques();
	}
	
	public void registerStyle(Style style) {
		list.add(style);
	}
	
	public Technique[] getNewTechniquesForStyle(ICharacter character, Style style) {
		Technique[] styleTechniques = getTechniquesForStyle(style.getName());
		IKnownTechnique[] knownTechniques = character.getKnownTechniques();
		List<Technique> availableStyleTechniques = new ArrayList<Technique>(Arrays.asList(styleTechniques));
		boolean knowsOverdrive = false;
		for (IKnownTechnique knownTechnique : knownTechniques) {
			availableStyleTechniques.remove(knownTechnique.getTechnique());
			if (knownTechnique.getTechnique().hasTag(TechniqueTag.Overdrive)) {
				knowsOverdrive = true;
			}
		}
		if (knowsOverdrive) {
			for (Technique technique : new ArrayList<Technique>(availableStyleTechniques)) {
				if (technique.hasTag(TechniqueTag.Overdrive)) {
					availableStyleTechniques.remove(technique);
				}
			}
		}
		for (Technique technique : new ArrayList<Technique>(availableStyleTechniques)) {
			if ((technique.hasTag(TechniqueTag.Celestial) && !knowsTechniqueWithTag(knownTechniques, TechniqueTag.Terrestrial)) ||
				(technique.hasTag(TechniqueTag.Sidereal) && !knowsTechniqueWithTag(knownTechniques, TechniqueTag.Celestial))) {
				availableStyleTechniques.remove(technique);
			}
		}
		if (character.getType() == CharacterType.Ryuujin) {
			boolean lightningAllowed = false;
			boolean metalAllowed = false;
			boolean shadowAllowed = false;
			boolean iceAllowed = false;
			boolean overdriveAllowed = true;
			switch (character.getSubtype()) {
			case Air:
				lightningAllowed = hasAdvanced(knownTechniques, TechniqueTag.AirBinding);
				break;
			case Earth:
				metalAllowed = hasAdvanced(knownTechniques, TechniqueTag.EarthBinding);
				break;
			case Fire:
				shadowAllowed = hasAdvanced(knownTechniques, TechniqueTag.FireBinding);
				break;
			case Water:
				iceAllowed = hasAdvanced(knownTechniques, TechniqueTag.WaterBinding);
				break;
			case Wood:
				overdriveAllowed = style.getSubtype() == RyuujinType.Wood;
			}
			for (Technique technique : new ArrayList<Technique>(availableStyleTechniques)) {
				if ((technique.hasTag(TechniqueTag.LightningBinding) && !lightningAllowed) ||
					(technique.hasTag(TechniqueTag.MetalBinding) && !metalAllowed) ||
					(technique.hasTag(TechniqueTag.ShadowBinding) && !shadowAllowed) ||
					(technique.hasTag(TechniqueTag.IceBinding) && !iceAllowed) ||
					(technique.hasTag(TechniqueTag.Overdrive) && !overdriveAllowed)) {
					availableStyleTechniques.remove(technique);
				}
			}
		}
		return availableStyleTechniques.toArray(new Technique[0]);
	}
	
	private boolean knowsTechniqueWithTag(IKnownTechnique[] techniques, TechniqueTag tag) {
		for (IKnownTechnique technique : techniques) {
			if (technique.getTechnique().hasTag(tag)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasAdvanced(IKnownTechnique[] known, TechniqueTag tag) {
		int basicTechniquesMastered = 0;
		for (IKnownTechnique technique : known) {
			if (technique.getTechnique().hasTag(tag) && technique.getRating() >= 2) {
				basicTechniquesMastered++;
			}
		}
		return basicTechniquesMastered >= 3;
		
	}
	
	public Style[] getNewStyles(Style[] knownStyles, CharacterType type, RyuujinType subtype) {
		return getNewStyles(knownStyles, type, subtype, type == CharacterType.Mortal ? StyleFilterType.Mundane : StyleFilterType.Exalted);
	}
	
	public Style[] getNewStyles(Style[] knownStyles, CharacterType characterType, RyuujinType subtype, StyleFilterType styleType) {
		List<Style> availableStyles = new ArrayList<Style>(list);
		for (Style knownStyle : knownStyles) {
			availableStyles.remove(knownStyle);
		}
		for (Style availableStyle : new ArrayList<Style>(availableStyles)) {
			// remove all exalted styles for mortals, and mundane types if we only want Exalted styles
			if (availableStyle.getType() == CharacterType.Mortal && styleType == StyleFilterType.ExaltedOnly) {
				availableStyles.remove(availableStyle);
			}
			// remove all exalted styles for other exalt types
			if (availableStyle.getType() != CharacterType.Mortal && (styleType == StyleFilterType.Mundane ||
					availableStyle.getType() != characterType)) {
				availableStyles.remove(availableStyle);
			}
			// remove other Ryuujin styles for non-Wood
			if (characterType == CharacterType.Ryuujin && availableStyle.getType() == CharacterType.Ryuujin &&
				subtype != RyuujinType.Wood && availableStyle.getSubtype() != subtype) {
				availableStyles.remove(availableStyle);
			}
		}
		return availableStyles.toArray(new Style[0]);
	}
	
	public Technique getTechnique(String techniqueName, String styleName) {
		Style style = getStyle(styleName);
		for (Technique technique : style.getTechniques()) {
			if (technique.getName().equals(techniqueName)) {
				return technique;
			}
		}
		return null;
	}
}
