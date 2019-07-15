package lisa.owusu.tellmeaboutmycountry.utils

/**
 * Created by Lisa on 6/18/18.
 **/

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lisa.owusu.tellmeaboutmycountry.models.Country

/**
 * Local Database for the app
 */
class Cache private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val gson: Gson


    init {
        sharedPreferences = context.getSharedPreferences(Constants.CACHE, Context.MODE_PRIVATE)
        gson = Gson()
    }



    /**
     * Store countries in Local cache
     * @param countries countries to be stored
     */
    @Synchronized
    fun storeCountries(countries: List<Country>?) {
        if (countries != null) {
            val type = object : TypeToken<List<Country>>() {}.type
            sharedPreferences.edit().putString(Constants.COUNTRIES, gson.toJson(countries, type)).apply()
        }
    }


    /**
     * Access stored countries in cache
     */
    val getCountries: ArrayList<Country>
        get() {
            var countries = ArrayList<Country>()

            var type = object : TypeToken<ArrayList<Country>>() {}.type
            if (sharedPreferences.getString(Constants.COUNTRIES, "").isNotEmpty())
                countries = gson.fromJson(sharedPreferences.getString(Constants.COUNTRIES, ""), type)
            return countries
        }


    companion object {
        var cache: Cache? = null

        /**
         * Instance of Cache
         * @param context Activity Context
         */
        fun getInstance(context: Context): Cache {
            if (cache == null) {
                cache = Cache(context)
            }
            return cache as Cache
        }
    }
}