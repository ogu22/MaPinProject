package myapplication.example.mapinproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.business.TweeitCallback;
import myapplication.example.mapinproject.business.DatabaseManager;
import myapplication.example.mapinproject.data.entities.SearchConditions;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.ui.postadd.PostAddFragment;

public class HomeFragment extends Fragment implements OnMapClickListener,OnMapReadyCallback {

    private GoogleMap mMap;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sapporo = new LatLng(43.068625, 141.350801);
        mMap.addMarker(new MarkerOptions().position(sapporo).title("Marker in Sapporo!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sapporo));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
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
                    mMap.addMarker(options);
                }
            }
        });
        super.onViewStateRestored(savedInstanceState);
    }
}