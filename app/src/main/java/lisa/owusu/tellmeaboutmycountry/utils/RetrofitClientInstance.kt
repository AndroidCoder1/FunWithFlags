package lisa.owusu.tellmeaboutmycountry.utils

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient


/**
 * RetrofitClientInstance
 *
 * Creating an persisting retrofit instance to use through out app
 *
 * @constructor Empty Constructor.
 */


class RetrofitClientInstance {

    companion object {

        private var retrofit: Retrofit? = null
        private val BASE_URL = "https://restcountries.eu"

        /**
         * Creating Retrofit Instance.. Method checks to see if retrofit instance is null.
         * Creates and return a new instance if it's null or
         * returns a reference to the already created instance
         * @return Retrofit instance.
         */
        fun retrofitInstance(): Retrofit? {
            val httpClient = OkHttpClient.Builder()
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            }
            return retrofit
        }
    }

}