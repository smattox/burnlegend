package com.mobius.legend.character;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.mobius.legend.management.CharacterManagement;

public class CharacterRoster {
	private static CharacterRoster instance;
	private Map<String, CharacterData> roster = new HashMap<String, CharacterData>();
	
	private CharacterRoster(Context ctx) {
		String[] files = ctx.fileList();
		for (String file : files) {
			if (!file.endsWith(CharacterManagement.CHARACTER_EXTENSION)) {
				continue;
			}
			try {
				FileInputStream inStream = ctx.openFileInput(file);
				ObjectInputStream in = new ObjectInputStream(inStream);
				CharacterData character = (CharacterData) in.readObject();
				addCharacter(character);
			} catch (Exception e) {
				e.printStackTrace();
				ctx.deleteFile(file);
			}
		}
	}
	
	public static CharacterRoster getInstance(Context ctx) {
		if (instance == null) {
			instance = new CharacterRoster(ctx);
		}
		return instance;
	}
	
	public CharacterData[] getAllCharacters() {
		return roster.values().toArray(new CharacterData[0]);
	}
	
	public void addCharacter(CharacterData data) {
		roster.put(data.getName(), new CharacterData(data));
	}
	
	public boolean deleteCharacter(Context ctx, CharacterData data) {
		try {
			boolean success = ctx.deleteFile(RandomCharacterGenerator.getFileNameForCharacter(data));
			if (success) {
				roster.remove(data.getName());
			}
			return success;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean hasCharacter(CharacterData data) {
		return roster.containsKey(data.getName());
	}
}
