package data.com.caoutchou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

public class SettingsActivity extends ActionBarActivity {

    private SharedPreferences settings;
    private static final String PREFS_NAME = "MyPrefsFile";
    private Switch pharma;
    private Switch distrib;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        settings = this.getSharedPreferences(PREFS_NAME, 0);
		setContentView(R.layout.activity_settings);
		getSupportActionBar().setTitle("Préferences");
        pharma = (Switch) findViewById(R.id.pharmaSwitch);
        distrib = (Switch) findViewById(R.id.distribSwitch);
        pharma.setChecked(settings.getBoolean("pharma", true));
        distrib.setChecked(settings.getBoolean("distrib", true));

	}

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pharma", pharma.isChecked());
        editor.putBoolean("distrib", distrib.isChecked());
        editor.commit();
        super.onPause();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MapsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}



}
