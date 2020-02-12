package myapplication.example.mapinproject.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.TweeitCallback;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.Params;
import myapplication.example.mapinproject.data.entities.SearchConditions;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;
import myapplication.example.mapinproject.ui.postdetail.PostDetailFragment;

import static com.google.maps.android.SphericalUtil.computeDistanceBetween;

public class HomeFragment extends Fragment implements OnMapLongClickListener,OnMapClickListener,OnMapReadyCallback,OnMarkerClickListener {

    private GoogleMap mMap;

    private Circle circle;

    private CircleOptions circleOptions;

    private final LatLng mDefaultLocation = new LatLng(43.068625, 141.350801);

    private static final String TAG = HomeFragment.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private Location mLastKnownLocation;

    private static final int DEFAULT_ZOOM = 15;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        View view = inflater.inflate(R.layout.home, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 参照 -> https://developers.google.com/android/reference/com/google/android/gms/maps/GoogleMap
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
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
    public void onMapClick(LatLng point) {}

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        final SearchConditions conditions = verfiedCondition();
        DatabaseManager.getTweeit(conditions, new TweeitCallback() {
            @Override
            public void getTweeitCallBack(HashMap<String, Tweeit> map) {
                if (mLastKnownLocation != null) {
                    drawCircle(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),conditions.getRadiusRange());
                } else {
                    drawCircle(mDefaultLocation,conditions.getRadiusRange());
                }

                for (Tweeit tweeit : map.values()) {
                    //フィルタリング処理
                    final String title = tweeit.getTweeitTitle();
                    if (title.length() != 0 && !title.contains(conditions.getSearchWords())) {
                        //マッチしないものは、スキップ
                        continue;
                    }
                    if (!(tweeit.getEvaluation() >= conditions.getRating())) {
                        //未満のものは、スキップ
                        continue;
                    }
                    double latitude = Double.valueOf(tweeit.getLocations().getLatitude());
                    double longitude =  Double.valueOf(tweeit.getLocations().getLongitude());
                    LatLng latLng = new LatLng(latitude,longitude);
                    double distance = computeDistanceBetween(latLng,circleOptions.getCenter());
                    if (distance > circleOptions.getRadius()) {
                        //円の外側なので、スキップ
                        continue;
                    }
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

    private SearchConditions verfiedCondition() {
        SearchConditions conditions = Params.searchConditions;
        if (conditions == null) {
            conditions = new SearchConditions();
        }
        return conditions;
    }


    private void drawCircle(LatLng point, int radius){
        if (circle != null) {
            //古い円を削除
            circle.remove();
        }
        circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x30ff0000);
        circleOptions.strokeWidth(1);
        circle = mMap.addCircle(circleOptions);
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
