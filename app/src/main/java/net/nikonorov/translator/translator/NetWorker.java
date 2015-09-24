package net.nikonorov.translator.translator;

import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vitaly on 23.09.15.
 */
public class NetWorker extends AsyncTask<String, Void, JSONObject> {

    JSONObject response = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isr = new InputStreamReader(in);

            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();

            response = new JSONObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }

}
