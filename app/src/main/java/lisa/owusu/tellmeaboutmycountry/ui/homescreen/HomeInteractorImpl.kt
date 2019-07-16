package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.content.Context
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Cache
import lisa.owusu.tellmeaboutmycountry.utils.InternetCheck
import lisa.owusu.tellmeaboutmycountry.utils.Requests
import lisa.owusu.tellmeaboutmycountry.utils.RetrofitClientInstance
import lisa.owusu.tellmeaboutmycountry.utils.Utils.Companion.checkForInternetConnectivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeInteractorImpl : HomeInteractor {

    private var call: Call<List<Country>>? = null

    override fun getCountryFromURLRequest(
        name: String,
        context: Context,
        listener: HomeInteractor.OnRequestFinishedListener
    ) {

        listener.onBeforeRequest()

        val cache = Cache.getInstance(context)

        /**
         * check to see if cache has the complete list of countries.
         * If it has, we look for the country from that list instead
         */
        if (cache.getCountries.isNotEmpty()) {
            getCountryFromLocalCache(name, context, listener)
            return
        }

        /**
         * Since there is no data in cache we have to check for internet availability
         * by checking if network hardware is turned on and connected
         * before getting the list of countries from the internet.
         * If internet is not available display network error
         */
        if (!checkForInternetConnectivity(context)) {
            listener.onNetworkError()
            return
        }


        /**
         * This Method checks for cases where the network hardware is
         * connected but has no internet. It makes sure there is an active internet
         * connection before countries list request is made
         */
        try {
            InternetCheck(object : InternetCheck.Consumer {

                override fun accept(internet: Boolean?) {
                    if (internet == false) {
                        listener.onNetworkError()
                        return
                    }

                    val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

                    call = service?.getCountry(name, Country.getMemberNames())

                    if (call != null && call!!.isExecuted) {

                        call?.cancel()

                    } else {

                        println(call?.request()?.url())

                        call?.enqueue(object : Callback<List<Country>> {

                            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                                listener.onError()
                            }

                            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                                println(response.body())
                                if (response.body() == null) {
                                    listener.onNullResponse()
                                    return
                                }
                                listener.onRequestSuccess(response.body())
                            }
                        })
                    }
                }
            })
        } catch (e: Exception) {
            listener.onError()
        }
    }

    override fun getCountryFromLocalCache(
        name: String,
        context: Context,
        listener: HomeInteractor.OnRequestFinishedListener
    ) {
        var countries = ArrayList<Country>()
        val cache = Cache.getInstance(context)
        if (cache.getCountries == null) {
            listener.onNullResponse()
            return
        }
        for (country in cache.getCountries) {
            if (country.name?.contains(name, true)!!) {
                countries.add(country)
            }
        }
        listener.onRequestSuccess(countries)
    }

    override fun makeAllCountriesRequest(context: Context, listener: HomeInteractor.OnRequestFinishedListener) {
        listener.onBeforeRequest()

        if (!checkForInternetConnectivity(context)) {
            listener.onNetworkError()
            return
        }



        try {
            InternetCheck(object : InternetCheck.Consumer {

                override fun accept(internet: Boolean?) {
                    if (internet == false) {
                        listener.onNetworkError()
                        return
                    }

                    val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

                    val call = service?.getCountries(Country.getMemberNames())

                    if (call == null) {
                        listener.onNullResponse()
                    }
                    println(call?.request()?.url())

                    call?.enqueue(object : Callback<List<Country>> {
                        override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                            listener.onError()
                        }

                        override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                            println(response.body())
                            listener.onRequestSuccess(response.body())
                        }
                    })

                }
            })
        } catch (e: Exception) {
            listener.onError()
        }
    }

    override fun saveCountries(
        countries: List<Country>,
        context: Context,
        listener: HomeInteractor.OnRequestFinishedListener
    ) {
        val cache = Cache.getInstance(context)
        cache.storeCountries(countries)
    }



}