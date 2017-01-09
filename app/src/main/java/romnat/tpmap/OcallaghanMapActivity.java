package romnat.tpmap;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

public class OcallaghanMapActivity extends FragmentActivity implements OnMapReadyCallback {

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocallaghan_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                /*
                 * Choose a random message inside a prepared list
                 * and display it as a Toast at the bottom of the screen on shake
                 */
                String[] randomMessages = {
                        getString(R.string.random_msg_1),
                        getString(R.string.random_msg_2),
                        getString(R.string.random_msg_3),
                        getString(R.string.random_msg_4),
                        getString(R.string.random_msg_5)
                };

                Random random = new Random();
                int shuffle = random.nextInt(randomMessages.length);
                String randomMessage = randomMessages[shuffle];

                Toast.makeText(                 // Create toast in view
                    getApplicationContext(),    // Set App Context
                    randomMessage,              // Set Message
                    Toast.LENGTH_LONG           // Set Duration
                ).show();                       // Display on screen



            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap mMap) {

        // Defines map type to standard (no satellite image)
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Defines zoom level
        float MapFocale = 16.0f;

        // Defines chosen location
        LatLng OCallaghanLocation = new LatLng(45.193678, 5.729407);

        // Defines a new marker with the chosen location's information
        MarkerOptions OCallaghanMarker = new MarkerOptions()
                .position(OCallaghanLocation)
                .title(getString(R.string.bar_name))
                .snippet(getString(R.string.bar_slogan));

        // Adds the new Marker on the map
        mMap.addMarker(OCallaghanMarker);

        // Focus on specified location at specified zoom level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(OCallaghanLocation, MapFocale));

        // Adds a behaviour to trigger when clicking on the marker
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            /**
             * Opens a new window containing information about the O'Callaghan
             * Pressing back button automatically quits this window and go back to the map
             * @param marker defined when adding OCallaghanMarker to mMap and automatically used here
             */
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent myIntent = new Intent(OcallaghanMapActivity.this, OcallaghanDescriptionActivity.class);
                // myIntent.putExtra("key", value); //Optional parameters
                OcallaghanMapActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
