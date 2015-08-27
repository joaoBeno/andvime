package br.net.beno.andvime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Gerando um numero aleatório
        Random aleatorio = new Random();
        int uniq_str = aleatorio.nextInt((268923423 + 1)+897);
        String rand_str = String.valueOf(uniq_str);

        // Convertendo ele num hash MD5
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(rand_str.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        Log.e("================> ","Hex format : " + sb.toString());

//        Map<String, String> extraHeaders = new HashMap<>();
//        extraHeaders.put("response_type", "code");
//        extraHeaders.put("client_id", "66d90b2d6f01e0f8e7f4bc0851834ae290d6639b");
//        extraHeaders.put("redirect_uri", "http://localhost");
//        extraHeaders.put("state", sb.toString());

        webView = (WebView) findViewById(R.id.visao_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://api.vimeo.com/oauth/authorize?response_type=code&client_id=66d90b2d6f01e0f8e7f4bc0851834ae290d6639b&redirect_uri=http://localhost&state="+sb.toString());
    }
}
