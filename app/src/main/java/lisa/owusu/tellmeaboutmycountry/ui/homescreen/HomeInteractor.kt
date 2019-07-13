package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country

interface HomeInteractor {

    interface OnRequestFinishedListener {
        fun onError()
        fun onRequestSuccess(countries: List<Country>?)
        fun onNullResponse()
        fun onBeforeRequest()
    }

    fun getCountryFromURLRequest(name: String, listener: OnRequestFinishedListener)
    fun getCountryFromLocalCache(name: String, listener: OnRequestFinishedListener)
    fun makeAllCountriesRequest(listener: OnRequestFinishedListener)
    fun saveCountries(countries: List<Country>, listener: OnRequestFinishedListener)
    fun checkForInternetConnectivity(context: Context) : Boolean
}