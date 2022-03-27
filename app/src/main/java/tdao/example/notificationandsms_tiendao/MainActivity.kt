package tdao.example.notificationandsms_tiendao

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onButtonClick(view: View) {
        when(view.id) {
            R.id.buttonSMS->{
                requestPermission()
            }
            R.id.buttonDirectSMS->{
                requestPermissionDirect()
            }
        }
    }

    // Check the permission status
    private fun requestPermissionDirect() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(this,getString(R.string.per_grant),Toast.LENGTH_SHORT).show()
                var smsManager:SmsManager
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
                    // Do something for S (12) and above versions
                    smsManager = baseContext.getSystemService(SmsManager::class.java)
                } else{
                    // do something for phones running an SDK before S (12)
                    smsManager = SmsManager.getDefault()
                }
                Toast.makeText(this, android.os.Build.VERSION.SDK_INT.toString(),Toast.LENGTH_LONG).show()

                smsManager.sendTextMessage("5554", "Test", "Direct SMS Message", null, null)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)-> {
                // additional rationale is displayed
                Toast.makeText(this,getString(R.string.per_not_grant),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
            else-> {
                // permission has not been asked yet
                Toast.makeText(this,getString(R.string.per_not_ask),Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(Manifest.permission.SEND_SMS)
            }
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED -> {
                // permission is granted
                Toast.makeText(this,getString(R.string.per_grant),Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:")  // This ensures only SMS apps respond
                    putExtra("address", "5554")
                    putExtra("sms_body", getString(R.string.sms))
                }
                // use default sms
//               startActivity(intent)
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


}