package com.toy.toynews.ui

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.toy.toynews.R
import com.toy.toynews.base.BaseFragment
import com.toy.toynews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel> (){
    override val viewModel: MainViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.fragment_main

    override fun initView() {
        viewModel.loadNews(country = "kr")
        viewModel.isLoadFinished.observe(this, Observer {
            main_list.adapter = MainNewsAdapter(viewModel.newsList, object: MainNewsAdapter.OnItemClickListener {
                override fun onItemClick(v: View, position: Int) {
                    val action
                            = MainFragmentDirections.actionMainFragmentToWebViewFragment(viewModel.newsList.get(position).url)
                    findNavController().navigate(action)
                }
            })
        })
        activity?.let {
            var country = "kr"
            main_spinner.adapter = ArrayAdapter(it.baseContext,
                R.layout.spinner_item,
                resources.getStringArray(R.array.countries))
            main_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
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
                    viewModel.loadNews(country = country)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("TRACE", "Main onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TRACE", "Main onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TRACE", "Main onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TRACE", "Main onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TRACE", "Main onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("TRACE", "Main onDetach")
    }
}