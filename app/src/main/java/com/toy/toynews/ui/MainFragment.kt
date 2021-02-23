package com.toy.toynews.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.toy.toynews.R
import com.toy.toynews.base.BaseFragment
import com.toy.toynews.databinding.FragmentMainBinding
import com.toy.toynews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel> (){
    override val viewModel: MainViewModel by viewModels(ownerProducer = {findNavController().getViewModelStoreOwner(R.id.navigation)})

    private var savedSpinnerPosition = 0

    override fun inflateBinder(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        postponeEnterTransition()
        view?.doOnPreDraw { startPostponedEnterTransition() }
        //exitTransition = MaterialFadeThrough()

        viewModel.testSingleLiveData.observe(viewLifecycleOwner) {
            Log.e("SingleLiveData", "Observed")
        }

        viewModel.testMutableLiveData.observe(viewLifecycleOwner) {
            Log.e("MutableLiveData", "Observed")
        }

        viewModel.invokeSingleLiveDate()
        viewModel.invokeMutableLiveData()

        _binding!!.mainList.apply {
            this.adapter = MainNewsAdapter { v, position ->
                val action = MainFragmentDirections.actionMainFragmentToWebViewFragment(viewModel.newsList[position].url)
                findNavController().navigate(action, FragmentNavigatorExtras(v to viewModel.newsList[position].url))
            }.apply {
                viewModel.isLoadFinished.observe(viewLifecycleOwner, Observer {
                    this.newsList = it
                })
            }
            this.setHasFixedSize(true)
            this.edgeEffectFactory = BounceEdgeEffectFactory()
        }

        if(viewModel.newsList.isEmpty()) viewModel.loadNews(country = "kr")
        activity?.let {
            var country = "kr"
            _binding!!.mainSpinner.adapter = ArrayAdapter(it,
                R.layout.spinner_item,
                resources.getStringArray(R.array.countries))
            _binding!!.mainSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("MainFragment", "onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.e("MainFragment", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainFragment", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("MainFragment", "onDetach")
    }
}