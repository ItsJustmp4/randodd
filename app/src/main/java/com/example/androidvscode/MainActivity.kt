package com.example.androidvscode

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidBridge")
        webView.webViewClient = WebViewClient()

        // Загружаем локальный интерфейс редактора
        webView.loadUrl("file:///android_asset/index.html")
    }

    inner class WebAppInterface(private val activity: MainActivity) {
        @JavascriptInterface
        fun openMarketplace() {
            activity.runOnUiThread {
                val availablePlugins = arrayOf(
                    "Python Extension (Microsoft)",
                    "C# Dev Kit",
                    "Prettier - Code Formatter",
                    "GitLens — Git supercharged",
                    "One Dark Pro Theme"
                )

                AlertDialog.Builder(activity)
                    .setTitle("VS Code Marketplace")
                    .setItems(availablePlugins) { _, which ->
                        val pluginName = availablePlugins[which]
                        webView.evaluateJavascript(
                            "alert('Плагин "$pluginName" успешно установлен!');", 
                            null
                        )
                    }
                    .setNegativeButton("Закрыть", null)
                    .show()
            }
        }
    }
}
