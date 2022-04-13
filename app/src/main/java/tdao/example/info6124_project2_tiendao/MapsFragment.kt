package tdao.example.info6124_project2_tiendao

import android.Manifest
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {
    //This will help to get the current Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val TAG = "MapsFragment"
    private lateinit var mMap: GoogleMap
    private lateinit var loc: LatLng
    private lateinit var userAddress: String
//    private lateinit var binding: ActivityMapsBinding




//    private val callback = OnMapReadyCallback { googleMap ->
//
//        Log.i(TAG, "onMapReady")
//        /**
//         * Manipulates the map once available.
//         * This callback is triggered when the map is ready to be used.
//         * This is where we can add markers or lines, add listeners or move the camera.
//         * In this case, we just add a marker near Sydney, Australia.
//         * If Google Play services is not installed on the device, the user will be prompted to
//         * install it inside the SupportMapFragment. This method will only be triggered once the
//         * user has installed Google Play services and returned to the app.
//         */
//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)


        // initialize location services query the place client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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

    // gets the current location of the phone
    private fun getCurrentLocation() {
        Log.i(TAG, "Getting current location")
        fusedLocationClient.lastLocation
            .addOnSuccessListener(requireActivity()) { lastLocation: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (lastLocation != null) {
                    loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                    Log.i(TAG, loc.toString())
                    // Add a BLUE marker to current location and zoom
                    // use reverse geocoding to get the current address at your location.
                    userAddress= getAddress(loc).toString()
                    mMap.addMarker(MarkerOptions().position(loc).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE))
                        .title(getAddress(loc))
                        .snippet("Your location Lat:" + loc.latitude + ",Lng:" + loc.longitude)
                    )

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
                    // animate camera allows zoom
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16f))  //zoom in at 16f

                }
            }
    }
    /* uses reverse geocoding to determine an address from
    LatLong position.
    Use Geocoding to determine the LatLong position from address
    In this case we are giving the LatLong of Bud Gardens */
    private fun getAddress(loc:LatLng): String? {
        //Geocoder transfer from location to latlng value
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
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


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        Log.i(TAG, "onMapReady")

        // this is where you can configure your google Maps controls that you want
        mMap.uiSettings.setMyLocationButtonEnabled(true)
        mMap.uiSettings.setZoomControlsEnabled(true)
        mMap.setTrafficEnabled(true)
    }

    override fun onPause() {
        val prefsEditor = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)?.edit()
        prefsEditor?.putString("address", userAddress)
        prefsEditor?.apply()
        Log.i(TAG,userAddress)

        super.onPause()
    }

}