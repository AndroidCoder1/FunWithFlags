package lisa.owusu.tellmeaboutmycountry.utils

import lisa.owusu.tellmeaboutmycountry.models.Country
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit Request Interface
 *
 * Interface specifying the requests signature from server
 *
 */
interface Requests {

    @GET("/rest/v2/all")
    /**
     * Request to get all countries with url query fields specified
     * @param fields string specifying all the fields that should be returned, separated by semi colon
     * @return the response containing list of countries.
     */
    fun getCountries(@Query("fields") fields: String): Call<List<Country>>



    @GET("/rest/v2/name/{name}")
    /**
     * Request to return details about a country based on country name
     * @param name string specifying country name
     * @param fields string specifying all the fields that should be returned, separated by semi colon
     * @return the response containing list of countries.
     */
    fun getCountry(@Path("name") name: String, @Query("fields") fields: String): Call<List<Country>>
}