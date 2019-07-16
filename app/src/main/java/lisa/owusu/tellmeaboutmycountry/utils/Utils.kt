package lisa.owusu.tellmeaboutmycountry.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.Html
import android.text.Spanned
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Utils class is a helper class with helper methods
 */
class Utils {
    companion object {

        /**
         * Method to get time in a country based on the country's timezone
         * A country may have multiple timezones but for this purpose the 
         * first one is used
         * @param timezoneStr String of the timezone
         */
        fun getTimeBasedOnTimeZone(timezoneStr: String?): String {

            val pattern = "EEE dd-MMM-yyyy hh:mm:ss a"
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val zoneId = ZoneId.of(timezoneStr)
                var zonedDateTime = ZonedDateTime.now(zoneId)
                val formatter = DateTimeFormatter.ofPattern(pattern)
                val formattedString = zonedDateTime.format(formatter)
                formattedString

            } else {

                val zoneId = org.threeten.bp.ZoneId.of(timezoneStr)
                var zonedDateTime = org.threeten.bp.ZonedDateTime.now(zoneId)
                val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern(pattern)
                val formattedString = zonedDateTime.format(formatter)
                formattedString
            }
        }

        /**
         * Method to generate readable message from a list
         * @param list list of items
         */
        fun generateStringsFromList(list: ArrayList<Any>?) : String{
            var generatedStrings = StringBuilder()
            if(list != null) {
                for (item in list) {
                    if(!item.toString().contains("null")) {
                        generatedStrings.append(item)
                        generatedStrings.append(", ")
                    }
                }
                return if(generatedStrings.length > 2) generatedStrings.deleteCharAt(generatedStrings.length - 2).toString() else generatedStrings.toString()
            }
            return generatedStrings.toString()
        }

        /**
         * Method to render html irrespective of the Build Version
         * @param htmlString html string to be rendered
         */
        fun fromHtml(htmlString: String) : Spanned{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlString)
            }
        }

        /**
         * Checks if Network hardware is turned on/off
         * @param context current activity context
         */
        fun checkForInternetConnectivity(context: Context): Boolean {

            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = manager.activeNetworkInfo
            var isAvailable = false
            if (networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable) {
                isAvailable = true
            }
            return isAvailable
        }

    }
}