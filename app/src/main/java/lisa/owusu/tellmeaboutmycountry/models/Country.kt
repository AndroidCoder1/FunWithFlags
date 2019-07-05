package lisa.owusu.tellmeaboutmycountry.models

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder



/**
 * Country Model
 *
 * Class representing the Country Model from server.
 *
 * @constructor Empty Constructor.
 */
class Country {


    @SerializedName("name")
    var name : String? = null

    @SerializedName("callingCodes")
    var callingCodes = ArrayList<String>()

    @SerializedName("capital")
    var capital : String? = null

    @SerializedName("latlng")
    var latlng = ArrayList<Float>()

    @SerializedName("timezones")
    var timezones = ArrayList<String>()

    @SerializedName("borders")
    var borders = ArrayList<String>()

    @SerializedName("currencies")
    var currencies = ArrayList<Currency>()

    @SerializedName("languages")
    var languages = ArrayList<Language>()

    @SerializedName("flag")
    var flag : String? = null




    companion object{

        /**
         * Get the filter query for the getCountry request
         * @return the filter query string.
         */
        fun getMemberNames() : String{
            val sb = StringBuilder()
            for (str in Country::class.java.declaredFields){
                if(!str.name.equals("companion", true)) {
                    sb.append(str.name)
                    sb.append(";")
                }
            }
            sb.setCharAt(sb.length -1, ' ')
            return sb.trim().toString()
        }
    }

    /**
     * The customised simplified description of parameters in the class
     * @return the customised description .
     */
    override fun toString(): String {
        return "Country(name=$name, callingCodes=$callingCodes, capital=$capital, latlng=$latlng, timezones=$timezones, borders=$borders, currencies=$currencies, languages=$languages, flag=$flag)"
    }
}

