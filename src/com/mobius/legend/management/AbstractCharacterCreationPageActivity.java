package com.mobius.legend.management;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.mobius.legend.R;
import com.mobius.legend.character.CharacterData;
import com.mobius.legend.character.CharacterRoster;
import com.mobius.legend.character.RandomCharacterGenerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class AbstractCharacterCreationPageActivity extends Activity {
	
	private CharacterManagementOverview overview;
	
	public void onCreate(Bundle savedInstanceState, int layoutID) {
		super.onCreate(savedInstanceState);
		setContentView(layoutID);
		
		this.overview = (CharacterManagementOverview) this.findViewById(R.id.management_overview);
	}
	
	protected CharacterData getCharacter() {
		return CharacterManagement.character;
	}
	
	public void updateOverview() {
		overview.updateOverview(this);
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(Menu.NONE, R.string.Save, Menu.NONE, R.string.Save);
		//menu.add(Menu.NONE, R.string.SaveAs, Menu.NONE, R.string.SaveAs);
		if (CharacterRoster.getInstance(this).hasCharacter(getCharacter())) {
			menu.add(Menu.NONE, R.string.Delete, Menu.NONE, R.string.Delete);
		}
		if (!getCharacter().isAdvancing() && CharacterManagement.isCreationComplete()) {
			menu.add(Menu.NONE, R.string.CompleteCreation, Menu.NONE, R.string.CompleteCreation);
		}
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.string.Save:
			save();
			break;
		case R.string.SaveAs:
			saveAs();
			break;
		case R.string.Delete:
			delete();
			break;
		case R.string.CompleteCreation:
			completeCreation();
			break;
		}
		return true;
	}
	
	private void save() {
		CharacterData data = getCharacter();
		String name = data.getName();
		if (name == "") {
			showToastDialog(R.string.NameMustNotBeBlank);
			return;
		}
		try {
			String fileName = RandomCharacterGenerator.getFileNameForCharacter(data);
			FileOutputStream outStream = openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream out = new ObjectOutputStream(outStream);
			out.writeObject(data);
			out.close();
			CharacterRoster.getInstance(this).addCharacter(data);
			showToastDialog(R.string.CharacterSaved);
		} catch (IOException e) {
			showToastDialog(R.string.ErrorSaving);
		}
	}
	
	private void delete() {
		if (CharacterRoster.getInstance(this).deleteCharacter(this, getCharacter())) {
			finish();
			showToastDialog(R.string.CharacterDeleted);
		} else {
			showToastDialog(R.string.ErrorDeleting);
		}
	}
	
	private void saveAs() {
		
	}
	
	private void completeCreation() {
		getCharacter().setAdvancing();
		Intent intent = new Intent(this, this.getClass());
		this.startActivity(intent);
		finish();
	}
	
	private void showToastDialog(int errorString) {
		Toast toast = Toast.makeText(this, errorString, Toast.LENGTH_SHORT);
		toast.show();
	}
}
