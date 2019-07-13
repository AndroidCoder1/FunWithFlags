package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.animation.Animator
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Cache
import lisa.owusu.tellmeaboutmycountry.utils.Requests
import lisa.owusu.tellmeaboutmycountry.utils.RetrofitClientInstance
import lisa.owusu.tellmeaboutmycountry.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeInteractorImpl : HomeInteractor {

    override fun getCountryFromURLRequest(name: String, listener: HomeInteractor.OnRequestFinishedListener) {

        listener.onBeforeRequest()

        val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

        val call = service?.getCountry(name, Country.getMemberNames())

        if(call == null){
            listener.onNullResponse()
        }
        println(call?.request()?.url())

        call?.enqueue(object : Callback<List<Country>> {
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                listener.onError()
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                println(response.body())
                println(Utils.getTimeBasedOnTimeZone(response.body()?.get(0)?.timezones?.get(0)!!))
                listener.onRequestSuccess(response.body())
            }
        })
    }

    override fun getCountryFromLocalCache(name: String, context: Context, listener: HomeInteractor.OnRequestFinishedListener) {
        var countries = ArrayList<Country>()
        val cache = Cache.getInstance(context)
        if(cache.getCountries == null){
            listener.onNullResponse()
            return
        }
        for(country in cache.getCountries){
            if(country.name?.startsWith(name, true)!!){
                countries.add(country)
            }
        }
        listener.onRequestSuccess(countries)
    }

    override fun makeAllCountriesRequest(listener: HomeInteractor.OnRequestFinishedListener) {
        listener.onBeforeRequest()

        val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

        val call = service?.getCountries(Country.getMemberNames())

        if(call == null){
            listener.onNullResponse()
        }
        println(call?.request()?.url())

        call?.enqueue(object : Callback<List<Country>> {
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                listener.onError()
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                println(response.body())
                println(Utils.getTimeBasedOnTimeZone(response.body()?.get(0)?.timezones?.get(0)!!))
                listener.onRequestSuccess(response.body())
            }
        })
    }

    override fun saveCountries(countries: List<Country>, context: Context, listener: HomeInteractor.OnRequestFinishedListener) {
        val cache = Cache.getInstance(context)
        cache.storeCountries(countries)
    }

    override fun checkForInternetConnectivity(context: Context): Boolean {

        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        var isAvailable = false
        if (networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable) {
            isAvailable = true
        }
        return isAvailable
    }

}