package com.example.myfastcampusstudy_android.basic.simple_web_browser

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.myfastcampusstudy_android.databinding.ActivitySimpleWebBrowserBinding

class SimpleWebBrowserActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimpleWebBrowserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleWebBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()   // 우리가 원하는 Activity 종료
        }
    }

    private fun initViews() {
        binding.apply {
            webView.apply {
                webViewClient =
                    WebViewClient()                     // 우리가 생성한 웹뷰를 사용하기 위함 == 외부 웹 어플리케이션을 이용하지 않을 수 있음.
                webChromeClient =
                    WebChromeClient()     // ProgressBar 사용을 위해, WebView에서 다양한 기능을 사용하기 위해서는 WebChromClient도 사용해주면 된다.
                settings.javaScriptEnabled = true       // JavaScript 사용을 허용한다. For 웹 이벤트를 위해
                webView.loadUrl(DEFAULT_URL)
            }
        }
    }


    private fun bindViews() {
        binding.apply {
            etSearch.setOnEditorActionListener { view, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    webView.loadUrl(view.text.toString())
                }
                return@setOnEditorActionListener false
            }

            btnGoBack.setOnClickListener { webView.goBack() }
            btnGoForward.setOnClickListener { webView.goForward() }
            btnGoHome.setOnClickListener { webView.loadUrl(DEFAULT_URL) }
            refreshLayout.setOnRefreshListener {
                webView.reload()
            }
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)

            binding.progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            binding.apply {
                refreshLayout.isRefreshing = false
                progressBar.hide()
            }

        }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            binding.progressBar.progress = newProgress
        }
    }

    companion object {
        private const val DEFAULT_URL = "http://www.google.com"
    }
}