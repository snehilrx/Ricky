package com.snehil.ricky.ui.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

private const val URL = "https://www.spacex.com/vehicles/falcon-9/"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Store(webView: MutableState<WebView?>) {
    var progress by remember { mutableIntStateOf(0) }
    var handleBack by remember { mutableStateOf(webView.value?.canGoBack() ?: false) }

    BackHandler(handleBack) {
        webView.value?.goBack()
        handleBack = webView.value?.canGoBack() ?: false
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            factory = {
                val view1 = webView.value
                if (view1 != null) {
                    (view1.parent as? ViewGroup)?.removeView(view1)
                    return@AndroidView view1
                }
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    loadUrl(URL)
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.javaScriptCanOpenWindowsAutomatically = true
                    settings.allowFileAccess = true
                    settings.allowContentAccess = true
                    // handle back navigation and progress
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            handleBack = canGoBack()
                        }
                    }
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            progress = newProgress
                            handleBack = canGoBack()
                        }
                    }
                }
            }
        ) {
            webView.value = it
        }

        if (progress < 100) {
            CircularProgressIndicator(
                modifier = Modifier.width(20.dp).height(20.dp),
            )
        }
    }
}