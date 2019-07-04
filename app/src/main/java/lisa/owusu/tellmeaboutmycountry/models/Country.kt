package lisa.owusu.tellmeaboutmycountry.models

import java.io.Serializable

class Country : Serializable {

    var name = ""
    var callingCodes = {}
    var capital = ""
    var latlng = {}
    var timezones = {}
    var borders = {}
    var currencies = arrayOf<Currency>()
    var languages = arrayOf<Language>()
    var flag = ""

}

