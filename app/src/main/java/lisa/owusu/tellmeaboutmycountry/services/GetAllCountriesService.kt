package lisa.owusu.tellmeaboutmycountry.services

import android.app.IntentService
import android.content.Intent
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Cache
import lisa.owusu.tellmeaboutmycountry.utils.InternetCheck
import lisa.owusu.tellmeaboutmycountry.utils.Requests
import lisa.owusu.tellmeaboutmycountry.utils.RetrofitClientInstance
import lisa.owusu.tellmeaboutmycountry.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetAllCountriesService : IntentService("GetCountriesIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        if (!Utils.checkForInternetConnectivity(this)) {
            println(">>>>>>>>network hardware turned off")
            return
        }
        try {
            InternetCheck(object : InternetCheck.Consumer {

                override fun accept(internet: Boolean?) {
                    if (internet == false) {
                        println(">>>>>>>>>>>no internet")
                        return
                    }

                    val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

                    val call = service?.getCountries(Country.getMemberNames()) ?: return

                    println(call?.request()?.url())

                    call?.enqueue(object : Callback<List<Country>> {
                        override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                            println(">>>>>>>>>>failure")
                            return
                        }

                        override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                            println(response.body())
                            if (response.body() != null){
                                println(">>>>>>>>>>>saving instance")
                                Cache.getInstance(this@GetAllCountriesService).storeCountries(response.body())
                            }
                        }
                    })
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            println(">>>>>>>>print exception in service")
            return
        }
    }
}
