package tdao.example.finalexamminhtiendao

import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class MapsFragment : Fragment() , OnMapReadyCallback {

    //This will help to get the current Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val TAG = "MapsFragment"
    private lateinit var mMap: GoogleMap
    private lateinit var loc: LatLng

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

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        Log.i(TAG, "onMapReady")

        // this is where you can configure your google Maps controls that you want
        mMap.uiSettings.setMyLocationButtonEnabled(true)
        mMap.uiSettings.setZoomControlsEnabled(true)
        mMap.setTrafficEnabled(true)
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
//                    userAddress= getAddress(loc).toString()
                    mMap.addMarker(MarkerOptions().position(loc).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE))
//                        .title(getAddress(loc))
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
}