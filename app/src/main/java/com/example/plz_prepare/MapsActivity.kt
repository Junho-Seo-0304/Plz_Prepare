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
    // 현재 위치에 대한 permission을 수락 하지 않았을 때 기본 위치를 한국항공대학교의 위도, 경도로 설정
    val locationManager : LocationManager by lazy { getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
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
            //현 위치에 대한 permission이 수락 되어 있으면 현 위치를 맵에 보여준다.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, this)
        } else{
            if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)){
                // 이미 거절되어 있는 상태이면 수락창을 띄우지 않고 아무것도 하지 않는다.
            }else {
                // 거절 되어 있는 상태가 아니라면 permission을 request한다.
                requestPermissions(Array(1) { android.Manifest.permission.ACCESS_FINE_LOCATION },1)
            }
        }

        database = FirebaseDatabase.getInstance().reference.child("Users")

        val InfomarkerList = arrayListOf<InfoMarker>()
        database.addValueEventListener(object : ValueEventListener  {
            override fun onCancelled(p0: DatabaseError) {
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
            // 마커를 클릭했을 때 뜨는 정보창을 클릭하면 그 레스토랑에 주문을 할 수 있는 Activity를 띄운다.
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
                // permission이 수락되었다면 현 위치를 맵에 띄운다.
                if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10.0f, this)
                }
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        // 위치가 변할 때마다 그 위치를 맵에 수정하여 띄운다.
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
