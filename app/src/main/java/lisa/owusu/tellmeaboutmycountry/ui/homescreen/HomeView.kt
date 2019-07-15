package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.app.Activity
import lisa.owusu.tellmeaboutmycountry.models.Country

interface HomeView {

    fun showProgress()
    fun hideProgress()
    fun hideKeyboard(activity: Activity)
    fun showOopsContainer()
    fun hideOopsContainer()
    fun showInternetConnectivityError()
    fun hideInternetConnectivityError()
    fun resetScreen()
    fun changeErrorText(text: String)
    fun displayCountryDetails(country: Country?)
    fun expandBottomView()
    fun collapseBottomView()
    fun showToast(message: String)
    fun setSearchViewAdapter(data: List<Country>?)
    fun navigateToMaps(latLngString: String)

}