package com.mobius.legend.management;

import com.mobius.legend.R;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.utilities.StringUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CharacterManagementOverview extends RelativeLayout {

	public CharacterManagementOverview(Context context, AttributeSet set) {
		super(context, set);
		this.initComponent(context);
		updateOverview(context);
	}
	
	private void initComponent(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.character_status_view, null, false);
        this.addView(v);
   }
	
	public void updateOverview(Context ctx) {
		CharacterData character = CharacterManagement.character;
		
		TextView leftStatsList = (TextView) this.findViewById(R.id.left_stats_list);
		TextView rightStatsList = (TextView) this.findViewById(R.id.right_stats_list);
		
		leftStatsList.setText("");
		rightStatsList.setText("");
		
		if (!character.isAdvancing()) {
			leftStatsList.append(ctx.getString(R.string.Attributes));
			addDots(leftStatsList, CharacterManagement.getSpentCreationAttributeCount(), CharacterManagement.CREATION_ATTRIBUTE_DOTS);
			leftStatsList.append("\n");
			leftStatsList.append(ctx.getString(R.string.MartialArts));
			
			
			addDots(rightStatsList, CharacterManagement.getSpentCreationBasicStyleCount(), CharacterManagement.CREATION_BASIC_MA_DOTS);
			rightStatsList.append(ctx.getString(R.string.BasicTechniques));
			rightStatsList.append("\n");
			addDots(rightStatsList, CharacterManagement.getSpentCreationOtherStyleCount(), CharacterManagement.CREATION_OTHER_MA_DOTS);
		} else {
			leftStatsList.append(ctx.getString(R.string.Attributes));
			leftStatsList.append(" ");
			leftStatsList.append("" + CharacterManagement.getXPSpentOnAttributes());
			
			leftStatsList.append("\n" + ctx.getString(R.string.MartialArts));
			leftStatsList.append(" ");
			leftStatsList.append("" + CharacterManagement.getXPSpentOnMartialArts());
			
			rightStatsList.append(ctx.getString(R.string.Refinements));
			rightStatsList.append(" ");
			rightStatsList.append("" + CharacterManagement.getXPSpentOnRefinements());
			
			rightStatsList.append("\n" + ctx.getString(R.string.Total));
			rightStatsList.append(" ");
			rightStatsList.append("" + CharacterManagement.getTotalXPSpent());
		}
	}
	
	private void addDots(TextView view, int amount, int max) {
		if (amount <= max) {
			view.append(StringUtils.dots(amount, max));
		} else {
			view.append(StringUtils.colorize(StringUtils.dots(max), Color.RED));
		} 
	}
}
