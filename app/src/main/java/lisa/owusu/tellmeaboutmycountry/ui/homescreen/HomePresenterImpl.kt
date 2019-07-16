package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country


class HomePresenterImpl(homeView: HomeView, homeInteractor: HomeInteractor) : HomePresenter {


    private var homeView: HomeView? = homeView
    private val homeInteractor: HomeInteractor? = homeInteractor


    override fun onItemClicked(country: Country?) {
        homeView?.displayCountryDetails(country)
    }

    override fun searchForCountryBasedOnQuery(query: String, context: Context) {

        homeInteractor?.getCountryFromURLRequest(query, context, object : HomeInteractor.OnRequestFinishedListener {
            override fun onNetworkError() {
                homeView?.changeErrorText("An error occurred...\nCheck internet and try again")
                homeView?.showOopsContainer()
            }

            override fun onError() {
                homeView?.changeErrorText("An error occurred...\nPlease try again")
                homeView?.showOopsContainer()
            }

            override fun onRequestSuccess(countries: List<Country>?) {
                homeView?.hideProgress()
                if(countries != null)
                    homeView?.setSearchViewAdapter(countries)
                else
                    homeView?.setSearchViewAdapter(ArrayList())
            }

            override fun onNullResponse() {
                homeView?.changeErrorText("An error has occurred...\nPlease try again")
                homeView?.showOopsContainer()
            }

            override fun onBeforeRequest() {
                homeView?.showProgress()
                homeView?.hideOopsContainer()
                homeView?.hideInternetConnectivityError()
            }

        })
    }

    override fun onNavigateToMapsClicked(country: Country?) {

        if(country?.latlng != null && country.latlng.isNotEmpty() && country.latlng.size == 2) {
            var geoString = StringBuilder()
            geoString.append("geo:")
            var appendFlag = true

            for (latOrLng in country.latlng) {
                geoString.append(latOrLng)

                if(appendFlag) {
                    geoString.append(", ")
                    appendFlag = false
                }
            }
            geoString.append("?z=7")
            homeView?.navigateToMaps(geoString.toString())
        }
    }
}