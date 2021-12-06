package com.example.myfastcampusstudy_android.intermediate.abnb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfastcampusstudy_android.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons

class AbnbMainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abnb_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        // set zoom level
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // set Camera Position
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.497885, 127.027512))
        naverMap.moveCamera(cameraUpdate)

        // current position    --> 사용자 동의가 필요함
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = true

        locationSource =
            FusedLocationSource(this@AbnbMainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource

        // create marker
        val marker = Marker()
        marker.position = LatLng(37.500493, 127.029740)
        marker.map = naverMap
        marker.icon = MarkerIcons.BLACK

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (!requestCode.equals(LOCATION_PERMISSION_REQUEST_CODE))
            return

        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun initViews() {

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}