package lisa.owusu.tellmeaboutmycountry.utils

import android.os.Build
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class Utils {
    companion object {

        fun getTimeBasedOnTimeZone(timezoneStr: String): String {

            println(Calendar.getInstance().timeZone.id)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val zoneId = ZoneId.of(timezoneStr)
                var zonedDateTime = ZonedDateTime.now(zoneId)
                zonedDateTime.toString()

            } else {

                val zoneId = org.threeten.bp.ZoneId.of(timezoneStr)
                var zonedDateTime = org.threeten.bp.ZonedDateTime.now(zoneId)
                zonedDateTime.toString()

            }
        }


    }
}