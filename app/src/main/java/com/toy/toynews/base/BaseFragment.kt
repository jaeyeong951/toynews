package com.toy.toynews.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.toy.toynews.utils.LoadingIndicator
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<VM : BaseViewModel> : Fragment(){
    private var mLoadingIndicator: Dialog? = null

    abstract val viewModel : VM

    abstract val layoutResourceId: Int

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoadingIndicator = context?.let { LoadingIndicator(it) }
        Log.e("onCreateView","onCreateView")
//        return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutResourceId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingIndicatorObserving()
        initView()
    }

    private fun loadingIndicatorObserving() {
        viewModel.startLoadingIndicatorEvent.observe(viewLifecycleOwner, Observer {
            startLoadingIndicator()
        })
        viewModel.stopLoadingIndicatorEvent.observe(viewLifecycleOwner, Observer {
            stopLoadingIndicator()
        })
    }

    private fun stopLoadingIndicator() {
        mLoadingIndicator?.let {
            if (it.isShowing) it.cancel()
        }
    }

    private fun startLoadingIndicator() {
        stopLoadingIndicator()
        activity?.let {
            if (!it.isFinishing) {
                mLoadingIndicator = LoadingIndicator(activity)
                mLoadingIndicator?.show()
            }
        }
    }
}