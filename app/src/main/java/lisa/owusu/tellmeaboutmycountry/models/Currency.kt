package lisa.owusu.tellmeaboutmycountry.models

import com.google.gson.annotations.SerializedName


/**
 * Currency Model
 *
 * Class representing the Currency Model from server.
 *
 * @constructor Empty Constructor.
 */
class Currency{

    @SerializedName("name")
    var name : String? = null

    @SerializedName("symbol")
    var symbol : String? = null

    @SerializedName("code")
    var code : String? = null


    /**
     * The customised simplified description of parameters in the class
     * @return the customised description .
     */
    override fun toString(): String {
        if(symbol == null)
            return "$name ($code)"
        return "$name ($symbol)"
    }
}