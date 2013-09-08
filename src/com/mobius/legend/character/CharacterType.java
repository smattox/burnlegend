package com.mobius.legend.character;

public enum CharacterType implements ICharacterType {
	Mortal,
	Mugen,
	Akuma,
	Yamajin,
	Ryuujin {
		public String getLabel(ICharacter character) {
			return character.getSubtype() + " " + toString();
		}
	},
	Tennin,
	Okami,
	Shinigami;
	
	public String getLabel(ICharacter character) {
		return toString();
	}
}
