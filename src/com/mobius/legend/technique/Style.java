package com.mobius.legend.technique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mobius.legend.character.CharacterType;
import com.mobius.legend.character.RyuujinType;
import com.mobius.legend.management.CharacterManagement;

public class Style implements Comparable<Style> {
	private List<Technique> techniques = new ArrayList<Technique>();
	private final String name;
	private final CharacterType type;
	private final RyuujinType subtype;
	
	public Style(String name, CharacterType type, RyuujinType subtype, Technique... techniques) {
		this.name = name;
		this.type = type;
		this.subtype = subtype;
		this.techniques.addAll(Arrays.asList(techniques));
	}
	
	public String getName() {
		return name;
	}
	
	public CharacterType getType() {
		return type;
	}
	
	public RyuujinType getSubtype() {
		return subtype;
	}
	
	public Technique[] getTechniques() {
		return techniques.toArray(new Technique[0]);
	}
	
	public void addTechnique(Technique technique) {
		techniques.add(technique);
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Style) && ((Style)obj).name.equals(name);
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(Style another) {
		if (this.getName().equals(CharacterManagement.BASIC_STYLE) &&
			another.getName().equals(CharacterManagement.BASIC_STYLE)) {
			return 0;
		}
		if (this.getName().equals(CharacterManagement.BASIC_STYLE)) {
			return -1;
		}
		if (another.getName().equals(CharacterManagement.BASIC_STYLE)) {
			return 1;
		}
		if (this.getType() == CharacterType.Mortal && another.getType() != CharacterType.Mortal) {
			return -1;
		}
		if (this.getType() != CharacterType.Mortal && another.getType() == CharacterType.Mortal) {
			return 1;
		}
		return this.getName().compareTo(another.getName());
	}
}
