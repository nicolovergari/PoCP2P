package com.company.carlo.restcalls
import com.company.carlo.assets.Car
import java.net.URL
import java.util.Date
import java.text.SimpleDateFormat
import org.json.*
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.io.InputStreamReader

class RestCalls {
    companion object{
        fun executeget(resourceUrl:String):JSONArray{
            val url=URL(resourceUrl)
            val connection=url.openConnection() as HttpURLConnection
            connection.requestMethod="GET"

            if(connection.responseCode!=200){
                throw IOException("Failed Connection")
            }

            val readbuffer = BufferedReader(InputStreamReader(connection.inputStream))
            val response:String=readbuffer.readLines().joinToString(separator = "")
            return JSONObject(response).getJSONArray("data")
        }
        /*private fun readAll(buff:BufferedReader): String {
            var out:String
            while ((out=buff.readLine))
        }*/
        fun getActiveByName(name:String,x:Double,y:Double):ArrayList<Car>{
            val jarray = executeget(String.format("http://159.65.194.154:5000/cars/activebyuser/%s/%f/%f",name,x,y))
            var out =arrayListOf<Car>()
            for (i in 0..jarray.length()){
                val obj=jarray[i] as JSONObject
                val coo=obj.getJSONObject("posizione").getJSONArray("coordinates")
                val form=SimpleDateFormat("yyyy-MM-dd")
                val dataIm=form.format(Date(obj.getJSONObject("dataIm").getLong("\$data")))
                val car=Car(obj.getString("produttore"),obj.getString("modello"),obj.getString("targa"),String.format("%f-%f",coo[0] as String,coo[1] as String),dataIm)
                out.add(car)
            }
            return out
        }
        /*fun getActiveByDate(date:Date,x:Double,y:Double):JSONArray{
            val form=SimpleDateFormat("yyyy-MM-dd")
            return executeget("http://159.65.194.154:5000/cars/activebydate/"+form.format(date)+
                    String.format("/%f/%f",x,y))
        }*/
    }
}