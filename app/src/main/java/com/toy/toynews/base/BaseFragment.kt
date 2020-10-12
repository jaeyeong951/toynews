package com.toy.toynews.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.toy.toynews.utils.LoadingIndicator
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseFragment<DB : ViewBinding, VM : BaseViewModel> : Fragment(){
    private var mLoadingIndicator: Dialog? = null

    abstract val viewModel : VM

    var _binding: DB? = null
    private val binding get() = _binding!!

    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflateBinder(inflater, container)
        mLoadingIndicator = context?.let { LoadingIndicator(it) }
        Log.e("onCreateView","onCreateView")
        return binding.root
    }

    abstract fun inflateBinder(inflater: LayoutInflater,
                               container: ViewGroup?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingIndicatorObserving()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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