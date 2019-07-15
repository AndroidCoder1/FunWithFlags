package lisa.owusu.tellmeaboutmycountry.utils

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class Utils {
    companion object {

        fun getTimeBasedOnTimeZone(timezoneStr: String?): String {

            println(Calendar.getInstance().timeZone.id)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val zoneId = ZoneId.of(timezoneStr)
                var zonedDateTime = ZonedDateTime.now(zoneId)
                val formatter = DateTimeFormatter.ofPattern("EEE dd-MMM-yyyy hh:mm:ss a")
                val formattedString = zonedDateTime.format(formatter)
                formattedString

            } else {

                val zoneId = org.threeten.bp.ZoneId.of(timezoneStr)
                var zonedDateTime = org.threeten.bp.ZonedDateTime.now(zoneId)
                val formatter = org.threeten.bp.format.DateTimeFormatter.ofPattern("EEE dd-MMM-yyyy hh:mm:ss a")
                val formattedString = zonedDateTime.format(formatter)
                formattedString
            }
        }

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

        fun fromHtml(htmlString: String) : Spanned{
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(htmlString)
            }
        }

    }
}