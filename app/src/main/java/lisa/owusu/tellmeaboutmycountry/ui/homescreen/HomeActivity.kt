package lisa.owusu.tellmeaboutmycountry.ui.homescreen

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import lisa.owusu.tellmeaboutmycountry.R
import lisa.owusu.tellmeaboutmycountry.models.Country
import lisa.owusu.tellmeaboutmycountry.utils.Requests
import lisa.owusu.tellmeaboutmycountry.utils.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import lisa.owusu.tellmeaboutmycountry.utils.Utils
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.squareup.picasso.Picasso


class HomeActivity : AppCompatActivity(), HomeView, AdapterView.OnItemClickListener  {

    private var appBarLayout : AppBarLayout? = null
    private var toolBar : Toolbar? = null
    private var progressBar : ProgressBar?  = null
    private var textViewError : TextView?  = null
    private var textViewCountryName : TextView?  = null
    private var textViewCapital : TextView?  = null
    private var textViewCallingCode : TextView?  = null
    private var textViewViewOnMap: TextView?  = null
    private var textViewCurrency: TextView?  = null
    private var textViewLanguage: TextView?  = null
    private var imageViewFlag : ImageView?  = null
    private var bottomViewLayout : LinearLayout? = null
    private var oopsLayout : LinearLayout? = null
    private var sheetBehavior : BottomSheetBehavior<View>? = null
    private var searchView: SearchView? = null
    private var searchAutoComplete: SearchView.SearchAutoComplete? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        appBarLayout = findViewById(R.id.app_bar_layout)
        toolBar = findViewById(R.id.tool_bar)

        setSupportActionBar(toolBar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout?.outlineProvider = null
        }


        progressBar = findViewById(R.id.progressBar)
        imageViewFlag = findViewById(R.id.iv_country_flag)
        bottomViewLayout = findViewById(R.id.contentLayout)
        oopsLayout = findViewById(R.id.oopsContainer)
        textViewError = findViewById(R.id.tv_error)
        textViewCountryName = findViewById(R.id.tv_country_name)
        textViewCapital = findViewById(R.id.tv_capital)
        textViewCallingCode = findViewById(R.id.tv_calling_code)
        textViewViewOnMap= findViewById(R.id.tv_view_on_map)
        textViewCurrency = findViewById(R.id.tv_currency)
        textViewLanguage = findViewById(R.id.tv_languages)

        sheetBehavior = BottomSheetBehavior.from(bottomViewLayout)
        sheetBehavior?.isFitToContents = true
        sheetBehavior?.isHideable = false
        sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED



        searchAutoComplete = searchView?.findViewById(androidx.appcompat.R.id.search_src_text)




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)

        val searchMenu = menu.findItem(R.id.action_search)
        val searchView = searchMenu?.actionView as SearchView
        searchView.queryHint = "Search for Country"
        searchView.onActionViewExpanded()
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                if (!searchView.isIconified) {
//                    searchView.isIconified = true
//                }
//                searchMenu.collapseActionView()
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun showOopsContainer() {
        changeErrorText(getString(R.string.error_loading_country_details))
        oopsLayout?.visibility = View.VISIBLE
    }

    override fun hideOopsContainer() {
        oopsLayout?.visibility = View.GONE
    }

    override fun showInternetConnectivityError() {
        changeErrorText(getString(R.string.internet_connection_error))
        oopsLayout?.visibility = View.VISIBLE
    }

    override fun hideInternetConnectivityError() {
        oopsLayout?.visibility = View.GONE
    }

    override fun resetScreen() {
        collapseBottomView()
        hideKeyboard()
        hideOopsContainer()
        hideInternetConnectivityError()
        textViewCountryName?.text = ""
        textViewCurrency?.text = ""
        textViewCallingCode?.text = ""
        textViewCapital?.text= ""
        textViewLanguage?.text = ""
        imageViewFlag?.setImageResource(0)

    }

    override fun displayCountryDetails(country: Country?) {
        expandBottomView()
        hideKeyboard()
        hideOopsContainer()
        hideInternetConnectivityError()
        Picasso.get().load(country?.flag).into(imageViewFlag)
        textViewCountryName?.text = country?.name
        textViewCurrency?.text = ""
        textViewCallingCode?.text = ""
        textViewCapital?.text= ""
        textViewLanguage?.text = ""
    }

    override fun changeErrorText(text: String) {
        textViewError?.text = text
    }

    override fun expandBottomView() {
        sheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun collapseBottomView() {
        sheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun setSearchViewAdapter(data: List<String>) {
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, data)
        searchAutoComplete?.setAdapter(dataAdapter)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}
