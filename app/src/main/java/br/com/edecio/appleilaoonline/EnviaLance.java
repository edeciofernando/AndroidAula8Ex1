package br.com.edecio.appleilaoonline;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by edecio on 28/09/2017.
 */

public class EnviaLance extends AsyncTask<String, Void, Integer>{

    private TextView txtMensa;

    public EnviaLance(TextView txtMensa) {
        this.txtMensa = txtMensa;
    }

    @Override
    protected Integer doInBackground(String... params) {
        int status = 0;

        String ws = params[0];
        String cliente = params[1];
        String email = params[2];
        String lance = params[3];

        try {
            URL url = new URL(ws);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");

                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

                // define string de envio
                String envio = "nome="+cliente+"&email="+email+"&lance="+lance;

                out.write(envio.getBytes(StandardCharsets.UTF_8));

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                if (in != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    status = Integer.parseInt(br.readLine());
                }
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return status;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if (integer == 1) {
            txtMensa.setText("Ok! Lance Registrado. Aguarde contato...");
        } else {
            txtMensa.setText("Erro... Não foi possível enviar seu lance. Tente mais tarde.");
        }


    }
}
