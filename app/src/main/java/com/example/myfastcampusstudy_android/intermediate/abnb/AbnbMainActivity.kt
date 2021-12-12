package com.example.myfastcampusstudy_android.intermediate.abnb

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myfastcampusstudy_android.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AbnbMainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private val mapView: MapView by lazy {
        findViewById(R.id.mapView)
    }
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val btnCurrentLocationButton: LocationButtonView by lazy {
        findViewById(R.id.btnCurrentLocation)
    }

    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.houseViewPager)
    }

    private val tvBottomSheetTitle: TextView by lazy {
        findViewById(R.id.tvBottomSheetTitle)
    }


    private val viewPagerAdapter = HouseViewPagerAdapter(itemClicked = {
        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "[지금 이 가격에 예약하세요!!] ${it.title} ${it.price} 사진보기: {${it.imgUrl}"
                )
                type = "text/plain"
            }
        startActivity(Intent.createChooser(intent, null))
    })
    private val recyclerViewAdapter = HouseListrAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abnb_main)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        viewPager.adapter = viewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedHouseModel = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selectedHouseModel.lat, selectedHouseModel.lng))
                        .animate(CameraAnimation.Easing)
                naverMap.moveCamera(cameraUpdate)
            }
        })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map

        // set zoom level
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        // set Camera Position
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.469646, 126.819207))
        naverMap.moveCamera(cameraUpdate)

        // current position    --> 사용자 동의가 필요함
        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false // false -> 원래 있던 버튼이 보이지 않음
        btnCurrentLocationButton.map = naverMap

        locationSource =
            FusedLocationSource(this@AbnbMainActivity, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource


        getHouseListFromAPI()

    }

    private fun getHouseListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HouseService::class.java).also {
            it.getHouseList()
                .enqueue(object : Callback<HouseDto> {
                    override fun onResponse(call: Call<HouseDto>, response: Response<HouseDto>) {
                        if (response.isSuccessful.not()) {
                            // TODO 실패 처리에 대한 구현
                            return
                        }

                        response.body()?.let { dto ->
                            updateMarker(dto.items)
                            viewPagerAdapter.submitList(dto.items)
                            recyclerViewAdapter.submitList(dto.items)

                            tvBottomSheetTitle.text = "${dto.items.size}개의 숙소"
                        }
                    }

                    override fun onFailure(call: Call<HouseDto>, t: Throwable) {
                        // TODO 실패 처리에 대한 구현
                    }
                })
        }
    }

    private fun updateMarker(houses: List<HouseModel>) {
        houses.forEach { house ->
            val marker = Marker()
            marker.position = LatLng(house.lat, house.lng)
            marker.onClickListener = this
            marker.map = naverMap
            marker.tag = house.id
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
        }
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

    // overlay: 마커들의 총집합
    override fun onClick(overlay: Overlay): Boolean {
        val selectedModel = viewPagerAdapter.currentList.firstOrNull() {
            it.id == overlay.tag
        }

        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }

        return true
    }
}