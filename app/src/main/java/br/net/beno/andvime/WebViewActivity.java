package br.net.beno.andvime;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class WebViewActivity extends AppCompatActivity {

    public final static String API_COD = "br.net.beno.andvime.CODIGO_API";

    private WebView webView;
    private Boolean chamado_feito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // ActionBar
        ActionBar actionBar =  getSupportActionBar();
        actionBar.hide();

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

        webView = (WebView) findViewById(R.id.visao_web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (url.contains("http://localhost/?") && !chamado_feito) {
                    Intent intent = new Intent(getApplicationContext(), LoadLoginActivity.class);
                    intent.putExtra(API_COD, url);

                    chamado_feito = !chamado_feito;

                    startActivity(intent);
                }
            }
        });
        webView.loadUrl("https://api.vimeo.com/oauth/authorize?response_type=code&client_id=66d90b2d6f01e0f8e7f4bc0851834ae290d6639b&redirect_uri=http://localhost&state="+sb.toString());
    }
}
