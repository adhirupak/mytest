package com.rupak.project.test.ui.fragments.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rupak.project.test.R
import com.rupak.project.test.common.MainApplication
import com.rupak.project.test.utils.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created By Rupak Adhikari
 */
class SearchFragment : Fragment(), TextWatcher {
    private lateinit var searchEditText: EditText
    private lateinit var crossImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var noResultsMessage: TextView
    private lateinit var searchArtistAdapter: SearchArtistAdapter
    private lateinit var searchSubject: PublishSubject<String>
    private val compositeDisposable = CompositeDisposable()

    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var factory: SearchVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        searchSubject.onNext(s.toString())
        if(s.toString().length < 2) {
            crossImageView.visibility = View.INVISIBLE
            return
        }

        crossImageView.visibility = View.VISIBLE



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MainApplication).createSearchComponent().inject(this)
        viewModel =  ViewModelProvider(this,factory).get(SearchViewModel::class.java)
        searchSubject = PublishSubject.create()

        val disposable = searchSubject.debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it != searchArtistAdapter.query) {
                    viewModel.search(it)
                } else {
                    Log.i(javaClass.simpleName, "Same query -> aborting search")
                }
            }

        compositeDisposable.add(disposable)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observe(this, Observer {
            if(it != null) handleStateView(it)
        })

        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(activity,throwable.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleStateView(state: SearchStateWithView){
        progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE
        val artist = state.artistList ?: listOf()
        if(state.showNoResultMessage){
            noResultsMessage.visibility = View.VISIBLE
            noResultsMessage.text = String.format("No artist found for %s", state.lastSearchArtist)
        }else{
            noResultsMessage.visibility =View.GONE
        }
        searchArtistAdapter.setArtist(artist,state.lastSearchArtist)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchEditText = search_artist_edit_text
        searchEditText.addTextChangedListener(this)
        crossImageView = cross
        crossImageView.visibility = View.INVISIBLE
        crossImageView.setOnClickListener(View.OnClickListener { searchEditText.setText("") })
        progressBar = search_artist_progress
        noResultsMessage = search_artist_no_results_message
        searchArtistAdapter = SearchArtistAdapter(imageLoader) { artist ->
            showSoftKeyboard(false)
            Toast.makeText(activity,"Clicked = ${artist.name}",Toast.LENGTH_LONG).show()
        }

        recyclerView = search_artist_recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = searchArtistAdapter
        searchEditText.requestFocus()
        showSoftKeyboard(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_search, container, false)
    }

    private fun showSoftKeyboard(show: Boolean) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
        } else {
            imm.hideSoftInputFromWindow(searchEditText.windowToken,0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("lastSearch", searchEditText.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showSoftKeyboard(false)
        compositeDisposable.clear()
        (activity?.application as MainApplication).releaseSearchComponent()
    }











}