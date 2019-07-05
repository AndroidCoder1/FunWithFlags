package lisa.owusu.tellmeaboutmycountry

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Requests
import lisa.owusu.tellmeaboutmycountry.utils.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitClientInstance.retrofitInstance()?.create(Requests::class.java)

        val call = service?.getCountry("ghana", Country.getMemberNames())

        println(call?.request()?.url())

        call?.enqueue(object : Callback<List<Country>> {

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                println(response.body())
            }

        })
    }
}
