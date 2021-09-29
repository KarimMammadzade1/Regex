package regexteam.example.regex.Screens


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

import regexteam.example.regex.Utils.Utils.cordSumqayit
import regexteam.example.regex.Utils.Utils.rideInfoList
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.PolyUtil
import kotlinx.android.synthetic.main.fragment_maps_screen.*
import regexteam.example.regex.BuildConfig.MAPS_API_KEY
import regexteam.example.regex.MyViewModel
import regexteam.example.regex.R

class MapsScreen() : Fragment(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener {
    private lateinit var  mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var mViewModel: MyViewModel
    private lateinit var userLocation: LatLng
    private var userDestination: LatLng? = null
    private lateinit var myPolyline: Polyline
    private var polyCounter = 0
    private lateinit var polyBound:LatLngBounds
    private var dMarker:Marker?=null
    private lateinit var itemList: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(this)[MyViewModel::class.java]

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView=view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        try { MapsInitializer.initialize(requireActivity().application) } catch (e:Exception){ e.printStackTrace() }
        mapView.getMapAsync(this)
       /* val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        */
        locateUserButton.setOnClickListener {
            mViewModel.getUserLocation(requireContext())
            mViewModel.userLocation.observe(viewLifecycleOwner, {
                userLocation = it
                showUser()   }) }
        locateUserButton.performClick()
        goHomeScreen.setOnClickListener {

            val action = R.id.action_mapsScreen_to_homeScreen
            findNavController().navigate(action)
        }

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(),MAPS_API_KEY)
        }
        val autocompletefragment = childFragmentManager.findFragmentById(R.id.autocompletefragment) as AutocompleteSupportFragment
        autocompletefragment.setActivityMode(AutocompleteActivityMode.OVERLAY)
        autocompletefragment.setPlaceFields(listOf(Place.Field.LAT_LNG))
        autocompletefragment.setCountry("AZ")
        autocompletefragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                userDestination = place.latLng
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userDestination!!, 14F))
                if (dMarker!=null) dMarker!!.remove()
               dMarker=mMap.addMarker(MarkerOptions().title("Destination").draggable(true).position(userDestination!!).icon(
                   regexteam.example.regex.Utils.CustomMarker()
                       .bitmapDescriptorFromVector(requireContext(),R.drawable.greenmarker48)))
                destinationSelected(userDestination!!)
            }
            override fun onError(p0: Status) {
                Log.d("PLACE", p0.toString())
            } })
        rideinfolayout.visibility=View.GONE
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(),R.raw.mapstyle))
        mMap.setMinZoomPreference(10.0f)
        mMap.uiSettings.isZoomControlsEnabled = true
        val options = GoogleMapOptions()
        options.rotateGesturesEnabled(true).tiltGesturesEnabled(true)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cordSumqayit))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16F))
        mMap.setOnMapLongClickListener(this)
        mMap.setOnMarkerDragListener(this)

    }

    private fun showUser() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14F)) //define zoom
        mMap.addMarker(
            MarkerOptions()
                //.draggable(true)
                .position(userLocation)
                .icon(
                    regexteam.example.regex.Utils.CustomMarker()
                        .bitmapDescriptorFromVector(requireContext(),R.drawable.oceanbluemarker48))//BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)
                .title("You Are Here"))
        mViewModel.getWeatherInfo(userLocation.latitude.toString(), userLocation.longitude.toString())
        mViewModel.weatherInfo.observe(viewLifecycleOwner, { regexteam.example.regex.Utils.Utils.myWeatherList = it })
    }

    private fun destinationSelected(userDest:LatLng){
        mViewModel.getPolyDirections(userLocation,userDest)
        mViewModel.polyDirectionsInfo.observe(viewLifecycleOwner, {
            val polyline = it[0]
            val distance = it[1]
            val duration = it[2]
            val startAdres = it[3]
            val endAdres = it[4]
            val boundNourth = it[5]
            val boundSouth = it[6]
            showInfos(polyline, distance,duration,startAdres,endAdres,boundNourth,boundSouth)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showInfos(polyline: String, distance: String, duration: String, startAdres: String, endAdres: String, boundNourth: String, boundSouth: String) {
        val points = PolyUtil.decode(polyline)
        if (polyCounter==1){myPolyline.remove()
            myPolyline = mMap.addPolyline(PolylineOptions().addAll(points).geodesic(true).width(12F).color(Color.GREEN))
            polyCounter = 1
        }else if (polyCounter==0){myPolyline = mMap.addPolyline(PolylineOptions().addAll(points).geodesic(true).width(12F).color(Color.GREEN))
            polyCounter=1 }
         polyBound=LatLngBounds.builder().include(userLocation).include(userDestination!!).build()
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(polyBound,50))
        animateRideInfoLayout()
        distanceText.text="Distance: "+distance
        durationText.text="Duration: "+duration
        fromText.text="From: "+startAdres
        toText.text="To: "+endAdres
        proceedButton.visibility=View.VISIBLE
        itemList= listOf(distance,duration,startAdres,endAdres)
        proceedButton.setOnClickListener { proceed() }

    }

    private fun proceed() {
        rideInfoList=itemList
        val action=R.id.action_mapsScreen_to_homeScreen
        findNavController().navigate(action)
    }

    override fun onMapLongClick(longPoint: LatLng) {
        userDestination=longPoint
        if (dMarker!=null) dMarker!!.remove()
        dMarker=mMap.addMarker(MarkerOptions().position(longPoint).draggable(true).icon(
            regexteam.example.regex.Utils.CustomMarker()
                .bitmapDescriptorFromVector(requireContext(),R.drawable.greenmarker48)))
        destinationSelected(longPoint)

    }

    override fun onMarkerDragStart(p0: Marker) {

    }

    override fun onMarkerDrag(p0: Marker) {

    }

    override fun onMarkerDragEnd(p0: Marker) {
       userDestination=p0.position
        destinationSelected(p0.position)
    }
    private fun animateRideInfoLayout() {
        rideinfolayout.visibility=View.VISIBLE
        YoYo.with(Techniques.SlideInUp).duration(1000).repeat(0).playOn(rideinfolayout)
    }


}

