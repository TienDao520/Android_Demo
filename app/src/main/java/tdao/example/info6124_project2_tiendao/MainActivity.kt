package tdao.example.info6124_project2_tiendao

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import java.lang.Exception

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks {

    //Setup Google API Client
    private var mGoogleApiClient: GoogleApiClient? = null
    private val TAG = "MainActivity"

    //For Email
    private lateinit var textEmailAddress:TextView
    private lateinit var textEmailSubject:TextView
    private lateinit var textEmailMessage:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        binding = ActivityMapsBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)


    }

    override fun onResume() {
        super.onResume()
        // Start Lifecycle of a fragment

    }



    override fun onStart() {
        Log.i(TAG, "onStart ${mGoogleApiClient.toString()}")
        super.onStart()
        if(mGoogleApiClient != null) {
            mGoogleApiClient?.connect()
        }
    }

    override fun onStop() {
        Log.i(TAG, "onStop")
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnected(p0: Bundle?) {
        // when location services is ready to be called
        Log.i(TAG, "onConnected")
    }

    override fun onConnectionSuspended(p0: Int) {
        // when it suspends location services
        Log.i(TAG, "onConnectionSuspended")
    }

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.mapButton -> {
                onMapFragment()
            }
            R.id.emailButton -> {
                onEmailFragment()
            }
            R.id.smsButton -> {
                onSmsFragment()
            }
        }
    }

    private fun onSmsFragment() {
        try {
            val fragment: SmsFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as SmsFragment
        } catch (e:Exception) {
            deleteCurrentFragment()
            val fragment = SmsFragment()
            supportFragmentManager.inTransaction { add(R.id.fragmentContainerView, fragment)}
        }
    }

    private fun onEmailFragment() {
        try {
            val fragment: EmailFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as EmailFragment
        } catch (e:Exception) {
            deleteCurrentFragment()
            val fragment = EmailFragment()
            supportFragmentManager.inTransaction { add(R.id.fragmentContainerView, fragment)}
        }
    }

    private fun onMapFragment() {
        try {
            val fragment: MapsFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as MapsFragment
        } catch (e:Exception) {
            deleteCurrentFragment()
            val fragment = MapsFragment()
            //            val fragmentTransaction = supportFragmentManager.beginTransaction()
//            fragmentTransaction.add(R.id.fragmentContainerView, fragment)
//            fragmentTransaction.commit()
            supportFragmentManager.inTransaction { add(R.id.fragmentContainerView, fragment)}
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }

    private fun deleteCurrentFragment() {
        val currentFragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainerView)
        currentFragmentContainer.removeAllViews()
    }

    fun onEmailClick(view: View) {

        textEmailAddress = findViewById(R.id.textEmailAddress)
        textEmailSubject = findViewById(R.id.textEmailSubject)
        textEmailMessage = findViewById(R.id.textEmailMessage)
        val toAddress = arrayOf("${textEmailAddress.text}")
        composeEmail(toAddress, "${textEmailSubject.text}", "${textEmailMessage.text}")
    }

    private fun composeEmail(addresses: Array<String>, subject: String, body: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

}