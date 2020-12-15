package com.example.farmaalves;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsuarioActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editNome, editCpf, editEmail, editTelefone, editTextId;
    Button btnSalvar;
    Button btnVerTodos;
    Button btnAtualizar;
    Button btnApagar;
    Button btnMedicamentos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        myDb = new DatabaseHelper (this);

        editNome = (EditText) findViewById(R.id.editText_nome);
        editCpf = (EditText) findViewById(R.id.editText_cpf);
        editEmail = (EditText) findViewById(R.id.editText_email);
        editTelefone = (EditText) findViewById(R.id.editText_telefone);
        editTextId = (EditText) findViewById(R.id.editText_id);
        btnSalvar = (Button) findViewById(R.id.button_add);
        btnVerTodos  = (Button) findViewById(R.id.button_VerTodos);
        btnAtualizar = (Button) findViewById(R.id.button_Atualizar);
        btnApagar = (Button) findViewById(R.id.button_Apagar);
        btnMedicamentos= (Button) findViewById(R.id.button_Lista_Med);
        Salvar();
        VerTodos();
        AtualizarDados();
        ApagarDados();
        Medicamentos();
    }

    public void ApagarDados(){
        btnApagar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if (deletedRows > 0)
                            Toast.makeText(UsuarioActivity.this, "Dados Apagados", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(UsuarioActivity.this,"Dados não Apagados", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void AtualizarDados(){
        btnAtualizar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData (editTextId.getText().toString(), editNome.getText().toString(),
                                editCpf.getText().toString(),
                                editEmail.getText().toString(),
                                editTelefone.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(UsuarioActivity.this, "Dados Atualizados", Toast.LENGTH_LONG).show();
                       else
                            Toast.makeText(UsuarioActivity.this,"Dados não Atualizados", Toast.LENGTH_LONG).show();

                        }
                }
        );

    }

    public  void Salvar() {
        btnSalvar.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                       boolean isInserted =  myDb.insertData(editNome.getText().toString(),
                               editCpf.getText().toString(),
                                editEmail.getText().toString(),
                               editTelefone.getText().toString());

                       if (isInserted == true)
                           Toast.makeText(UsuarioActivity.this, "Dados Salvos", Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(UsuarioActivity.this,"Dados não salvos", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void VerTodos(){
        btnVerTodos.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            //Exibir mensagem
                            showMessage ("Erro", "Nada Encontrado");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Nome :"+ res.getString(1)+"\n");
                            buffer.append("Cpf :"+ res.getString(2)+"\n");
                            buffer.append("Email :"+ res.getString(3)+"\n");
                            buffer.append("Telefone :"+ res.getString(4)+"\n\n");
                        }

                        //Exibir todos os dados
                        showMessage("Dados", buffer.toString());
                    }
                }
        );

        }

    public void Medicamentos(){
        btnMedicamentos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioActivity.this, MedicamentoActivity.class);
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

