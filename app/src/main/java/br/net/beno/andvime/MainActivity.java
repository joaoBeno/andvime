package br.net.beno.andvime;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String token_api = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.hasExtra("br.net.beno.andvime.CODIGO_API")) {
            // Get the message from the intent
            String url_api = intent.getStringExtra(WebViewActivity.API_COD);
            // token_api =
            obterToken(url_api);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirLogin(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    private void obterToken(String url_resposta) {
        int pos = url_resposta.indexOf("code=") + 5;

        DownloadDados task = new DownloadDados();
        task.execute(new String[] { url_resposta.substring(pos) });

        //return url_resposta.substring(pos);
    }

    private class DownloadDados extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {

        };

        @Override
        protected String doInBackground(String... url_op) {
            // TODO Auto-generated method stub

            // Call your web service here
            URL url;
            HttpURLConnection urlConnection = null;

            String dados = "";

            try {
                url = new URL("https://api.vimeo.com/oauth/access_token");

                urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString(
                        ("66d90b2d6f01e0f8e7f4bc0851834ae290d6639b" + ":" + "098faea78c9df16f0f1634b55467602a1c387d56").getBytes(),
                        Base64.DEFAULT));
                urlConnection.connect();

                String str = "grant_type=authorization_code&code="+url_op[0]+"&redirect_uri=http://localhost";
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = urlConnection.getOutputStream();
                os.write(outputInBytes );
                os.close();

                int status = urlConnection.getResponseCode();

                InputStream in;
                if (status >= 400) {
                    in = urlConnection.getErrorStream();
                } else {
                    in = urlConnection.getInputStream();
                }

                InputStreamReader isw = new InputStreamReader(in);

                BufferedReader buffer = new BufferedReader(isw);
                String s;
                while ((s = buffer.readLine()) != null) {
                    dados += s;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    urlConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace(); //If you want further info on failure...
                }
            }

            return dados;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            // Update your UI here
            Log.e("================> ","resposta: " +result);
            token_api = result;
        }
    }
}
