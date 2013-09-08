package com.mobius.legend.battle.brain.local;

import com.mobius.legend.R;
import com.mobius.legend.battle.BattleEngine;
import com.mobius.legend.battle.ChosenAction;
import com.mobius.legend.character.CharacterStatus;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.ITechniqueOptionPick;
import com.mobius.legend.technique.MutableTechnique;
import com.mobius.legend.technique.TechniqueOptions;
import com.mobius.legend.technique.view.TechniqueView;
import com.mobius.legend.ui.UIUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class TechniqueSelectionActivity extends Activity {
    
	private MutableTechnique chosenTechnique;
	private IKnownTechnique[] legalTechniqueList;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.technique_selection);
        
        legalTechniqueList = BattleEngine.getInstance().getCurrentCharacter().getBrain().determineLegalTechniques();
        
        ListView techniqueList = (ListView) this.findViewById(R.id.technique_list);
        techniqueList.setAdapter(new TechniqueListAdapter());
        techniqueList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,
					long id) {
			    IKnownTechnique known = legalTechniqueList[position];
			    chosenTechnique = new MutableTechnique(known.getTechnique());
			    handleOptions();
			}
        });
    }
        
    private void handleOptions() {
    	if (chosenTechnique.getOptions().length == 0) {
    		doConfirmSelection();
    		return;
    	}
    	TechniqueOptions options = chosenTechnique.getOptions()[0];
    	
    	ITechniqueOptionPick legalPick = options.getSingleLegalPick(BattleEngine.getInstance().getCurrentCharacter());
    	if (legalPick != null) {
    		chosenTechnique.applyOptionPick(legalPick);
			handleOptions();
			return;
    	}
    	
    	final ITechniqueOptionPick[] picks = options.getPicks();
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(options.getLabel());
    	if (picks.length > 1) {
	    	builder.setPositiveButton(picks[0].getButtonLabel(), new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					chosenTechnique.applyOptionPick(picks[0]);
					handleOptions();
				}
	    	});
	    	builder.setNegativeButton(picks[1].getButtonLabel(), new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					chosenTechnique.applyOptionPick(picks[1]);
					handleOptions();
				}
	    	});
	    	if (picks.length == 3) {
		    	builder.setNeutralButton(picks[2].getButtonLabel(), new OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						chosenTechnique.applyOptionPick(picks[2]);
						handleOptions();
					}
		    	});
	    	}
    	} else {
    		builder.setPositiveButton(R.string.Yes, new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					chosenTechnique.applyOptionPick(picks[0]);
					handleOptions();
				}
	    	});
    		builder.setNegativeButton(R.string.No, new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					chosenTechnique.applyOptionPick(null);
					handleOptions();
				}
	    	});
    	}
    	builder.create().show();
    }
    
    private void doConfirmSelection() {
    	UIUtils.createConfirmationDialog(this, "Use " + chosenTechnique.getName() + "?", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finishSelection();
			}
    		
    	});
    }
    
    private void finishSelection() {
    	finish();
    	HumanPlayer.selectionCallback.choose(new ChosenAction(chosenTechnique));
    }
    
    private class TechniqueListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return legalTechniqueList.length;
		}

		@Override
		public Object getItem(int arg0) {
			return legalTechniqueList[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TechniqueView view;
			CharacterStatus status = BattleEngine.getInstance().getCurrentCharacter().getStatus();
			if (convertView == null) {
				view = new TechniqueView(TechniqueSelectionActivity.this,
						(IKnownTechnique) getItem(position), status);
			} else {
				IKnownTechnique technique = (IKnownTechnique)getItem(position);
				view = (TechniqueView) convertView;
				view.setData(technique.getTechnique(), technique.toString(), status);
			}
			return view;
		}
    }
    
    
}