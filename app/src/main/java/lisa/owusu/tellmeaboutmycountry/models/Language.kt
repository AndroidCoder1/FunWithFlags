package lisa.owusu.tellmeaboutmycountry.models

import com.google.gson.annotations.SerializedName

/**
 * Language Model
 *
 * Class representing the Language Model from server.
 *
 * @constructor Empty Constructor.
 */
class Language {

    @SerializedName("name")
    var name : String? = null

    @SerializedName("nativeName")
    var nativeName: String? = null


    /**
     * The customised simplified description of parameters in the class
     * @return the customised description .
     */
    override fun toString(): String {
        return "$name ($nativeName)"
    }
}