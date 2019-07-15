package lisa.owusu.tellmeaboutmycountry.utils


import android.os.AsyncTask
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


/**
 * InternetCheck class checks for internet availability
 */
internal class InternetCheck(private val mConsumer: Consumer) : AsyncTask<Void, Void, Boolean>() {

    /**
     * Consumer interface to track internet check progress
     */
    interface Consumer {

        /**
         * accept function returns internet availability
         * @param internet Boolean indicating internet availability
         */
        fun accept(internet: Boolean?)
    }

    /**
     * Constructor executing AsyncTask
     */
    init {
        execute()
    }


    override fun doInBackground(vararg voids: Void): Boolean? {
        return try {
            /**
             * Trying to connect to a socket. If it fails,
             * it is caught in the catch block
             */
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }
    }

    override fun onPostExecute(internet: Boolean?) {
        /**
         * Consumer Interface called with the internet availability indicator
         */
        mConsumer.accept(internet)
    }
}