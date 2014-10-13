package data.com.caoutchou;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private GoogleMap mMap; // Peut être null si Google Play services APK n'est pas dispo.
    private LocationManager mLocMgr;
    private LocationClient mLocationClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actBar = getSupportActionBar();
        actBar.setSubtitle("Le préservatif c'est le kiff");
        setContentView(R.layout.activity_maps);
        mLocMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,500, 0, mLocationListener);
        mLocationClient = new LocationClient(this, this, this);
        setUpMapIfNeeded();
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(48.872568, 2.275998)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_distrib)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(48.899628, 2.351833)).title("Test2").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharma)));
    }

    private void setUpMarkerDistrib(String title, String address, Integer lat, Integer lng) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_distrib)));
    }

    private void setUpMarkerPharma(String title, String address, Integer lat, Integer lng) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pharma)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isGooglePlayServicesAvailable()){
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
