package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import lisa.owusu.tellmeaboutmycountry.R
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Constants
import lisa.owusu.tellmeaboutmycountry.utils.Utils


class HomeActivity : AppCompatActivity(), HomeView, AdapterView.OnItemClickListener, View.OnClickListener {

    private var appBarLayout : AppBarLayout? = null
    private var toolBar : Toolbar? = null
    private var progressBar : ProgressBar?  = null
    private var textViewError : TextView?  = null
    private var textViewCountryName : TextView?  = null
    private var textViewCapital : TextView?  = null
    private var textViewTime : TextView?  = null
    private var textViewCallingCode : TextView?  = null
    private var textViewViewOnMap: TextView?  = null
    private var textViewCurrency: TextView?  = null
    private var textViewLanguage: TextView?  = null
    private var imageViewFlag : ImageView?  = null
    private var contentLayout : LinearLayout? = null
    private var timeLayout : LinearLayout? = null
    private var oopsLayout : LinearLayout? = null
    private var searchView: SearchView? = null
    private var searchAutoComplete: SearchView.SearchAutoComplete? = null
    private var presenter: HomePresenter? = null
    private var currentCountry: Country? = null
    private var countries: List<Country>? = null
    private var dataAdapter: ArrayAdapter<Country>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        appBarLayout = findViewById(R.id.app_bar_layout)
        toolBar = findViewById(R.id.tool_bar)

        setSupportActionBar(toolBar)

        /**
         *  Removes the shadow bottom border from the AppLayout for Lollipop OS and above,
         *  Its hidden for devices  below Lollipop
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout?.outlineProvider = null
        }

        presenter = HomePresenterImpl(this, HomeInteractorImpl())
        progressBar = findViewById(R.id.progressBar)
        imageViewFlag = findViewById(R.id.iv_country_flag)
        contentLayout = findViewById(R.id.contentLayout)
        timeLayout = findViewById(R.id.timeLayout)
        oopsLayout = findViewById(R.id.oopsContainer)
        textViewError = findViewById(R.id.tv_error)
        textViewTime = findViewById(R.id.tv_time)
        textViewCountryName = findViewById(R.id.tv_country_name)
        textViewCapital = findViewById(R.id.tv_capital)
        textViewCallingCode = findViewById(R.id.tv_calling_code)
        textViewViewOnMap= findViewById(R.id.tv_view_on_map)
        textViewCurrency = findViewById(R.id.tv_currency)
        textViewLanguage = findViewById(R.id.tv_languages)


        textViewViewOnMap?.setOnClickListener(this)

        /**
         * Clearing all Default Text and Images in the layout
         */
        resetScreen()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)

        val searchMenu = menu.findItem(R.id.action_search)
        searchView = searchMenu?.actionView as SearchView
        searchView?.queryHint = "Search for Country"

        /**
         * Expanding the SearchView field to make query hint visible
         * There is no indicator on the screen to show a user where to search
         * country from... Hence the decision to expand it
         */
        searchView?.onActionViewExpanded()
        searchView?.setIconifiedByDefault(false)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isNotEmpty())
                    presenter?.searchForCountryBasedOnQuery(query, this@HomeActivity)
                else resetScreen()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                /**
                 * Clearing filter message when text is changed to prevent user from
                 * selecting a country from drop down when the internet connection goes down
                 * The flag won't be loaded in that case
                 */
                dataAdapter?.filter?.filter(null)

                if (s.length > 1)
                    presenter?.searchForCountryBasedOnQuery(s, this@HomeActivity)
                else resetScreen()
                return false
            }
        })

        /**
         * Accessing the AutoComplete widget from the SearchView widget
         */
        searchAutoComplete = searchView?.findViewById(androidx.appcompat.R.id.search_src_text)
        searchAutoComplete?.onItemClickListener = this

        return true
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun hideKeyboard(activity: Activity) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if(view == null){
            view = View(activity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showOopsContainer() {
        resetScreen()
        changeErrorText(getString(R.string.error_loading_country_details))
        oopsLayout?.visibility = View.VISIBLE
    }

    override fun hideOopsContainer() {
        oopsLayout?.visibility = View.GONE
    }

    override fun showInternetConnectivityError() {
        resetScreen()
        changeErrorText(getString(R.string.internet_connection_error))
        oopsLayout?.visibility = View.VISIBLE
    }

    override fun hideInternetConnectivityError() {
        oopsLayout?.visibility = View.GONE
    }

    override fun resetScreen() {
        hideProgress()
        hideOopsContainer()
        hideOopsContainer()
        hideInternetConnectivityError()
        textViewCountryName?.text = ""
        textViewCurrency?.text = ""
        textViewCallingCode?.text = ""
        textViewCapital?.text= ""
        textViewLanguage?.text = ""
        contentLayout?.visibility = View.GONE

    }

    override fun displayCountryDetails(country: Country?) {

        currentCountry = country
        hideKeyboard(this@HomeActivity)
        hideOopsContainer()
        hideInternetConnectivityError()

        contentLayout?.visibility = View.VISIBLE

        val imageUrl = String.format(Constants.IMAGE_BASE_URL, country?.alpha2Code?.toLowerCase())
        Picasso.get().load(imageUrl).placeholder(android.R.drawable.progress_indeterminate_horizontal).error(R.mipmap.error).into(imageViewFlag)
        textViewCapital?.text= Utils.fromHtml(getString(R.string.capitalFormat, country?.capital))

        textViewTime?.text = Utils.fromHtml(getString(R.string.timeFormat, Utils.getTimeBasedOnTimeZone(country?.timezones?.get(0))))
        textViewCountryName?.text = country?.name

        val currencies = Utils.generateStringsFromList(country?.currencies as ArrayList<Any>)
        textViewCurrency?.text = Utils.fromHtml(getString(R.string.currencyFormat, currencies))

        val callingCodes = Utils.generateStringsFromList(country.callingCodes as ArrayList<Any>)
        textViewCallingCode?.text = Utils.fromHtml(getString(R.string.callingCode, callingCodes))

        val languages = Utils.generateStringsFromList(country.languages as ArrayList<Any>)
        textViewLanguage?.text = Utils.fromHtml(getString(R.string.languagesFormat, languages))

        dataAdapter?.filter?.filter(null)
    }

    override fun changeErrorText(text: String) {
        textViewError?.text = text
    }

    override fun setSearchViewAdapter(data: List<Country>?) {
        searchAutoComplete?.post {
            countries = data
            dataAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, data)
            searchAutoComplete?.setOnClickListener(this)
            searchAutoComplete?.setAdapter(dataAdapter)
            dataAdapter?.notifyDataSetChanged()
            searchAutoComplete?.showDropDown()
        }

    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        view?.post {
            presenter?.onItemClicked(countries?.get(position))
        }
    }

    override fun navigateToMaps(latLngString: String) {
        val mapsIntentUri = Uri.parse(latLngString)
        val intent = Intent(Intent.ACTION_VIEW, mapsIntentUri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, Constants.MAP_REQUEST_CODE)
        }
        else{
            showToast(getString(R.string.maps_not_found))
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            is SearchView.SearchAutoComplete -> {
                if(countries != null){
                    if (countries!!.count() > 0) {
                        if (searchAutoComplete?.text.toString() != "") {
                            dataAdapter?.filter?.filter(null)
                            searchAutoComplete?.showDropDown()
                        }
                    }
                }
            }
            else -> presenter?.onNavigateToMapsClicked(currentCountry)
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        displayCountryDetails(currentCountry)
    }

    override fun onResume() {
        super.onResume()
        startGetAllCountriesService(this)
    }


    override fun startGetAllCountriesService(context: Context) {
//        if (Cache.getInstance(context).getCountries.isEmpty()) {
//            val intent = Intent(context, GetAllCountriesService::class.java)
//            context.startService(intent)
//        }
    }
}
