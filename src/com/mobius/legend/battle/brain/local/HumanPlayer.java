package com.mobius.legend.battle.brain.local;

import android.content.Context;
import android.content.Intent;

import com.mobius.legend.battle.brain.AbstractBrain;
import com.mobius.legend.battle.brain.IPreviewRoundCallback;
import com.mobius.legend.battle.brain.ITechniqueSelectionCallback;

public class HumanPlayer extends AbstractBrain {

	public static ITechniqueSelectionCallback selectionCallback;
	public static IPreviewRoundCallback reviewCallback;
	private final boolean isHotseat;
	private static Intent nextScreenIntent;
	private static Context ctx;
	
	public HumanPlayer(boolean isHotseat) {
		this.isHotseat = isHotseat;
	}
	
	public static void proceed() {
		ctx.startActivity(nextScreenIntent);
	}
	
	private void prepareScreen() {
		if (isHotseat) {
			Intent blinderIntent = new Intent(ctx, TurnBlinderActivity.class);
			ctx.startActivity(blinderIntent);
		} else {
			proceed();
		}
	}
	
	@Override
	public void chooseAction(Context ctx, ITechniqueSelectionCallback callback) {
		HumanPlayer.selectionCallback = callback;
		nextScreenIntent = new Intent(ctx, TechniqueSelectionActivity.class);
		HumanPlayer.ctx = ctx;
		
		prepareScreen();
	}

	@Override
	public void reviewRound(Context ctx, IPreviewRoundCallback callback) {
		HumanPlayer.reviewCallback = callback;
		nextScreenIntent = new Intent(ctx, PreviewRoundActivity.class);
		HumanPlayer.ctx = ctx;
		
		prepareScreen();
	}
}
