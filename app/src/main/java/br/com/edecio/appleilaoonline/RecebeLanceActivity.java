package br.com.edecio.appleilaoonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class RecebeLanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtObra;
    private TextView txtLanceMin;
    private TextView txtMensa;
    private EditText edtCliente;
    private EditText edtEmail;
    private EditText edtLance;
    private Button btnEnviar;
    private double lanceMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_lance);

        txtObra = (TextView) findViewById(R.id.txtObra);
        txtLanceMin = (TextView) findViewById(R.id.txtLanceMin);
        txtMensa = (TextView) findViewById(R.id.txtMensa);
        edtCliente = (EditText) findViewById(R.id.edtCliente);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtLance = (EditText) findViewById(R.id.edtLance);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(this);

        Intent it = getIntent();
        String tituloObra = it.getStringExtra("tituloObra");
        lanceMin = it.getDoubleExtra("lanceMin", -1);

        txtObra.setText("Obra em Leilão: " + tituloObra);

        NumberFormat nfBr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        txtLanceMin.setText("Lance Mínimo: " + nfBr.format(lanceMin));
    }

    @Override
    public void onClick(View v) {

        String cliente = edtCliente.getText().toString();
        String email = edtEmail.getText().toString();
        String lance = edtLance.getText().toString();

        if (cliente.trim().isEmpty() || email.trim().isEmpty() || lance.trim().isEmpty()) {
            Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show();
            edtCliente.requestFocus();
            return;
        }

        double valLance = Double.parseDouble(lance);

        if (valLance < lanceMin) {
            Toast.makeText(this, "Lance inferior ao mínimo...", Toast.LENGTH_LONG).show();
            edtLance.requestFocus();
            return;
        }

        EnviaLance enviaLance = new EnviaLance(txtMensa);
        enviaLance.execute("http://187.7.106.14/edecio/gravalance.php", cliente, email, lance);

    }
}
