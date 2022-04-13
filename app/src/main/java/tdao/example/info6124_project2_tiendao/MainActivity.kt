package tdao.example.info6124_project2_tiendao

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    //For SMS
    private lateinit var textSmsNumber:TextView
    private lateinit var textSmsMessage:TextView
    private lateinit var textCurrentLocation:TextView


    //For UserData
    private var userData: UserData = UserData()


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
        Log.i(TAG,"onResume")

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
//                saveUserData()
                onMapFragment()
            }
            R.id.emailButton -> {
//                saveUserData()
                onEmailFragment()
            }
            R.id.smsButton -> {
//                saveUserData()
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
        //Get all the current fragments
        val currentFragment = supportFragmentManager.fragments
        Log.i(TAG,currentFragment.toString())
        for (fragment in currentFragment){
            try {
                supportFragmentManager.inTransaction { remove(fragment) }
            }
            catch(e:Exception){

            }
        }

    }

    fun onEmailClick(view: View) {

        textEmailAddress = findViewById(R.id.textEmailAddress)
        textEmailSubject = findViewById(R.id.textEmailSubject)
        textEmailMessage = findViewById(R.id.textEmailMessage)
        textCurrentLocation = findViewById(R.id.textCurrentLocation)
        val toAddress = arrayOf("${textEmailAddress.text}")
        composeEmail(toAddress, "${textEmailSubject.text}", "${textEmailMessage.text}", "${textCurrentLocation.text}")
    }

    private fun composeEmail(emailAddress: Array<String>, subject: String, body: String, address: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, emailAddress)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, "${address} \n Content: ${body}")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }



    private fun requestPermission(address: String, sms_body: String, currentAddress: String) {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(this,getString(R.string.per_grant),Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:")  // This ensures only SMS apps respond
                    putExtra("address", address)
                    putExtra("sms_body", "${sms_body} ${currentAddress}")
                }

                // chooser for sms app
                startActivity(Intent.createChooser(intent,"SMS"))
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)-> {
                // additional rationale is displayed
                // This case will occur when the user change the setting permission
                Toast.makeText(this,getString(R.string.per_not_grant),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            else-> {
                // permission has not been asked yet
                // This case will occur when the user first run application
                Toast.makeText(this,getString(R.string.per_not_ask),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result:Boolean ->
        // checking the result of permission
        if (result) {
            Toast.makeText(this,getString(R.string.per_grant), Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,getString(R.string.per_not_grant), Toast.LENGTH_SHORT).show()
        }
    }

    fun onSmsClick(view: View) {
        textSmsNumber = findViewById(R.id.textSmsNumber)
        textSmsMessage = findViewById(R.id.textSmsMessage)
        textCurrentLocation = findViewById(R.id.textCurrentLocation)

        requestPermission("${textSmsNumber.text}", "${textSmsMessage.text}" , "${textCurrentLocation.text}")
    }

}