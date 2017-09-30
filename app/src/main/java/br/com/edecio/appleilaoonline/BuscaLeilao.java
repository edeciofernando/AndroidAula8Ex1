package br.com.edecio.appleilaoonline;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by edecio on 21/09/2017.
 */

public class BuscaLeilao extends AsyncTask<String, Void, String> {

    private TextView txtObra;
    private TextView txtPath;
    private Button btnVerObra;
    private AsyncResposta asyncResposta;

    public BuscaLeilao(TextView txtObra, TextView txtPath, Button btnVerObra, AsyncResposta asyncResposta) {
        this.txtObra = txtObra;
        this.txtPath = txtPath;
        this.btnVerObra = btnVerObra;
        this.asyncResposta = asyncResposta;
    }

    @Override
    protected String doInBackground(String... params) {
        String obra = "";
        String ws = params[0];

        try {
            URL url = new URL(ws);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                if (in != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    obra = br.readLine();
                }
                in.close();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return obra;
    }

    @Override
    protected void onPreExecute() {
        btnVerObra.setEnabled(false);
    }

    @Override
    protected void onPostExecute(String s) {
        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        String[] partes = s.split(";");
        String artista = partes[0];
        String obra = partes[1];
        String detalhes = partes[2];
        double lancemin = Double.parseDouble(partes[3]);
        String path = partes[5];

        txtObra.setText("Artista: " + artista +
                        "\nObra: " + obra +
                        "\nDetalhes: " + detalhes +
                        "\nLance Mínimo: " + nfBr.format(lancemin));

        txtPath.setText(path);

        asyncResposta.retornaDados(obra, lancemin);

        btnVerObra.setEnabled(true);
    }
}
