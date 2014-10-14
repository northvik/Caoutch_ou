package data.com.caoutchou;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import data.com.model.Distributeur;
import data.com.model.Pharmacie;

public class MapsActivity extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener, ActionBar.OnNavigationListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap; // Peut être null si Google Play services APK n'est pas dispo.
    private LocationManager mLocMgr;
    private LocationClient mLocationClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();
    private ArrayList<Distributeur> distributeurs = new ArrayList<Distributeur>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actBar = getSupportActionBar();
        setContentView(R.layout.activity_maps);
        JSONArray jsonArrayPharmacie = getJsonFile(getApplicationContext(), "pharmacie.json");
        JSONArray jsonArrayDistributeurs = getJsonFile(getApplicationContext(), "preservatif.json");
        createPharmacies(jsonArrayPharmacie);
        createDistributeurs(jsonArrayDistributeurs);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationClient = new LocationClient(this, this, this);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, mLocationListener);
        if (isGpsOn()){
            setUpMapIfNeeded();
        }
        else{
            showGPSDisabledAlertToUser();
        }
    }

    private boolean isGpsOn() {
        return mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private JSONArray getJsonFile(Context context, String filename) {
        String result = null;
        JSONArray jsonArray = new JSONArray();
        StringBuilder sb = new StringBuilder();
        AssetManager manager = context.getAssets();
        InputStream file = null;
        BufferedReader reader = null;
        try {
            file = manager.open(filename);
            reader = new BufferedReader(new InputStreamReader(file, "UTF-8"), 8);
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            reader.close();
            result = sb.toString();
            jsonArray = new JSONArray(result);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void createPharmacies(JSONArray jsonArray)
    {
        pharmacies.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i) != null)
                {
                    Pharmacie pharma = new Pharmacie();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    pharma.setName(jsonObject.getString("rs"));
                    pharma.setTelephone(jsonObject.getString("telephone"));
                    pharma.setAdrComplete(jsonObject.getString("adresse"));
                    pharma.setLng((double) jsonObject.getDouble("longitude"));
                    pharma.setLat((double) jsonObject.getDouble("latitude"));
                    pharmacies.add(pharma);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createDistributeurs(JSONArray jsonArray)
    {
        distributeurs.clear();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i) != null)
                {
                    Distributeur distrib = new Distributeur();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    distrib.setName(jsonObject.getString("rs"));
                    distrib.setAcces(jsonObject.getString("access"));
                    distrib.setAdrComplete(jsonObject.getString("adresse"));
                    distrib.setLng((double) jsonObject.getDouble("longitude"));
                    distrib.setLat((double) jsonObject.getDouble("latitude"));
                    distributeurs.add(distrib);
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Voulez vous activer votre GPS ?")
                .setCancelable(false)
                .setPositiveButton("Activer le GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Annuler",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }

    LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
        @Override
        public void onLocationChanged(Location location) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mentions:
                    startSettings(findViewById(R.id.map));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Crée un intent auquel on passe l'activity Settings
     * Puis lances cette activité à l'écran
     * @param v Vue courante
     */
    public void startSettings(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    /**
     * Prépares la map si c'est possible et qu'elle n'a pas déjà été instancié.
     * De cette manière on s'assure que l'on fait appel à setMap que si {@link #mMap} est null
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                setUpMap();
                mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater(), getApplicationContext()));
                mMap.setOnInfoWindowClickListener(this);
            }
        }
    }

    /**
     * Les ajouts de marqueurs / de lignes / déplacement de caméra doivent être appelé dans cette fonction
     * Elle doit être appelée une seule fois pour être sur que {@link #mMap} n'est pas null
     */
    private void setUpMap() {
        for (Pharmacie pharma : pharmacies)
        {
            setUpMarkerPharma(pharma.getName(), pharma.getAdrComplete(), pharma.getTelephone(), pharma.getLng(), pharma.getLat());
        }

        for (Distributeur distrib : distributeurs)
        {
            setUpMarkerDistrib(distrib.getName(), distrib.getAdrComplete(), distrib.getHoraires(), distrib.getLng(), distrib.getLat());
        }

        setUpMarkerDistrib("Stade Jean Pierre Wimille", "56 Bd de l'Amiral Bruix 75016 Paris  France", "7h à 22h30", 48.872568, 2.275998);
        setUpMarkerPharma("SELARL PHARMACIE MATHIAU LAM", "3 RUE JEANNE D'ARC, 75013 PARIS", "145834022", 48.8287599, 2.3695644);

    }


    private void setUpMarkerDistrib(String title, String address,String horaire, Double lat, Double lng) {
        StringBuilder snippet = new StringBuilder();
        snippet.append(address).append("\nHoraires: ").append(horaire);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_distrib))
                .snippet(snippet.toString()));
    }

    private void setUpMarkerPharma(String title, String address, String telephone, Double lat, Double lng) {
        StringBuilder snippet = new StringBuilder();
        snippet.append(address).append("\nTéléphone: ").append(telephone);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharma))
                .snippet(snippet.toString()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isGooglePlayServicesAvailable() && isGpsOn()){
            mLocationClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mLocationClient.connect();
                        break;
                }
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check si Google Play services est dispo
        int resultCode =  GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Récupérer le DialogError depuis Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog( resultCode,
                    this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
            if (errorDialog != null) {
                // Crée Dialog Fragment contenant l'erreur
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }
            return false;
        }
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        Location location = mLocationClient.getLastLocation();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        setUpMapIfNeeded();
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Déconnecté. Veuillez vous reconnecter.",
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Tente de résoudre l'erreur ne lançant une activité
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Désolé. Aucun signal GPS trouvé !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    /**
     * Lance l'itinéraire sur ce marker lorsque l'on clique dessus
     */
    public void onInfoWindowClick(Marker marker) {
       Toast.makeText(this, "Chargement de l'itinéraire", Toast.LENGTH_LONG).show();
        final Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                ("http://maps.google.com/maps?" + "&daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
        navigationIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(navigationIntent);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }


    // DialogFragment comprennant les erreurs
    public static class ErrorDialogFragment extends DialogFragment {

        private Dialog mDialog;

        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        @android.support.annotation.NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

}
