package br.net.beno.andvime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadLoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "AndVimePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_login);

        Intent intent = getIntent();
        if (intent.hasExtra("br.net.beno.andvime.CODIGO_API")) {
            // Get the message from the intent
            String url_api = intent.getStringExtra(WebViewActivity.API_COD);
            // token_api =
            obterToken(url_api);
        }
    }

    private void obterToken(String url_resposta) {
        int pos = url_resposta.indexOf("code=") + 5;

        DownloadDados task = new DownloadDados();
        task.execute(new String[]{url_resposta.substring(pos)});
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
                        ("66d90b2d6f01e0f8e7f4bc0851834ae290d6639b" + ":" + "098faea78c9df16f0f1634b55467602a1c387d56").getBytes(), Base64.NO_WRAP));
                urlConnection.connect();

                String str = "grant_type=authorization_code&code="+url_op[0]+"&redirect_uri=http://localhost";
                byte[] outputInBytes = str.getBytes();
                OutputStream os = urlConnection.getOutputStream();
                os.write(outputInBytes);
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

            try {
                JSONObject resposta = new JSONObject(result);

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("token", resposta.getString("access_token"));
                editor.putString("bio", resposta.getJSONObject("user").getString("bio"));
                editor.putString("location", resposta.getJSONObject("user").getString("location"));
                editor.putString("link", resposta.getJSONObject("user").getString("link"));
                editor.putString("name", resposta.getJSONObject("user").getString("name"));
                editor.putString("uri", resposta.getJSONObject("user").getString("uri"));
                editor.putString("foto_link", resposta.getJSONObject("user").getJSONArray("pictures").getJSONObject(3).getString("link"));

                // Commit the edits!
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), ListaVideosActivity.class);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
