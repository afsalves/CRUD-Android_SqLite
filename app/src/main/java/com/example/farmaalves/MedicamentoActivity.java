package com.example.farmaalves;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MedicamentoActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editMedicamento, editPreco, editTextId;
    Button btnSalvar;
    Button btnVerTodos;
    Button btnAtualizar;
    Button btnApagar;
    Button btnVendas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        myDb = new DatabaseHelper (this);

        editMedicamento = (EditText) findViewById(R.id.editText_NomeMedicamento);
        editPreco = (EditText) findViewById(R.id.editText_Preco);
        editTextId = (EditText) findViewById(R.id.editText_id);
        btnSalvar = (Button) findViewById(R.id.button_Add_Med);
        btnVerTodos  = (Button) findViewById(R.id.button_VerTodos_Med);
        btnAtualizar = (Button) findViewById(R.id.button_Atualizar_Med);
        btnApagar = (Button) findViewById(R.id.button_Apagar_Med);
        btnVendas = (Button) findViewById(R.id.button_Lista_Vendas);
        Salvar();
        VerTodos();
        AtualizarDados();
        ApagarDados();
        Vendas();
    }

    public void ApagarDados(){
        btnApagar.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteDataMed(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(MedicamentoActivity.this, "Dados Apagados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MedicamentoActivity.this,"Dados não Apagados", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void AtualizarDados(){
        btnAtualizar.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateDataMed (editTextId.getText().toString(), editMedicamento.getText().toString(),
                                (Float.parseFloat(editPreco.getText().toString())));
                        if (isUpdate == true)
                            Toast.makeText(MedicamentoActivity.this, "Dados Atualizados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MedicamentoActivity.this,"Dados não Atualizados", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

    public  void Salvar() {
        btnSalvar.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted =  myDb.insertDataMed(editMedicamento.getText().toString(),
                                (Float.parseFloat(editPreco.getText().toString())));

                        if (isInserted == true)
                            Toast.makeText(MedicamentoActivity.this, "Dados Salvos", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MedicamentoActivity.this,"Dados não salvos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void VerTodos(){
        btnVerTodos.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllDataMed();
                        if (res.getCount() == 0) {
                            //Exibir mensagem
                            showMessage ("Erro", "Nada Encontrado");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Medicamento :"+ res.getString(1)+"\n");
                            buffer.append("Preco :"+ res.getString(2)+"\n\n");
                        }

                        //Exibir todos os dados
                        showMessage("Dados", buffer.toString());
                    }
                }
        );

    }

    public void Vendas(){
        btnVendas.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicamentoActivity.this, VendaActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMessage (String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}



