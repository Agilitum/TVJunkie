package com.tvjunkie.api

import android.content.Context
import android.content.SharedPreferences
import com.github.kittinunf.fuel.core.FuelManager

/**
 * Created by ludwig on 11/01/17.
 */

class TVJunkieAPIClient (context : Context)

val clientID = "0e7e55d561c7e688868a5ea7d2c82b17e59fde95fbc2437e809b1449850d4162"
val apiKey = "acac2f8984eff126f39d2aa53ce08366733fda1013cc6e5e09699dadbbab517f"

var url = "https://api.trakt.tv"

val PREFS_FILENAME = "com.tvjunkie.sharedPreferences"
var tvJunkieSharedPreferences: SharedPreferences? = null

// initialize basic values on instace to connect to trakt.tv
fun initFuel(){
    //if we set baseURL beforehand, simply use relativePath
    FuelManager.instance.basePath = url
    FuelManager.instance.baseHeaders = mapOf("Content-type" to "application/json",
            "trakt-api-key" to clientID, "trakt-api-version" to "2")
}

//fun getTrendingMovies(){
//    "/movies/trending".httpGet().responseJson { request, response, result ->
//
//        //do something with response
//        when (result) {
//            is Result.Failure -> {
//                //TODO: display error toast
//            }
//            is Result.Success -> {
//                tvJunkieSharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
//                val editor = tvJunkieSharedPreferences!!.edit()
//                editor.putString("KEY_MOVIES_TRENDING", String(response.data))
//                editor.apply()
//            }
//        }
//    }
//}