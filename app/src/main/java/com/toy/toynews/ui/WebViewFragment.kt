package com.toy.toynews.ui

import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
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

    private val url : WebViewFragmentArgs by navArgs()

    override fun initView() {
        webview.settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.setSupportZoom(true)
            it.blockNetworkImage = false
            it.loadsImagesAutomatically = true
            it.domStorageEnabled = true
        }

        webview.fitsSystemWindows = true
        webview.loadUrl(url.url)
        webview.webViewClient = WebViewClient()

    }
}