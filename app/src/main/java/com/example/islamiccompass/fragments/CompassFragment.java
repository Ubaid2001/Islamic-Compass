package com.example.islamiccompass.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.islamiccompass.R;
import com.example.islamiccompass.api.RandomApi;
import com.example.islamiccompass.helper.GeoResponseObject;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompassFragment extends Fragment implements SensorEventListener {

    private OnFragmentInteractionListener mListener;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private FusedLocationProviderClient fusedLocationProviderClientKaaba;

    private Location userlocation;
    private Location destLoc;

    private double longitude;
    private double latitude;
    private double altitude;
    private double destLocLatitude;
    private double destLocLongitude;

    private ImageView compassView;
    private ImageView pointerView;
    private TextView bearingView;
    private TextView bearingInfo;
    private TextView coordinates;

    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currentAzimuth = 0f;

    private SensorManager mSensorManager;

    private float bearTo = 0f;
    private float head = 0f;

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
            hashmap -> {
                for (Map.Entry<String, Boolean> entry : hashmap.entrySet()) {
                    String permissionName = entry.getKey();
                    Boolean isGranted = entry.getValue();
                    System.out.println("Permission: " + permissionName + " isGranted: " + isGranted);

                    if (isGranted) {
                        getLocations();
                        Toast.makeText(getActivity(), " Location Permission! IN USE!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Need Location Permission!", Toast.LENGTH_SHORT).show();
                        System.out.println("Compass stop functioning!!! COMPASS NEEDS LOCATION");
                    }
                }
            });


    View view;

    public CompassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_compass, container, false);
        compassView = (ImageView) view.findViewById(R.id.compass);
        pointerView = (ImageView) view.findViewById(R.id.pointer);
        bearingView = (TextView) view.findViewById(R.id.bearing);
        bearingInfo = (TextView) view.findViewById(R.id.bearingInfo);
        coordinates = (TextView) view.findViewById(R.id.coordinates);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Fragment hadithFragment = new HadithFragment();
        Fragment salahFragment = new SalahFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction
                .replace(R.id.hadith_fragment, hadithFragment)
                .replace(R.id.salah_fragment, salahFragment)
                .commit();


    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("!!!On Start!!!");
        getLocations();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getGeocode();
            }
        }, 500);
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onSensorChanged(@NonNull SensorEvent event) {
        final float alpha = 0.97f;

        head = Math.round(event.values[0]);
        synchronized (this) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha) * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha) * event.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha) * event.values[2];
            }
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;
                azimuth -= 10;


                Animation anim = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currentAzimuth = azimuth;
                anim.setDuration(500);
                anim.setRepeatCount(0);
                anim.setFillAfter(true);

                compassView.startAnimation(anim);
                RotateAnimation raQibla = new RotateAnimation(-currentAzimuth, bearTo, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                raQibla.setDuration(500);
                raQibla.setRepeatCount(0);
                raQibla.setFillAfter(true);
                pointerView.startAnimation(raQibla);


            }
        }

    }

    private void getLocations(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationProviderClientKaaba = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
            getQiblaDirection();

        } else requestPermissionLauncher.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    userlocation = task.getResult();

                    if (userlocation != null) {
                        latitude = userlocation.getLatitude();
                        longitude = userlocation.getLongitude();
                        altitude = userlocation.getAltitude();

                    } else {
                        LocationRequest locationRequest = LocationRequest.create()
                                .setInterval(100)
                                .setFastestInterval(3000)
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setMaxWaitTime(100)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();

                                latitude = location1.getLatitude();
                                longitude =  location1.getLongitude();
                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());

                    }

                    Bundle bundle = new Bundle();
                    bundle.putDouble("lat", latitude);
                    bundle.putDouble("long", longitude);
                    getChildFragmentManager().setFragmentResult("requestKey", bundle);
                    System.out.println("This is from the Compass Fragment: "+latitude + " & " +longitude);

                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void getQiblaDirection(){

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            fusedLocationProviderClientKaaba.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    destLoc = task.getResult();

                    if (destLoc != null) {

                        destLoc.setLatitude(21.422487); //kaaba latitude setting
                        destLoc.setLongitude(39.826206); //kaaba longitude setting

                        destLocLatitude = destLoc.getLatitude();
                        destLocLongitude = destLoc.getLongitude();

                        bearTo = userlocation.bearingTo(destLoc);

                        float bearing = (float) (Math.round(bearTo * 100.0) / 100.0);
                        bearingView.setText(bearing + "\u00B0");
                        coordinates.append(latitude+", "+longitude);


                        GeomagneticField geoField = new GeomagneticField(Double.valueOf(latitude).floatValue(), Double
                            .valueOf(longitude).floatValue(),
                            Double.valueOf(altitude).floatValue(),
                            System.currentTimeMillis());
                        head -= geoField.getDeclination();

                        if (bearTo < 0) bearTo = bearTo + 360;


                        pointerView.setRotation(bearTo);

                    }
                    else {
                        LocationRequest destinationRequest = LocationRequest.create()
                                .setInterval(100)
                                .setFastestInterval(3000)
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setMaxWaitTime(100)
                                .setNumUpdates(1);
                        LocationCallback destinationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                Location location1 = locationResult.getLastLocation();

                                destLocLatitude =  location1.getLatitude();
                                destLocLongitude = location1.getLongitude();
                            }
                        };

                        fusedLocationProviderClientKaaba.requestLocationUpdates(destinationRequest, destinationCallback, Looper.myLooper());

                    }
                }

            });

        }
    }

    private void getGeocode(){

        if(latitude != 0.0 && longitude != 0.0){
            double lat = latitude;
            double lon = longitude;
            final String api_key = "674f10b801a24299655695pbk78675b";

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://geocode.maps.co")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            RandomApi randomApi = retrofit.create(RandomApi.class);

            Call<GeoResponseObject> call = randomApi.getGeocode(lat, lon, api_key);

            call.enqueue(new Callback<GeoResponseObject>() {
                @Override
                public void onResponse(Call<GeoResponseObject> call, Response<GeoResponseObject> response) {
                    if(!response.isSuccessful()){
                        System.out.println("Code: "+response.code());
                        return;
                    }

                    System.out.println("The Android is connected to the API endpoint!!!");

                    GeoResponseObject geoResponseObject = response.body();
                    GeoResponseObject.Address address = geoResponseObject.address;

                    System.out.println(address.city);
                    System.out.println(address.iso3166_2_lv14);

                    bearingInfo.append(address.state);
                    bearingInfo.append(", ");
                    bearingInfo.append(address.county);
                    bearingInfo.append(", ");
                    bearingInfo.append(address.iso3166_2_lv14);

                }

                @Override
                public void onFailure(Call<GeoResponseObject> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        getChildFragmentManager().setFragmentResultListener("isSalah", this, (requestKey, bundle) -> {
            boolean isSalah = bundle.getBoolean("isSalah");
            System.out.println("isSalah in the compass fragment: "+isSalah);
            initiateAlarmScreen(isSalah);
        });
    }

    private void initiateAlarmScreen(boolean isSalah) {
        if(isSalah) System.out.println("Hello we are creating the alarm screen");
        else System.out.println("Hello we are not creating the alarm screen");
    }

    @Override
    public void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void messageFromChildFragment(Uri uri);
    }

}