package data.com.caoutchou;

/**
 * Created by valentin on 10/15/14.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import data.com.model.Distributeur;
import data.com.model.DistributeurDataSource;
import data.com.model.Pharmacie;
import data.com.model.PharmacieDataSource;

public class SplashActivity extends ActionBarActivity {

    private final int SPLASH_DISPLAY_LENGTH = 500;
    private SharedPreferences settings;
    private static final String PREFS_NAME = "MyPrefsFile";
    private PharmacieDataSource pharmacieDataSource;
    private DistributeurDataSource distributeurDataSource;
    private boolean isDbEmpty;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        pharmacieDataSource = new PharmacieDataSource(this);
        distributeurDataSource = new DistributeurDataSource(this);
        try {
            pharmacieDataSource.open();
            isDbEmpty = pharmacieDataSource.isEmpty();
            pharmacieDataSource.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.splashscreen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (isDbEmpty)
                {
                    createDB();
                }
                Intent mainIntent = new Intent(SplashActivity.this,MapsActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    public JSONArray loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;

    }

    public void createDB()
    {
        JSONArray jsonArrayPharmacie = loadJSONFromAsset("pharmacie-idf.json");
        JSONArray jsonArrayDistributeurs = loadJSONFromAsset("preservatif.json");
        createPharmacies(jsonArrayPharmacie);
        createDistributeurs(jsonArrayDistributeurs);
    }

    private void createPharmacies(JSONArray jsonArray)
    {

        try {
            pharmacieDataSource.open();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i) != null)
                {
                    Pharmacie pharma = new Pharmacie();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    pharma.setName(jsonObject.getString("name"));
                    pharma.setTelephone(jsonObject.getString("telephone"));
                    pharma.setAdrComplete(jsonObject.getString("adresse_complete"));
                    pharma.setLng(jsonObject.getDouble("lng"));
                    pharma.setLat(jsonObject.getDouble("lat"));
                    pharmacieDataSource.createPharmacie(pharma);
                }
            }
            pharmacieDataSource.close();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createDistributeurs(JSONArray jsonArray)
    {
        try {
            distributeurDataSource.open();
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i) != null)
                {
                    Distributeur distrib = new Distributeur();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    distrib.setName(jsonObject.getString("name"));
                    distrib.setAdrComplete(jsonObject.getString("adresse_complete"));
                    distrib.setLng(jsonObject.getDouble("lng"));
                    distrib.setLat(jsonObject.getDouble("lat"));
                    distributeurDataSource.createDistributeur(distrib);
                }
            }
            distributeurDataSource.close();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
