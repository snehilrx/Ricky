package com.snehil.falconix.ui.components

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Store() {
    var progress by remember { mutableIntStateOf(0) }
    var handleBack by remember { mutableStateOf(false) }

    if (progress < 100) {
        CircularProgressIndicator(progress = {progress/100f})
    }
    var webView: WebView? = null
    AndroidView(
        factory = {
            WebView(it).apply {
                loadUrl("https://www.spacex.com/vehicles/falcon-9/")
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
                    }
                }
            }
        }
    ) {
        webView = it
    }

    BackHandler(handleBack) {
        webView?.goBack()
    }
}