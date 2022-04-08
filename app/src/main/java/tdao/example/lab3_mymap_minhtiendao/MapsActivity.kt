package tdao.example.lab3_mymap_minhtiendao

import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import tdao.example.lab3_mymap_minhtiendao.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val TAG = "MyMaps"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
}