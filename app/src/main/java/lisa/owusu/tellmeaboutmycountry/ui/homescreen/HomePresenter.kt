package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country

interface HomePresenter {

    fun onItemClicked(country: Country?)

    fun onNavigateToMapsClicked(country: Country?)

    fun searchForCountryBasedOnQuery(query: String, context: Context)
}