package com.tvjunkie.api

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

/**
 * Created by ludwig on 11/01/17.
 */

class TVJunkieAPIClient

val clientID = "0e7e55d561c7e688868a5ea7d2c82b17e59fde95fbc2437e809b1449850d4162"
val apiKey = "acac2f8984eff126f39d2aa53ce08366733fda1013cc6e5e09699dadbbab517f"

var url = "https://api.trakt.tv"

// initialize basic values on instace to connect to trakt.tv
fun init(){
    //if we set baseURL beforehand, simply use relativePath
    FuelManager.instance.basePath = url
    FuelManager.instance.baseHeaders = mapOf("Content-type" to "application/json",
            "trakt-api-key" to clientID, "trakt-api-version" to "2", "Authorization"  to apiKey)
}

fun getTrendingMovies(){
    "/movies/trending".httpGet().responseJson { request, response, result ->
        println(result)
        //do something with response
        when (result) {
            is Result.Failure -> {
                //TODO: display error toast
            }
            is Result.Success -> {
               //TODO: temp/offline storage somewhere & periodic checking
               result.value.content
            }
        }
    }
}