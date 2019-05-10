package com.company.carlo.p2pcarsharing

import android.content.Context
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.regions.Regions

class CognitoSettings(val context: Context) {
    val userPoolId:String="us-east-1_jEqXt1Ptc"
    val clientId:String="41adn49va2gr6madcqsak9s5tl"
    val clientSecret:String="1cqrl25k3gdoa9q7bb7f8d456tjljbf77el0sgq7005hm246ufha"
    val cognitoregion=Regions.US_EAST_1

    public fun getUserPool():CognitoUserPool{
        return CognitoUserPool(context,userPoolId,clientId,clientSecret,cognitoregion)
    }


}