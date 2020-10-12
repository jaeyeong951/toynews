package com.toy.toynews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.toy.toynews.R
import com.toy.toynews.base.BaseFragment
import com.toy.toynews.databinding.FragmentWebViewBinding
import com.toy.toynews.viewmodel.WebViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding, WebViewModel>() {
    override val viewModel: WebViewModel by viewModels()

    private val args : WebViewFragmentArgs by navArgs()

    override fun inflateBinder(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500L
            isElevationShadowEnabled = true
            setAllContainerColors(requireContext().getColor(R.color.background))
        }
    }

    override fun initView() {
        _binding!!.webview.transitionName = args.url
        //enterTransition = MaterialFadeThrough()
        _binding!!.webview.settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.setSupportZoom(true)
            it.blockNetworkImage = false
            it.loadsImagesAutomatically = true
            it.domStorageEnabled = true
        }

        _binding!!.webview.fitsSystemWindows = true
        _binding!!.webview.loadUrl(args.url)
        _binding!!.webview.webViewClient = WebViewClient()

        _binding!!.addressMapFabBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}