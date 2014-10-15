package fr.valentin.BrainTrust;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import fr.valentin.BrainTrust.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ToggleButton;

public class SettingsActivity extends SherlockActivity {

	private SharedPreferences settings;
	private static final String PREFS_NAME = "MyPrefsFile";
	private ToggleButton music;
	private ToggleButton hardmode;
	private ToggleButton sound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		getActionBar().setTitle("Settings");
		settings = this.getSharedPreferences(PREFS_NAME, 0);
		music = (ToggleButton) findViewById(R.id.musicOnOff);
		hardmode = (ToggleButton) findViewById(R.id.toggleHM);
		sound = (ToggleButton) findViewById(R.id.toggleSound);
		if (settings.getBoolean("music", false) == true)
			music.setChecked(true);
		if (settings.getBoolean("hm", false) == true)
			hardmode.setChecked(true);
		if (settings.getBoolean("sound", false) == true)
			sound.setChecked(true);
	}

	@Override
	public void onPause() {
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("music", music.isChecked());
		editor.putBoolean("hm", hardmode.isChecked());
		editor.putBoolean("sound", sound.isChecked());
		editor.commit();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getSupportMenuInflater();
		inflater.inflate(R.menu.test, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
