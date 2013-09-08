package com.mobius.legend.battle.brain;

import android.content.Context;

import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.character.technique.IKnownTechnique;

public interface IBrain {
	void attach(BattleCharacter character);
	
	void chooseAction(Context ctx, ITechniqueSelectionCallback callback);
	
	void reviewRound(Context ctx, IPreviewRoundCallback callback);

	IKnownTechnique[] determineLegalTechniques();
}
