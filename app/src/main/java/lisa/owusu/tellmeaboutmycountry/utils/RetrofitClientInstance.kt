package lisa.owusu.tellmeaboutmycountry.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


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
        private const val BASE_URL = "https://restcountries.eu"

        /**
         * Creating Retrofit Instance.. Method checks to see if retrofit instance is null.
         * Creates and return a new instance if it's null or
         * returns a reference to the already created instance
         * @return Retrofit instance.
         */
        fun retrofitInstance(): Retrofit? {
            val httpClient = OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
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