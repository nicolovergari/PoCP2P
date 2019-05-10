package com.company.carlo.p2pcarsharing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.amazonaws.mobileconnectors.cognitoidentityprovider.*
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler
import com.amazonaws.regions.Regions
import com.company.carlo.p2pcarsharing.R.id.edit_user

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.lang.Exception

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity(){

    //val userPool= CognitoUserPool(this, "us-east-1_jEqXt1Ptc",  "41adn49va2gr6madcqsak9s5tl", "1cqrl25k3gdoa9q7bb7f8d456tjljbf77el0sgq7005hm246ufha", Regions.US_EAST_1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        val authenticationHandle= object : AuthenticationHandler{ //Gestore di callback per il processo di autenticazione dell'utente.
            override fun onFailure(exception: Exception?) {
                println("login fallito")
            }

            override fun onSuccess(userSession: CognitoUserSession?, newDevice: CognitoDevice?) {
                println("login ok")
                val intent = Intent(this@LoginActivity, HomeActivity()::class.java)
                intent.putExtra("logged", true)
                startActivity(intent)

            }

            override fun getAuthenticationDetails( //Chiama il dev per ottenere le credenziali per un utente.
                authenticationContinuation: AuthenticationContinuation?,
                userId: String?
            ) {

                var authenticationdetails:AuthenticationDetails= AuthenticationDetails(username_edit.text.toString(),password_edit.text.toString(),null)
                authenticationContinuation!!.setAuthenticationDetails(authenticationdetails)
                authenticationContinuation!!.continueTask()
            }

            override fun authenticationChallenge(continuation: ChallengeContinuation?) { //Challenge generica

                continuation!!.continueTask()

            }

            override fun getMFACode(continuation: MultiFactorAuthenticationContinuation?) { //MultifactorAuthentication

            }
        }

        sign_in_button.setOnClickListener(){

            val cognitosettings:CognitoSettings= CognitoSettings(this)  //Classe contente credenziali UserPool
            val thisUser:CognitoUser=cognitosettings.getUserPool().getUser(username_edit.text.toString())
            thisUser.getSessionInBackground(authenticationHandle)

        }


    }















}
