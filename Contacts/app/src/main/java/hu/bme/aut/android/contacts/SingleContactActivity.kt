package hu.bme.aut.android.contacts

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony.Sms
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.contacts.databinding.ActivitySingleContactBinding
import hu.bme.aut.android.contacts.model.Contact
import permissions.dispatcher.*

@RuntimePermissions
class SingleContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingleContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleContactBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.tvContactName.text = intent.getStringExtra(Contact.KEY_NAME)
            ?: resources.getString(R.string.contact_name_placeholder)
        binding.tvContactNumber.text = intent.getStringExtra(Contact.KEY_NUMBER)
            ?: resources.getString(R.string.contact_name_placeholder)
        binding.buttonCall.setOnClickListener {
            callPhoneNumberWithPermissionCheck(binding.tvContactNumber.text.toString())
        }
        binding.buttonSendSMS.setOnClickListener {
            sendMessageWithPermissionCheck(binding.tvContactNumber.text.toString())
        }
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    fun callPhoneNumber(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    @NeedsPermission(Manifest.permission.SEND_SMS)
    fun sendMessage(phoneNumber: String) {
        val text = binding.etSMS.text.toString()
        @Suppress("DEPRECATION") val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, text, null, null)
        Toast.makeText(this, "Sent text: " + text + " to: " + phoneNumber, Toast.LENGTH_SHORT).show()
    }

    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
    fun onCallDenied() {
        Toast.makeText(this, getString(R.string.permission_denied_call), Toast.LENGTH_SHORT).show()
    }

    @OnPermissionDenied(Manifest.permission.SEND_SMS)
    fun onSmsDenied() {
        Toast.makeText(this, getString(R.string.permission_denied_sms), Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(Manifest.permission.CALL_PHONE)
    fun showRationaleForCall(request: PermissionRequest) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(R.string.call_permission_explanation)
            .setCancelable(false)
            .setPositiveButton(R.string.proceed) { dialog, id -> request.proceed() }
            .setNegativeButton(R.string.exit) { dialog, id -> request.cancel() }
            .create()
        alertDialog.show()
    }

    @OnShowRationale(Manifest.permission.SEND_SMS)
    fun showRationaleForSms(request: PermissionRequest) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(R.string.sms_permission_explanation)
            .setCancelable(false)
            .setPositiveButton(R.string.proceed) { dialog, id -> request.proceed() }
            .setNegativeButton(R.string.exit) { dialog, id -> request.cancel() }
            .create()
        alertDialog.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
}