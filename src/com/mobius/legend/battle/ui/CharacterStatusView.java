package com.mobius.legend.battle.ui;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.Attribute;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.BattleCharacter;
import com.mobius.legend.management.CharacterManagement;
import com.mobius.legend.utilities.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CharacterStatusView extends RelativeLayout {

	public CharacterStatusView(Context context, AttributeSet set) {
		super(context, set);
		this.initComponent(context);
	}

	private void initComponent(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.character_status_view, null, false);
        this.addView(v);

        BattleCharacter yourCharacter = BattleEngine.getInstance().getCurrentCharacter();
        CharacterStatus yourStatus = yourCharacter.getStatus();
        BattleCharacter theirCharacter = BattleEngine.getInstance().getOpponent(yourCharacter);
        CharacterStatus theirStatus = theirCharacter.getStatus();

        TextView nameView = (TextView) this.findViewById(R.id.character_name);
        nameView.append(StringUtils.colorize(yourCharacter.getName(), Color.WHITE));

        TextView leftStatsList = (TextView) this.findViewById(R.id.left_stats_list);
        TextView rightStatsList = (TextView) this.findViewById(R.id.right_stats_list);

        leftStatsList.append("Health ");
        leftStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getHealth(), CharacterStatus.MAX_HEALTH), Color.RED));
        leftStatsList.append("\nStocks ");
		leftStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getStocks(), yourStatus.getStartingStocks()), Color.RED));
		leftStatsList.append("\nOverdrive ");
        leftStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getOverdrive(), CharacterStatus.MAX_OVERDRIVE), Color.YELLOW));
        leftStatsList.append("\nKi");
		leftStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getKi(), CharacterStatus.MAX_KI, true), Color.CYAN));
        leftStatsList.append("\nStagger");
        leftStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getRoundsHit(), CharacterStatus.ROUNDS_TO_STAGGER), Color.GRAY));
        leftStatsList.append("\n\n");
        rightStatsList.append(StringUtils.colorize(StringUtils.dots(theirStatus.getHealth(), CharacterStatus.MAX_HEALTH, true), Color.RED));
		rightStatsList.append(" Health\n");
		rightStatsList.append(StringUtils.colorize(StringUtils.dots(theirStatus.getStocks(), theirStatus.getStartingStocks(), true), Color.RED));
        rightStatsList.append(" Stocks\n");
        rightStatsList.append(StringUtils.colorize(StringUtils.dots(theirStatus.getOverdrive(), CharacterStatus.MAX_OVERDRIVE, true), Color.YELLOW));
        rightStatsList.append(" Overdrive\n");
        rightStatsList.append(StringUtils.colorize(StringUtils.dots(theirStatus.getKi(), CharacterStatus.MAX_KI, true), Color.CYAN));
        rightStatsList.append(" Ki\n");
        rightStatsList.append(StringUtils.colorize(StringUtils.dots(theirStatus.getRoundsHit(), CharacterStatus.ROUNDS_TO_STAGGER, true), Color.GRAY));
        rightStatsList.append(" Stagger\n\n");

        for (Attribute attribute : Attribute.values()) {
        	leftStatsList.append(attribute.toString() + " " +
        			StringUtils.dots(yourCharacter.getAttribute(attribute), CharacterManagement.ADVANCEMENT_TRAIT_MAX) +
        			"\n");
        }
        
        rightStatsList.append(StringUtils.colorize(StringUtils.dots(yourStatus.getWillpower(), CharacterStatus.MAX_WILLPOWER, true), Color.WHITE));
        rightStatsList.append(" Willpower\n");
        if (BattleEngine.getInstance().getOpponentCount() == 1) {
        	rightStatsList.append(BattleEngine.getInstance().isDistant(yourCharacter, theirCharacter) ?
        			"Distant" : "Near");
        }

        TextView statusView = (TextView) this.findViewById(R.id.status_effects);
        String statusLines = "";
        for (ISpecialEffect effect : yourStatus.getEffects()) {
        	if (effect.getStatusString() != null) {
        		statusLines += "\n" + effect.getStatusString();
        	}
        }
        statusView.append(StringUtils.colorize(statusLines.trim(), Color.WHITE));
   }


}
