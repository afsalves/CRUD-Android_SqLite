package com.example.farmaalves;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class VendaActivity extends AppCompatActivity { DatabaseHelper myDb;
    EditText editText_id_venda, editText_id_cliente, editText_id_medicamento, editText_Preco_Venda, editText_nome_med, editText_nome_cli, editText_telefone_cli;
    Button btnCadastrar;
    Button btnVerTodas;
    Button btnApagar;
    Button btnBuscaCliente;
    Button btnBuscaMed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);
        myDb = new DatabaseHelper (this);

        editText_id_venda = (EditText) findViewById(R.id.editText_id_venda);
        editText_id_cliente = (EditText) findViewById(R.id.editText_id_cliente);
        editText_id_medicamento = (EditText) findViewById(R.id.editText_id_medicamento);
        editText_Preco_Venda = (EditText) findViewById(R.id.editText_Preco_Venda);
        editText_nome_med = (EditText) findViewById(R.id.editText_nome_med);
        editText_nome_cli = (EditText) findViewById(R.id.editText_nome_cli);
        editText_telefone_cli = (EditText) findViewById(R.id.editText_telefone_cli);
        btnCadastrar  = (Button) findViewById(R.id.button_add_venda);
        btnVerTodas = (Button) findViewById(R.id.button_VerTodos_Venda);
        btnApagar = (Button) findViewById(R.id.button_Apagar_Venda);
        btnBuscaCliente = (Button) findViewById(R.id.button_Busca_Cliente);
        btnBuscaMed = (Button) findViewById(R.id.button_Busca_Med);
        Salvar();
        VerTodos();
        ApagarDados();
        BuscarCliente();
        BuscarMedicamento();
    }

    public void ApagarDados(){
        btnApagar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteDataVenda(editText_id_venda.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(VendaActivity.this, "Dados Apagados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(VendaActivity.this,"Dados não Apagados", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public  void Salvar() {
        btnCadastrar.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted =  myDb.insertDataVenda(editText_id_cliente.getText().toString(),
                                editText_id_medicamento.getText().toString(), (Float.parseFloat(editText_Preco_Venda.getText().toString())), editText_nome_med.getText().toString(), editText_nome_cli.getText().toString(), editText_telefone_cli.getText().toString());
                        if (isInserted == true)
                            Toast.makeText(VendaActivity.this, "Dados Salvos", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(VendaActivity.this,"Dados não salvos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void VerTodos(){
        btnVerTodas.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllDataVenda();
                        if (res.getCount() == 0) {
                            //Exibir mensagem
                            showMessage ("Erro", "Nada Encontrado");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Id Cliente :"+ res.getString(1)+"\n");
                            buffer.append("Id Medicamento :"+ res.getString(2)+"\n");
                            buffer.append("Preco :"+ res.getString(3)+"\n");
                            buffer.append("Nome Medicamento :"+ res.getString(4)+"\n");
                            buffer.append("Nome Cliente :"+ res.getString(5)+"\n");
                            buffer.append("Telefone :"+ res.getString(6)+"\n\n");
                        }

                        //Exibir todos os dados
                        showMessage("Dados", buffer.toString());
                    }
                }
        );

    }

    public void BuscarCliente(){
        btnBuscaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.buscaCliente(editText_id_cliente.getText().toString());
                if (res.getCount() == 0) {
                    //Exibir mensagem
                    showMessage ("Erro", "Nada Encontrado");
                    return;
                }
                while (res.moveToNext()){

                    editText_nome_cli.setText(res.getString(0));
                    editText_telefone_cli.setText(res.getString(1));


                }
            }
        });

    }

    public void BuscarMedicamento(){
        btnBuscaMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res = myDb.buscaMedicamento(editText_id_medicamento.getText().toString());
                if (res.getCount() == 0) {
                    //Exibir mensagem
                    showMessage ("Erro", "Nada Encontrado");
                    return;
                }
                while (res.moveToNext()){

                    editText_Preco_Venda.setText(res.getString(0));
                     editText_nome_med.setText(res.getString(1));
                }
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
