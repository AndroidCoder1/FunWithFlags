package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.animation.Animator
import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import lisa.owusu.tellmeaboutmycountry.models.Country
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

    override fun getCountryFromLocalCache(name: String, listener: HomeInteractor.OnRequestFinishedListener) {

    }

    override fun makeAllCountriesRequest(listener: HomeInteractor.OnRequestFinishedListener) {

    }

    override fun saveCountries(countries: List<Country>, listener: HomeInteractor.OnRequestFinishedListener) {

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