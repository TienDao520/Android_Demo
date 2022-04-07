package tdao.example.myfirstmappapp

import android.Manifest
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import tdao.example.myfirstmappapp.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    //Step2: Setup Google API Client
    private var mGoogleApiClient: GoogleApiClient? = null
    //This will help to get the current Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var loc: LatLng

    // Fanshawe Oxford St. location
    // Fanshawe  43.012440, -81.200180
    // Location to place the bullseye
    var bullseye = LatLng(42.98, -81.23)

    // Approximate LatLng of Fanshawe Downtown
    var start = LatLng(42.982600, -81.250000)

    // Approximate LatLng of Budweiser Gardens
    var budGardens = LatLng(42.98237, -81.25255)
    private val TAG = "MyMaps"

    // Used for selecting the Current Place.
    private var mPlacesClient: PlacesClient? = null
    //5 closest places
    private val M_MAX_ENTRIES = 5
    private lateinit var mLikelyPlaceNames: Array<String>
    private lateinit var mLikelyPlaceAddresses: ArrayList<String>
    private lateinit var mLikelyPlaceLatLngs: ArrayList<LatLng>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // GooglePlaces: Step1: Initialize the Places client
        val apiKey = getString(R.string.maps_api_key)
        Places.initialize(applicationContext, apiKey)
        mPlacesClient = Places.createClient(this)
        recyclerView = findViewById(R.id.recyclerViewPlaces)
        mLikelyPlaceNames = arrayOf<String>("","","","","")
        mLikelyPlaceAddresses = ArrayList<String>(5)
        mLikelyPlaceLatLngs = ArrayList<LatLng>(5)

        // GooglePlaces: Step2: setting up the recyclerView
        viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerAdapter(mLikelyPlaceNames)  // pass in data to be displayed
        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPlaces).apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter  }

        // initialize location services query the place client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                Log.i(TAG, "Fine Location accessed")
                getCurrentLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Log.i(TAG, "Coarse Location accessed")
                getCurrentLocation()
            } else -> {
            Log.i(TAG, "No location permissions")
        }
        }
    }

    override fun onStart() {
        super.onStart()
        if(mGoogleApiClient != null) {
            mGoogleApiClient?.connect()
        }
    }

    override fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
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
        Log.i(TAG, "onMapReady")

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        // this is where you can configure your google Maps controls that you want
        mMap.uiSettings.setMyLocationButtonEnabled(true)
        mMap.uiSettings.setZoomControlsEnabled(true)
        mMap.setTrafficEnabled(true)
    }

    // gets the current location of the phone
    private fun getCurrentLocation() {
        Log.i(TAG, "Getting current location")
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { lastLocation: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (lastLocation != null) {
                    loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                    Log.i(TAG, loc.toString())
                    // Add a BLUE marker to current location and zoom
                    // use reverse geocoding to get the current address at your location.
                    mMap.addMarker(MarkerOptions().position(loc).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE))
                        .title(getAddress(loc)).snippet("Your location Lat:" + loc.latitude + ",Lng:" + loc.longitude))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
                    // animate camera allows zoom
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16f))  //zoom in at 16f


                    // adding two more markers just for fun
                    mMap.addMarker(MarkerOptions().position(budGardens).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_GREEN))
                        .title(getAddress(budGardens)).snippet("Budweiser Gardens"))
                    mMap.addMarker(MarkerOptions().position(start).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_VIOLET))
                        .title(getAddress(start)).snippet("Covent Garden Market"))
                    // how to add a image to the map overlay
                    val bullsEyeMap = GroundOverlayOptions()
                        .image(BitmapDescriptorFactory.fromResource(R.drawable.bullseye))
                        .position(bullseye,1600f, 1600f)
                    mMap.addGroundOverlay(bullsEyeMap)
                    // draw lines on the map
                    drawLines()
                }
            }
    }

    override fun onConnected(p0: Bundle?) {
        // when location services is ready to be called
        Log.i(TAG, "onConnected")
    }

    override fun onConnectionSuspended(p0: Int) {
        // when it suspends location services
        Log.i(TAG, "onConnectionSuspended")
    }

    /* uses reverse geocoding to determine an address from
    LatLong position.
    Use Geocoding to determine the LatLong position from address
    In this case we are giving the LatLong of Bud Gardens */
    private fun getAddress(loc:LatLng): String? {
        //Geocoder transfer from location to latlng value
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            //If you want to get more values you can change the maxResult value
            addresses = geocoder.getFromLocation(loc!!.latitude, loc!!.longitude, 1)
        } catch (e1: IOException) {
            Log.e(TAG, getString(R.string.service), e1)
        } catch (e2: IllegalArgumentException) {
            Log.e(TAG, getString(R.string.invalid_lat_long)+ ". " +
                    "Latitude = " + loc!!.latitude +
                    ", Longitude = " +
                    loc!!.longitude, e2)
        }
        // If the reverse geocode returned an address
        if (addresses != null) {
            // Get the first address
            val address = addresses[0]
            val addressText = String.format(
                "%s, %s, %s",
                address.getAddressLine(0), // If there's a street address, add it
                address.locality,                 // Locality is usually a city
                address.countryName)              // The country of the address
            return addressText
        }
        else
        {
            Log.e(TAG, getString(R.string.no_address_found))
            return ""
        }
    }

    // Draw a line on the Google Map
    private  fun drawLines() {
        mMap.addPolyline(
            PolylineOptions()
                .add(start,
                    budGardens,
                    LatLng(42.9829, -81.2529),
                    start)
                .width(5.0F)
                .color(Color.BLUE)
                .geodesic(true))
    }
}