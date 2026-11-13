package com.waseem.hsk1audiocompanion;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.Locale;

public class MainActivity extends Activity {
    private WebView webView;
    private TextToSpeech tts;
    private boolean ttsReady = false;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new AndroidTTSBridge(), "AndroidTTS");
        tts = new TextToSpeech(this, status -> {
            ttsReady = status == TextToSpeech.SUCCESS;
            if (ttsReady) {
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override public void onStart(String id) { }
                    @Override public void onDone(String id) { notifyJs(id, true); }
                    @Override public void onError(String id) { notifyJs(id, false); }
                });
            }
        });
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void notifyJs(String id, boolean ok) {
        if (webView == null) return;
        String safeId = id.replace("\\", "\\\\").replace("'", "\\'");
        runOnUiThread(() -> webView.evaluateJavascript(
            "window.onNativeTtsDone && window.onNativeTtsDone('" + safeId + "'," + ok + ")", null));
    }

    private class AndroidTTSBridge {
        @JavascriptInterface public void speak(String text, String language, float rate, String id) {
            runOnUiThread(() -> {
                if (!ttsReady || tts == null) { notifyJs(id, false); return; }
                Locale locale = "zh".equals(language) ? Locale.SIMPLIFIED_CHINESE : Locale.US;
                int result = tts.setLanguage(locale);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    notifyJs(id, false); return;
                }
                tts.setSpeechRate(Math.max(0.5f, Math.min(1.5f, rate)));
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, id);
            });
        }
        @JavascriptInterface public void stop() {
            runOnUiThread(() -> { if (tts != null) tts.stop(); });
        }
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack(); else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if (tts != null) { tts.stop(); tts.shutdown(); }
        if (webView != null) { webView.removeJavascriptInterface("AndroidTTS"); webView.destroy(); }
        super.onDestroy();
    }
}
