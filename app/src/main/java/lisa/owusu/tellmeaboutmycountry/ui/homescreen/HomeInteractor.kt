package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country


/**
 * Interface that handles the data being requested in the view
 */
interface HomeInteractor {

    /**
     * Interface to track network request progress
     */
    interface OnRequestFinishedListener {

        /**
         * Called when any error/exception apart from Network Error occurs whiles
         * the network request is made
         */
        fun onError()

        /**
         * Called when network request encounters Network Error
         * For instance, network disabled or internet off situations
         */
        fun onNetworkError()

        /**
         * Called when the network request made successfully returns the response
         * @param countries the list of countries with the query in their names
         */
        fun onRequestSuccess(countries: List<Country>?)

        /**
         * Called when a Null Exception occurs in the Network Request Progress
         */
        fun onNullResponse()

        /**
         * Called before the network request is initiated
         */
        fun onBeforeRequest()
    }

    /**
     * Network call to get list of countries based on a query string
     * @param name query containing country name
     * @param context current activity context
     * @param listener Listeners to track the network request progress
     */
    fun getCountryFromURLRequest(name: String, context: Context, listener: OnRequestFinishedListener)

    /**
     * Get a list of countries from the Local Cache based on a query string
     * @param name query containing country name
     * @param context current activity context
     * @param listener Listeners to track the request progress
     */
    fun getCountryFromLocalCache(name: String, context: Context, listener: OnRequestFinishedListener)

    /**
     * Network call to get list of all countries
     * @param context current activity context
     * @param listener Listeners to track the network request progress
     */
    fun makeAllCountriesRequest(context: Context, listener: OnRequestFinishedListener)

    /**
     * Saves list of countries in a local database
     * @param countries list of countries
     * @param context current activity context
     * @param listener Listeners to track the network request progress
     */
    fun saveCountries(countries: List<Country>, context: Context, listener: OnRequestFinishedListener)

}