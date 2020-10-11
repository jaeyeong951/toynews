package com.toy.toynews.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.toy.toynews.R
import com.toy.toynews.base.BaseFragment
import com.toy.toynews.databinding.FragmentMainBinding
import com.toy.toynews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel> (){
    override val viewModel: MainViewModel by viewModels(ownerProducer = {findNavController().getViewModelStoreOwner(R.id.navigation)})

    private var savedSpinnerPosition = 0

    private lateinit var mainNewsAdapter: MainNewsAdapter

    override fun inflateBinder(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        postponeEnterTransition()
        view?.doOnPreDraw { startPostponedEnterTransition() }
        //exitTransition = MaterialFadeThrough()
        mainNewsAdapter = MainNewsAdapter(viewModel.newsList, itemClick)
        main_list.adapter = mainNewsAdapter
        if(viewModel.newsList.isEmpty()) viewModel.loadNews(country = "kr")
        main_list.setHasFixedSize(true)
        viewModel.isLoadFinished.observe(this, Observer {
            mainNewsAdapter.notifyDataSetChanged()
            Log.e("singleLiveEvent","OBSERVE")
        })
        activity?.let {
            var country = "kr"
            main_spinner.adapter = ArrayAdapter(it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.countries))
            main_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(view != null && savedSpinnerPosition != position){
                        when(position) {
                            0 -> {
                                country = "kr"
                            }
                            1 -> {
                                country = "br"
                            }
                            2 -> {
                                country = "de"
                            }
                            3 -> {
                                country = "hk"
                            }
                            4 -> {
                                country = "jp"
                            }
                            5 -> {
                                country = "mx"
                            }
                            6 -> {
                                country = "us"
                            }
                        }
                        Log.e("Item"," $position Is Selected")
                        savedSpinnerPosition = position
                        viewModel.loadNews(country = country)
                    }
                }
            }
        }
    }
    private val itemClick = object : MainNewsAdapter.OnItemClickListener {
        override fun onItemClick(v: View, position: Int) {
            val action
                    = MainFragmentDirections.actionMainFragmentToWebViewFragment(viewModel.newsList[position].url)
                findNavController().navigate(action, FragmentNavigatorExtras(v to viewModel.newsList[position].url))
        }
    }
}