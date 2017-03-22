package h2l.se.uit.placesaroundme;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import  android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.widget.AutoCompleteTextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Document;

import java.util.ArrayList;

import h2l.se.uit.placesaroundme.dialogs.DetailsMarker;
import h2l.se.uit.placesaroundme.dialogs.DetailsMarker_;

@EActivity(R.layout.activity_maps)
public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback {

    @ViewById(R.id.txt_place)
    AutoCompleteTextView txt_place;

    private GoogleMap mMap;

    private ArrayList<Marker> markers=new ArrayList<Marker>();
    Marker mym;
    Marker marker_b;

    @AfterViews
    void initView(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String[] places = new String[]{"Atm","Bus station","Hotel","Bar","Parking","Pharmacy ","School","Bank","Coffee","Restaurant","Shop","Company","Park","Market","Supper market"};

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,places);

        txt_place.setAdapter(adapter);
        txt_place.setThreshold(1);
    }

    @ItemClick(R.id.txt_place)
    void ItemClick(String position){
        LatLng sydney = new LatLng(10.762622,106.660172);
        mym=mMap.addMarker(new MarkerOptions().position(sydney).title(position));
        markers.add(mym);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng arg0) {
                // TODO Auto-generated method stub
                marker_b = mMap.addMarker(
                        new MarkerOptions()
                                .position(arg0)
                                .title("my position")
                                .snippet("tui dang di bui o day")
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_ROSE)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(arg0, 15));
                markers.add(marker_b);

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub
                marker_b=arg0; //gantruocde can thiveduongdi
                DetailsMarker detailsMarker = DetailsMarker_.newInstance(new DetailsMarker.CallBackRaw() {//goi interface ben dialog
                    @Override
                    public void finish() {
                      veduongdi();
                    }
                });
                detailsMarker.show(getSupportFragmentManager(), "showDetailsMarker");
            }
        });

    }

    class veduongdixml extends AsyncTask<Double, Void, Void>
    {
        ArrayList<LatLng> mangtoado;
        @Override
        protected Void doInBackground(Double... params) {
            // TODO Auto-generated method stub
            //Log.d("json", params[0]+","+params[1]+"..."+params[2]+","+params[3]);
            Direction md = new Direction();
            LatLng x=new LatLng(params[0], params[1]);
            LatLng y=new LatLng(params[2],params[3]);
            Document doc = md.getDocument(x, y, Direction.MODE_DRIVING);
            mangtoado = md.getDirection(doc);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED); // Màu và độrộng

            for(int i = 0 ; i <mangtoado.size() ; i++) {
                rectLine.add(mangtoado.get(i));
            }
            mMap.addPolyline(rectLine);
        }

    }

    public void veduongdi()
    {
        Toast.makeText(MapsActivity.this, "nhan nut",Toast.LENGTH_SHORT).show();
        veduongdixml a=new veduongdixml();
        a.execute(mym.getPosition().latitude,
                mym.getPosition().longitude,
                marker_b.getPosition().latitude,
                marker_b.getPosition().longitude);

    }



        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12);
        mMap.setMaxZoomPreference(30);

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(10.762622, 	106.660172);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



}
