package com.mobius.legend.character;

import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.Technique;

public interface ICharacter {
	public String getName();
	
	public CharacterType getType();
	
	public RyuujinType getSubtype();
	
	public boolean isAdvancing();
	
	public void addAttribute(Attribute attribute, int amount);
	
	public int getAttribute(Attribute attribute);
	
	public int getAttribute(Attribute attribute, boolean isAdvanced);
	
	public int getTechniqueRating(Technique technique);
	
	public CharacterStatus getStatus();
	
	public IKnownTechnique[] getKnownTechniques();
	
	public boolean isTrueForm();
	
	public Attribute getOkamiAttribute();
	
	public Style[] getKnownStyles();
	
	public IKnownTechnique[] getKnownTechniquesForStyle(Style style);
	
	public void cleanupData();

	public void setOkamiForm(boolean form);
}
