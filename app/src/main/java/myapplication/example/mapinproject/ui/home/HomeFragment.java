package myapplication.example.mapinproject.ui.home;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.TweeitCallback;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.SearchConditions;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;
import myapplication.example.mapinproject.ui.postdetail.PostDetailFragment;

public class HomeFragment extends Fragment implements OnMapLongClickListener,OnMapClickListener,OnMapReadyCallback,OnMarkerClickListener {

    private GoogleMap mMap;

    // A default location (Sapporo) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(43.068625, 141.350801);

    private static final String TAG = HomeFragment.class.getSimpleName();

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    private static final int DEFAULT_ZOOM = 15;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        View view = inflater.inflate(R.layout.home, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 参照 -> https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        getDeviceLocation();
    }

    @Override
    public void onMapLongClick(LatLng point) {
        PostAddFragment postAdd = new PostAddFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", point.latitude);
        bundle.putDouble("longitude", point.longitude);
        postAdd.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, postAdd);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        PostDetailFragment postDetail = new PostDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tweeit", (Tweeit) marker.getTag());
        postDetail.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, postDetail);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        return false;
    }

    @Override
    public void onMapClick(LatLng point) {

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        SearchConditions conditions = new SearchConditions();
        DatabaseManager.getTweeit(conditions, new TweeitCallback() {
            @Override
            public void getTweeitCallBack(HashMap<String, Tweeit> map) {
                for (Tweeit tweeit : map.values()) {
                    Double latitude = Double.parseDouble(tweeit.getLocations().getLatitude());
                    Double longitude =  Double.parseDouble(tweeit.getLocations().getLongitude());
                    LatLng latLng = new LatLng(latitude,longitude);
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng);
                    options.title(tweeit.getTweeitTitle());
                    options.draggable(false);
                    //options.anchor(tweeit.getUserId());
                    //options.getIcon(tweeit.getImagePath());
                    mMap.addMarker(options).setTag(tweeit);
                }
            }
        });
        super.onViewStateRestored(savedInstanceState);
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = task.getResult();
                        if (mLastKnownLocation != null) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


}
