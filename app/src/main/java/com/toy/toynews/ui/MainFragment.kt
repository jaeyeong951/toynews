package com.toy.toynews.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
            main_list.adapter = MainNewsAdapter(viewModel.newsList)
        })
    }
}