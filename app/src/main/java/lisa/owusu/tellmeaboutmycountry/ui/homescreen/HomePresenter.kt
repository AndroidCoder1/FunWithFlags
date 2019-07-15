package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country

/**
 * Presenter interface to serve as a middle person for the Model and View Interfaces
 */

interface HomePresenter {

    /**
     * Called when the AutoComplete drop down item is clicked
     * @param country country item clicked in drop down list
     */
    fun onItemClicked(country: Country?)

    /**
     * Called when the View on Map is clicked
     * @param country country to view on map
     */
    fun onNavigateToMapsClicked(country: Country?)

    /**
     * Called when a query is typed or submitted in the SearchAutoComplete widget
     * @param query Name of country being searched for
     * @param context Context of the activity
     */
    fun searchForCountryBasedOnQuery(query: String, context: Context)
}