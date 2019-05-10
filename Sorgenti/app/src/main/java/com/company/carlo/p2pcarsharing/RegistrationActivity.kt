package com.company.carlo.p2pcarsharing

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.regions.Regions
import kotlinx.android.synthetic.main.activity_registration.*
import java.lang.Exception
import java.util.*
import android.app.AlertDialog;
import android.content.DialogInterface;

class RegistrationActivity : AppCompatActivity() {

    private val insertAuto: InsertAuto= InsertAuto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        Datebutton.setOnClickListener {
            showcalendar()
        }

        Carbutton.setOnClickListener {
            OpenInsertAuto()
    }







        val signupCallback = object: SignUpHandler {
            override fun onSuccess(
                user: CognitoUser?,
                signUpConfirmationState: Boolean,
                cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?
            ) {
                println("Registrazione avvenuta con successo")
            }

            override fun onFailure(exception: Exception?) {
                println("Registrazione fallita")
            }
        }

        signupobutton.setOnClickListener(){
            val userAttributes=CognitoUserAttributes()
            userAttributes.addAttribute("email", edit_mail.text.toString())
            userAttributes.addAttribute("phone_number",editphone.text.toString())
            val cognitosettings:CognitoSettings= CognitoSettings(this)
            cognitosettings.getUserPool().signUpInBackground(edit_user.text.toString(),editpsw.text.toString(),userAttributes,null,signupCallback)
        }
    }





    private fun OpenInsertAuto () {
        val intent = Intent(this, InsertAuto::class.java)
        val message = "eheh"

        intent.putExtra(EXTRA_MESSAGE, message)
        startActivity(intent)
    }

    private fun showcalendar(){
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)


        val datepickerdialog:DatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            textView9.setText("""$dayOfMonth - ${monthOfYear + 1} - $year""")
        }, y, m, d)

        datepickerdialog.show()
    }


}
