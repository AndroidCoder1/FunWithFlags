package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import lisa.owusu.tellmeaboutmycountry.models.Country

interface HomeView {

    fun showProgress()
    fun hideProgress()
    fun hideKeyboard()
    fun showOopsContainer()
    fun hideOopsContainer()
    fun showInternetConnectivityError()
    fun hideInternetConnectivityError()
    fun resetScreen()
    fun changeErrorText(text: String)
    fun displayCountryDetails(country: Country?)
    fun expandBottomView()
    fun collapseBottomView()
    fun setSearchViewAdapter(data: List<String>)

}