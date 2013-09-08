package com.mobius.legend.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.character.technique.UnlinkedKnownTechnique;
import com.mobius.legend.management.CharacterManagement;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueRegistry;

public class CharacterData implements ICharacter, Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected CharacterType type;
	protected int creationStrength, advancedStrength;
	protected int creationDexterity, advancedDexterity;
	protected int creationStamina, advancedStamina;
	protected Vector<UnlinkedKnownTechnique> knownTechniques = new Vector<UnlinkedKnownTechnique>();
	
	protected Attribute okamiTrueFormAttribute;
	protected boolean okamiStartTrueForm;
	
	protected RyuujinType subtype;
	
	private boolean isAdvancing;
	
	public CharacterData() {
		this.name = "";
		this.type = CharacterType.Mortal;
		this.creationStrength = this.advancedStrength = CharacterManagement.STARTING_ATTRIBUTE_RATING;
		this.creationDexterity = this.advancedDexterity = CharacterManagement.STARTING_ATTRIBUTE_RATING;
		this.creationStamina = this.advancedStamina = CharacterManagement.STARTING_ATTRIBUTE_RATING;
		this.isAdvancing = false;
		this.okamiStartTrueForm = true;
		
		for (Technique technique : TechniqueRegistry.getInstance().getTechniquesForStyle(CharacterManagement.BASIC_STYLE)) {
			knownTechniques.add(new UnlinkedKnownTechnique(technique, CharacterManagement.STARTING_BASIC_MA_DOTS));
		}
	}
	
	public CharacterData(CharacterData data) {
		this(data.name, data.type, data.creationStrength, data.advancedStrength, data.creationDexterity, 
				data.advancedDexterity, data.creationStamina, data.advancedStamina, data.isAdvancing,
				data.subtype, 
				copyKnownTechniques(data));
		
		this.okamiStartTrueForm = data.okamiStartTrueForm;
		this.okamiTrueFormAttribute = data.okamiTrueFormAttribute;
		
		cleanupData();
	}
	
	public CharacterData(String name, CharacterType type, int creationStrength, int advancedStrength,
			int creationDexterity, int advancedDexterity, int creationStamina, int advancedStamina,
			boolean advancing, RyuujinType subtype, UnlinkedKnownTechnique... techniques) {
		this.name = name;
		this.type = type;
		this.creationStrength = creationStrength;
		this.advancedStrength = advancedStrength;
		this.creationDexterity = creationDexterity;
		this.advancedDexterity = advancedDexterity;
		this.creationStamina = creationStamina;
		this.advancedStamina = advancedStamina;
		
		this.isAdvancing = advancing;
		
		this.subtype = subtype;
		
		knownTechniques.addAll(Arrays.asList(techniques));
	}
	
	private static UnlinkedKnownTechnique[] copyKnownTechniques(CharacterData data) {
		List<UnlinkedKnownTechnique> copy = new ArrayList<UnlinkedKnownTechnique>();
		for (UnlinkedKnownTechnique technique : data.knownTechniques) {
			copy.add(new UnlinkedKnownTechnique(technique));
		}
		return copy.toArray(new UnlinkedKnownTechnique[0]);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public CharacterType getType() {
		return type;
	}
	
	public void setType(CharacterType characterType) {
		this.type = characterType;
	}
	
	public RyuujinType getSubtype() {
		return subtype;
	}
	
	public void setSubtype(RyuujinType subtype) {
		this.subtype = subtype;
	}
	
	public boolean isAdvancing() {
		return isAdvancing;
	}
	
	public void setAdvancing() {
		isAdvancing = true;
	}
	
	public CharacterStatus getStatus() {
		return null;
	}
	
	public void addAttribute(Attribute attribute, int amount) {
		addAttribute(attribute, amount, true);
	}
	
	public void addAttribute(Attribute attribute, int amount, boolean advanced) {
		switch (attribute) {
		case Strength:
			creationStrength += advanced ? 0 : amount;
			advancedStrength += amount;
			advancedStrength = Math.max(creationStrength, advancedStrength);
			break;
		case Dexterity:
			creationDexterity += advanced ? 0 : amount;
			advancedDexterity += amount;
			advancedDexterity = Math.max(creationDexterity, advancedDexterity);
			break;
		case Stamina:
			creationStamina += advanced ? 0 : amount;
			advancedStamina += amount;
			advancedStamina = Math.max(creationStamina, advancedStamina);
			break;
		}
	}
	
	public int getAttribute(Attribute attribute) {
		return getAttribute(attribute, false);
	}
	
	public int getAttribute(Attribute attribute, boolean creationOnly) {
		switch (attribute) {
		case Strength: return creationOnly ? creationStrength : advancedStrength;
		case Dexterity: return creationOnly ? creationDexterity : advancedDexterity;
		case Stamina: return creationOnly ? creationStamina : advancedStamina;
		}
		return 0;
	}
	
	public void addKnownTechnique(IKnownTechnique technique) {
		knownTechniques.add((UnlinkedKnownTechnique) technique);
	}
	
	public void addTechniqueRating(Technique technique, int amount) {
		UnlinkedKnownTechnique known = getKnownTechnique(technique);
		if (known != null) {
			known.setRating(known.getRating() + amount, isAdvancing);
		}
	}
	
	public void removeKnownTechnique(IKnownTechnique remove) {
		knownTechniques.remove(remove);
	}
	
	public int getTechniqueRating(Technique technique) {
		UnlinkedKnownTechnique known = getKnownTechnique(technique);
		return known == null ? 0 : known.getRating();
	}
	
	private UnlinkedKnownTechnique getKnownTechnique(Technique technique) {
		for (UnlinkedKnownTechnique known : knownTechniques) {
			if (known.getTechnique().equals(technique))
				return known;
		}
		return null;
	}
	
	public IKnownTechnique[] getKnownTechniques() {
		return knownTechniques.toArray(new UnlinkedKnownTechnique[0]);
	}
	
	public Style[] getKnownStyles() {
		List<Style> styles = new ArrayList<Style>();
		for (UnlinkedKnownTechnique technique : knownTechniques) {
			if (!styles.contains(technique.getTechnique().getStyle())) {
				styles.add(technique.getTechnique().getStyle());
			}
		}
		return styles.toArray(new Style[0]);
	}
	
	public IKnownTechnique[] getKnownTechniquesForStyle(Style style) {
		List<UnlinkedKnownTechnique> techniques = new ArrayList<UnlinkedKnownTechnique>();
		for (UnlinkedKnownTechnique technique : knownTechniques) {
			if (style.equals(technique.getTechnique().getStyle())) {
				techniques.add(technique);
			}
		}
		return techniques.toArray(new IKnownTechnique[0]);
	}

	@Override
	public void cleanupData() {
		List<IKnownTechnique> toRemove = new ArrayList<IKnownTechnique>();
		for (IKnownTechnique technique : getKnownTechniques()) {
			if (technique.getRating() == 0) {
				toRemove.add(technique);
			}
		}
		for (IKnownTechnique remove : toRemove) {
			removeKnownTechnique(remove);
		}
		
		Collections.sort(knownTechniques);
	}

	public void setOkamiAttribute(Attribute attribute) {
		this.okamiTrueFormAttribute = attribute;
	}

	public void setOkamiForm(boolean form) {
		this.okamiStartTrueForm = form;
	}

	@Override
	public boolean isTrueForm() {
		return okamiStartTrueForm;
	}

	@Override
	public Attribute getOkamiAttribute() {
		return okamiTrueFormAttribute;
	}
}
