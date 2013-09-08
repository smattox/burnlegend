package com.mobius.legend.technique.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobius.legend.battle.effect.ISpecialEffect;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Technique;
import com.mobius.legend.technique.TechniqueOptions;
import com.mobius.legend.technique.Type;
import com.mobius.legend.utilities.StringUtils;

public class TechniqueView extends LinearLayout {

	private TextView title;
	private TextView tags;
	private LabelView cost;
	private LabelView range;
	private LabelView type;
	private LabelView defeats;
	private LabelView clash;
	private LabelView damage;
	private TextView effects;
	
	public TechniqueView(Context context, IKnownTechnique technique, CharacterStatus status) {
		super(context);
		setupView(context);
		setData(technique.getTechnique(), technique.toString(), status);
	}
	
	public TechniqueView(Context context, Technique technique) {
		super(context);
		setupView(context);
		setData(technique, technique.toString(), null);
	}
	
	private void setupView(Context context) {
		
		this.setOrientation(VERTICAL);
		
		this.title = new TextView(context);
		title.setTextSize(18f);
		
		this.tags = new TextView(context);
		tags.setTypeface(null, Typeface.BOLD);
		
		LinearLayout topRow = new LinearLayout(context);
		this.cost = new LabelView(context, "Cost: ");
		this.range = new LabelView(context, "Range: ");
		topRow.addView(cost,
				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2));
		topRow.addView(range,
				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2));
		
		LinearLayout typeRow = new LinearLayout(context);
		this.type = new LabelView(context, "Type: ");
		this.defeats = new LabelView(context, "Defeats: ");
		typeRow.addView(type,
				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2));
		typeRow.addView(defeats,
				new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 2));
		
		
		this.clash = new LabelView(context, "Clash: ");
		this.damage = new LabelView(context, "Damage: ");
		
		this.effects = new TextView(context);
		effects.setTypeface(null, Typeface.BOLD);
		
		addView(title);
		addView(tags);
		addView(topRow, LayoutParams.FILL_PARENT);
		addView(typeRow, LayoutParams.FILL_PARENT);
		addView(clash);
		addView(damage);
		addView(effects);
	}
	
	public void setData(Technique technique, String titleString, CharacterStatus status) {

		//String titleString = baseTechnique.getName() + " (" +
		//		StringUtils.dots(technique.getRating()) + ")";
		String tagString = StringUtils.join(technique.getTags(), StringUtils.COMMA_SPACE);
		
		String kiCostString = technique.getKiCost() > 0 ? technique.getKiCost() + " ki" : null;
		String wpCostString = status != null && technique.getWillpowerCost(status) > 0 ?
				technique.getWillpowerCost(status) + " wp" : null;
		String costString = StringUtils.join(new String[] { kiCostString, wpCostString }, StringUtils.COMMA_SPACE, "-");
		
		String rangeString = technique.getRange().toString() + "/" + technique.getMovement().toString();
		String typeString = technique.getType().toString();
		String defeatString = getDefeatsString(technique.getDefeats());
		String clashString = technique.getClashPool() != null ?
				technique.getClashPool().toString() : null;
		String damageString = technique.getDamagePool() != null ? 
				technique.getDamagePool().toString() : null;
		String effectsString = "";
		for (TechniqueOptions option : technique.getOptions()) {
			effectsString += option.getTechniqueDisplayString() + "\n";
		}
		for (ISpecialEffect effect : technique.getEffects()) {
			effectsString += effect.getTechniqueDisplayString() + "\n";
		}
		effectsString = effectsString.trim();
		
		if (tagString.length() > 0) {
			tags.setVisibility(View.VISIBLE);
			tags.setText(tagString);
		} else {
			tags.setVisibility(View.GONE);
		}
		title.setText(titleString);
		cost.setText(costString);
		range.setText(rangeString);
		type.setText(typeString);
		defeats.setText(defeatString);
		if (clashString != null) {
			clash.setVisibility(View.VISIBLE);
			clash.setText(clashString);
		} else {
			clash.setVisibility(View.GONE);
		}
		if (damageString != null) {
			damage.setVisibility(View.VISIBLE);
			damage.setText(damageString);
		} else {
			damage.setVisibility(View.GONE);
		}
		if (effectsString.length() > 0) {
			effects.setVisibility(View.VISIBLE);
			effects.setText(effectsString);
		} else {
			effects.setVisibility(View.GONE);
		}
	}
	
	private String getDefeatsString(Type[] defeats) {
		String defeat = "";
		for (Type type : defeats) {
			if (type != defeats[0]) {
				defeat += ", ";
			}
			defeat += type;
		}
		if (defeats.length == 0) {
			defeat = "-";
		}
		return defeat;
	}
	
	private class LabelView extends LinearLayout {

		private TextView content;
		
		public LabelView(Context context, String label) {
			super(context);
			TextView labelView = new TextView(context);
			labelView.setText(label);
			labelView.setTypeface(null, Typeface.BOLD);
			content = new TextView(context);
			
			addView(labelView);
			addView(content);
		}
		
		public void setText(String text) {
			content.setText(text);
		}
		
	}
}


