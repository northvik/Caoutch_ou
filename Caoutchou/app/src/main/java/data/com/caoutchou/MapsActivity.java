package data.com.caoutchou;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonReader;
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
import org.json.JSONObject;

import java.io.FileReader;
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
        actBar.setSubtitle("Le préservatif c'est le kiff");
        setContentView(R.layout.activity_maps);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationClient = new LocationClient(this, this, this);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, mLocationListener);
        if (isGpsOn()){
            setUpMapIfNeeded();
            mMap.setInfoWindowAdapter(new PopupAdapter(getLayoutInflater()));
            mMap.setOnInfoWindowClickListener(this);
        }
        else{
            showGPSDisabledAlertToUser();
        }
    }

    private boolean isGpsOn() {
        return mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Voulez vous activer votre GPS ?")
                .setCancelable(false)
                .setPositiveButton("Activer le GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
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
            }
        }
    }

    /**
     * Les ajouts de marqueurs / de lignes / déplacement de caméra doivent être appelé dans cette fonction
     * Elle doit être appelée une seule fois pour être sur que {@link #mMap} n'est pas null
     */
    private void setUpMap() {
        pharmacies.clear();
        distributeurs.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(50, 0)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(80, 0)).title("Test2"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24, 0)).title("Test3"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(78, 0)).title("Marker2"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(12, 0)).title("Marker3"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(45, 0)).title("Marker4").rotation(34));
        setUpMarkerDistrib("Stade Jean Pierre Wimille", "56 Bd de l'Amiral Bruix 75016 Paris  France", "7h à 22h30", 48.872568, 2.275998);


    }


    private void setUpMarkerDistrib(String title, String address,String horaire, Double lat, Double lng) {
        StringBuilder snippet = new StringBuilder();
        snippet.append(address).append("\nHoraires: ").append(horaire);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_distrib))
                .snippet(snippet.toString()));
    }

    private void setUpMarkerPharma(String title, String address, String horaire, Double lat, Double lng) {
        StringBuilder snippet = new StringBuilder();
        snippet.append(address).append("\nHoraires: ").append(horaire);
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
    public void onInfoWindowClick(Marker marker) {
       //Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
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
