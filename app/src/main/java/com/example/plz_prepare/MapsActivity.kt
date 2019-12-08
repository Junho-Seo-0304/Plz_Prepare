package com.example.plz_prepare

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener{

    private lateinit var mMap: GoogleMap
    var LX : Double = 37.600562
    var LY : Double = 126.864789

    val locationManager : LocationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val currentLocation = LatLng(LX,LY)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15.toFloat()))
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, this)
        } else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)){

            }else {
                requestPermissions(Array(1) { android.Manifest.permission.ACCESS_FINE_LOCATION },1)
            }
        }
        database = FirebaseDatabase.getInstance().reference.child("Users")

        val InfomarkerList = arrayListOf<InfoMarker>()
        database.addValueEventListener(object : ValueEventListener  {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                googleMap.clear()
                InfomarkerList.clear()
                for(i in p0.children){
                    for(j in i.children){
                        val x = j.child("rlocationX").value.toString().toDouble()
                        val y = j.child("rlocationY").value.toString().toDouble()
                        val marker = MarkerOptions().title(j.child("rname").value.toString()).position(LatLng(x,y))
                        mMap.addMarker(marker)
                        InfomarkerList.add(InfoMarker(i.key.toString(),j.key.toString()))
                    }
                }
            }
        })

        mMap.setOnInfoWindowClickListener {
            for(i in InfomarkerList.indices) {
                if(it.id=="m"+i.toString()) {
                    val intent = Intent(baseContext, RestaurantActivity::class.java)
                    intent.putExtra("Category", InfomarkerList[i].category)
                    intent.putExtra("uid", InfomarkerList[i].uid)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==1){
            if (grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, this)
                }
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        if(location!=null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),15.0f))
        }
    }

    override fun onProviderDisabled(provider: String?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }
}
