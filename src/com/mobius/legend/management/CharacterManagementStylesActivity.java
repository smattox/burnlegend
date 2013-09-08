package com.mobius.legend.management;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.mobius.legend.R;
import com.mobius.legend.character.technique.IKnownTechnique;
import com.mobius.legend.technique.Refinement;
import com.mobius.legend.technique.Style;
import com.mobius.legend.technique.TechniqueRegistry;
import com.mobius.legend.ui.ITraitRemovalListener;

public class CharacterManagementStylesActivity extends AbstractCharacterCreationPageActivity {
	
	private BaseExpandableListAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.character_creation_styles);
        
        ExpandableListView styleList = (ExpandableListView) this.findViewById(R.id.styleList);
        adapter = new CreationStyleListAdapter();
        styleList.setAdapter(adapter);
        styleList.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int styleIndex, long arg3) {
				Style style = (Style) adapter.getGroup(styleIndex);
				if (style == null) {
					CharacterManagementStylesActivity.this.startActivity(new Intent(CharacterManagementStylesActivity.this,
							CharacterManagementNewStyleActivity.class));
				}
				return false;
			}
        });
        styleList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int styleIndex, int techniqueIndex, long arg4) {
				IKnownTechnique technique = (IKnownTechnique) adapter.getChild(styleIndex, techniqueIndex);
				if (technique == null) {
					startAddTechnique(styleIndex);
					return true;
				}
				return false;
			}
        });
        styleList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
		            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
		            int childPosition = ExpandableListView.getPackedPositionChild(id);

		            IKnownTechnique technique = (IKnownTechnique) adapter.getChild(groupPosition, childPosition);
		            
		            return technique != null ? removeTechnique(technique) : false;
		        }

		        return false;

			}
        });
        //styleList.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        
        CharacterManagementNavigationView nav = (CharacterManagementNavigationView) this.findViewById(R.id.navigation);
        nav.setLeftButton(CharacterManagementPage.Attributes, this);
        nav.setRightButton(CharacterManagementPage.Details, this);
	}
	
	protected void startAddTechnique(int styleIndex) {
		Intent intent = new Intent(CharacterManagementStylesActivity.this,
				CharacterManagementNewTechniqueActivity.class);
		intent.putExtra(CharacterManagementNewTechniqueActivity.KNOWN_STYLE, true);
		intent.putExtra(CharacterManagementNewTechniqueActivity.STYLE_INDEX, styleIndex);
		CharacterManagementStylesActivity.this.startActivity(intent);
	}
	
	protected boolean removeTechnique(IKnownTechnique technique) {
		if ((!getCharacter().isAdvancing() && !technique.getTechnique().getStyle().getName().equals(CharacterManagement.BASIC_STYLE))
            	|| technique.getRating(true) == 0) {
            	getCharacter().removeKnownTechnique(technique);
            	adapter.notifyDataSetChanged();
            	return true;
            }
            return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
	
	private TechniqueControlView getTechniqueView(final IKnownTechnique technique, View convertView) {
		TechniqueControlView techniqueView;
		boolean canRemove = (!getCharacter().isAdvancing() && !technique.getTechnique().getStyle().getName().equals(CharacterManagement.BASIC_STYLE)) ||
				( getCharacter().isAdvancing() && technique.getRating(true) == 0);
		ITraitRemovalListener listener = !canRemove ? null :
			new ITraitRemovalListener() {
				@Override
				public void removeTrait() {
					getCharacter().removeKnownTechnique(technique);
					adapter.notifyDataSetChanged();
				}
		};
		if (convertView == null || !(convertView instanceof TechniqueControlView)) {
			techniqueView = new TechniqueControlView(this, adapter,
					getCharacter(),
					technique.getTechnique().getStyle().getName().equals(CharacterManagement.BASIC_STYLE) ? 2 : 1,
					getCharacter().isAdvancing() ? CharacterManagement.ADVANCEMENT_TRAIT_MAX : CharacterManagement.CREATION_TRAIT_MAX,
					technique);
		} else {
			techniqueView = (TechniqueControlView) convertView;
			techniqueView.setTechnique(technique);
		}
		techniqueView.setRemovalListener(listener);

        //techniqueView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
		return techniqueView;
	}
	
	private CheckBox getRefinementView(final Refinement refinement, final IKnownTechnique technique, View convertView) {
		CheckBox refinementBox;
		/*if (convertView != null && convertView instanceof CheckBox) {
			refinementBox = (CheckBox)convertView;
		} else*/ {
			refinementBox = new CheckBox(CharacterManagementStylesActivity.this);
		}
		refinementBox.setText(refinement.toString());
		refinementBox.setChecked(technique.isRefinementLearned(refinement) && technique.canLearnRefinement(refinement));
		refinementBox.setEnabled(technique.canLearnRefinement(refinement));
		
		refinementBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				if (checked) {
					technique.learnRefinement(refinement);
				} else {
					technique.unlearnRefinement(refinement);
				}
				adapter.notifyDataSetChanged();
			}
		});
		return refinementBox;
	}
	
	private class CreationStyleListAdapter extends BaseExpandableListAdapter {

		private IKnownTechnique[] getKnownTechniques(int styleIndex) {
			return getCharacter().getKnownTechniquesForStyle(
					getCharacter().getKnownStyles()[styleIndex]);
		}
		
		private Object[] getItems(int styleIndex) {
			List<Object> items = new ArrayList<Object>();
			for (IKnownTechnique technique : getKnownTechniques(styleIndex)) {
				items.add(technique);
				if (getCharacter().isAdvancing()) {
					for (Refinement refinement : technique.getTechnique().getRefinements()) {
						items.add(refinement);
					}
				}
			}
			return items.toArray(new Object[0]);
		}
		
		private IKnownTechnique getTechniqueForRefinement(Refinement refinement, int styleIndex) {
			for (IKnownTechnique technique : getKnownTechniques(styleIndex)) {
				for (Refinement otherRefinement : technique.getTechnique().getRefinements()) {
					if (refinement.equals(otherRefinement)) {
						return technique;
					}
				}
			}
			return null;
		}
		
		@Override
		public Object getChild(int styleIndex, int techniqueIndex) {
			Object[] items = getItems(styleIndex);
			if (techniqueIndex >= items.length) {
				return null;
			}
			return items[techniqueIndex];
		}

		@Override
		public long getChildId(int styleIndex, int techniqueIndex) {
			return techniqueIndex;
		}

		@Override
		public View getChildView(final int styleIndex, int techniqueIndex, boolean isLastChild, View convertView,
				ViewGroup arg4) {
			if (isLastChild && hasNewTechniquePicks(styleIndex)) {
				TextView newView = new TextView(CharacterManagementStylesActivity.this);
				newView.setText("Add New Technique...");
				newView.setTextSize(18);
				return newView;
			}
			final Object obj = getChild(styleIndex, techniqueIndex);

			if (obj instanceof Refinement) {
				return getRefinementView((Refinement) obj, getTechniqueForRefinement((Refinement) obj, styleIndex), convertView);
			} else {
				return getTechniqueView((IKnownTechnique) obj, convertView);
			}
		}
		
		private boolean hasNewTechniquePicks(int styleIndex) {
			Style style = getCharacter().getKnownStyles()[styleIndex];
			return TechniqueRegistry.getInstance().getNewTechniquesForStyle(getCharacter(), style).length > 0;
		}

		@Override
		public int getChildrenCount(int styleIndex) {
			Style style = (Style) getGroup(styleIndex);
			if (style == null) {
				return 0;
			}
			Object[] items = getItems(styleIndex); 
			return items.length + (hasNewTechniquePicks(styleIndex)  ? 1 : 0);
		}

		@Override
		public Object getGroup(int styleIndex) {
			Style[] knownStyles = getCharacter().getKnownStyles();
			if (styleIndex >= knownStyles.length) {
				return null;
			}
			return knownStyles[styleIndex];
		}

		@Override
		public int getGroupCount() {
			return getCharacter().getKnownStyles().length + 
					(TechniqueRegistry.getInstance().getNewStyles(getCharacter().getKnownStyles(), getCharacter().getType(),
							getCharacter().getSubtype()).length > 0 ? 1 : 0);
		}

		@Override
		public long getGroupId(int styleIndex) {
			return styleIndex;
		}

		@Override
		public View getGroupView(int styleIndex, boolean isExpanded, View convertView,
				ViewGroup arg3) {
			View view = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(CharacterManagementStylesActivity.this);
	        	view = inflater.inflate(android.R.layout.simple_expandable_list_item_2, null, false);
			} else {
				view = convertView;
			}
			TextView text = (TextView) view.findViewById(android.R.id.text1);
			if (!isNewStyleIndex(styleIndex)) {
				text.setText(getGroup(styleIndex).toString());
			} else {
				text.setText("Add New Style...");
			}
			return view;
		}
		
		private boolean isNewStyleIndex(int index) {
			return index == getCharacter().getKnownStyles().length;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int styleIndex, int techniqueIndex) {
			return !(getChild(styleIndex, techniqueIndex) instanceof Refinement);
			/*return techniqueIndex == getCharacter().getKnownTechniquesForStyle(
					getCharacter().getKnownStyles()[styleIndex]).length;*/
		}
	}
}
