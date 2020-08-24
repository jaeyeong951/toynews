package com.toy.toynews.ui

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.core.content.res.use
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.toy.toynews.R
import com.toy.toynews.base.BaseFragment
import com.toy.toynews.viewmodel.WebViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_web_view.*

@AndroidEntryPoint
class WebViewFragment : BaseFragment<WebViewModel>() {
    override val viewModel: WebViewModel by viewModels()

    override val layoutResourceId: Int
        get() = R.layout.fragment_web_view

    private val args : WebViewFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500L
            isElevationShadowEnabled = true
            setAllContainerColors(requireContext().getColor(R.color.background))
        }
    }

    override fun initView() {
        webview_container.transitionName = args.url
        //enterTransition = MaterialFadeThrough()
        webview.settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.setSupportZoom(true)
            it.blockNetworkImage = false
            it.loadsImagesAutomatically = true
            it.domStorageEnabled = true
        }

        webview.fitsSystemWindows = true
        webview.loadUrl(args.url)
        webview.webViewClient = WebViewClient()

        address_map_fab_btn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}