package br.com.edecio.appleilaoonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtObra;
    private TextView txtPath;
    private Button btnVerObra;
    private ImageView imgObra;
    private Button btnDarLance;
    private String tituloObra;
    private double lanceMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtObra = (TextView) findViewById(R.id.txtObra);
        txtPath = (TextView) findViewById(R.id.txtPath);
        btnVerObra = (Button) findViewById(R.id.btnVerObra);
        imgObra = (ImageView) findViewById(R.id.imgObra);
        btnDarLance = (Button) findViewById(R.id.btnDarLance);

        btnVerObra.setOnClickListener(this);
        btnDarLance.setOnClickListener(this);

        AsyncResposta asyncResposta = new AsyncResposta() {
            @Override
            public void retornaDados(String titulo, double valor) {
                tituloObra = titulo;
                lanceMin = valor;
            }
        };

        BuscaLeilao buscaLeilao = new BuscaLeilao(txtObra, txtPath, btnVerObra, asyncResposta);
        buscaLeilao.execute("http://187.7.106.14/edecio/obradasemana.php");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnVerObra) {
            String path = txtPath.getText().toString();
            Picasso.with(this).load(path).into(imgObra);
            btnDarLance.setVisibility(View.VISIBLE);
        } else {
            // Toast.makeText(this, "Obra: " + tituloObra + " R$: " + lanceMin, Toast.LENGTH_LONG).show();

            Intent it = new Intent(this, RecebeLanceActivity.class);

            // define dados a serem passados
            it.putExtra("tituloObra", tituloObra);
            it.putExtra("lanceMin", lanceMin);

            startActivity(it);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuLista) {
            Toast.makeText(this, "Ok", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
