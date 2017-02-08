package com.example.galux.mofos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String result;
    String url;
    Button gamospito;
    TextView feleki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "http://www.e-orders.org/api/app/test";
        gamospito = (Button) findViewById(R.id.button);
        feleki = (TextView) findViewById(R.id.mydi);
        Koloapi arxidi = new Koloapi();

        gamospito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Koloapi().execute(url);
            }
        });
    }

    public void ProcessResponse(String response) {
        result = response;
        feleki.setText(response);
    }

    public String GET(String u) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String line;
        String jsonString = "";

        try {
            URL url = new URL(u);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return jsonString;
    }

    public class Koloapi extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            return GET(url);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ProcessResponse(s);
        }
    }
}
