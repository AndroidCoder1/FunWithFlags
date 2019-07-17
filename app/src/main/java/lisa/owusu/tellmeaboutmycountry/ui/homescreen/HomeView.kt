package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.app.Activity
import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country


/**
 * Interface to show interactions to user
 */
interface HomeView {

    /**
     * Called to show a loading widget on the screen
     */
    fun showProgress()

    /**
     * Called to hide a loading widget on the screen
     */
    fun hideProgress()

    /**
     * Hide keyboard from screen
     * @param activity activity reference to hide keyboard
     */
    fun hideKeyboard(activity: Activity)

    /**
     * Called to show an error information on screen
     */
    fun showOopsContainer()

    /**
     * Called to hide an error information on screen
     */
    fun hideOopsContainer()

    /**
     * Called to show network error information on screen
     */
    fun showInternetConnectivityError()

    /**
     * Called to hide network error information on screen
     */
    fun hideInternetConnectivityError()

    /**
     * Called to clear previously inputted details
     */
    fun resetScreen()

    /**
     * Called to change error message before displaying
     * @param text Error message
     */
    fun changeErrorText(text: String)

    /**
     * Called to display country details
     * @param country Country details to be displayed
     */
    fun displayCountryDetails(country: Country?)

    /**
     * Called to show toast message on screen
     * @param message Toast message
     */
    fun showToast(message: String)

    /**
     * Set SearchViewAutoComplete adapter
     * @param data Adapter data
     */
    fun setSearchViewAdapter(data: List<Country>?)

    /**
     * Calls Map Activity with latitude and longitude details
     *@param latLngString latitude and longitude details prepended by geo:
     * and appended ny ?z =5; z is for zoom
     */
    fun navigateToMaps(latLngString: String)


    /**
     * Start Service to get all countries to save to cache
     * @param context context of activity
     */
    fun startGetAllCountriesService(context: Context)
}