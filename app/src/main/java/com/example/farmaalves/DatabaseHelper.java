package com.example.farmaalves;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "farmacia.db";
    public static final String TABLE_NAME = "usuario";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NOME";
    public static final String COL_3 = "CPF";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "TELEFONE";

    public static final String TABLE_MED = "medicamento";
    public static final String COL_6 = "ID";
    public static final String COL_7 = "MEDICAMENTO";
    public static final String COL_8 = "PRECO";

    public static final String TABLE_VENDA = "venda";
    public static final String COL_9 = "ID";
    public static final String COL_10 = "ID_CLIENTE";
    public static final String COL_11 = "ID_MEDICAMENTO";
    public static final String COL_12 = "PRECO";
    public static final String COL_13 = "NOME_MEDICAMENTO";
    public static final String COL_14 = "NOME_CLIENTE";
    public static final String COL_15 = "TELEFONE";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, CPF INTEGER, EMAIL TEXT, TELEFONE INTEGER )");
        db.execSQL("create table " + TABLE_MED +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, MEDICAMENTO TEXT, PRECO FLOAT )");
        db.execSQL("create table " + TABLE_VENDA +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, ID_CLIENTE INTEGER, ID_MEDICAMENTO INTEGER, PRECO FLOAT, NOME_MEDICAMENTO TEXT, NOME_CLIENTE TEXT, TELEFONE INTEGER," +
                " FOREIGN KEY (ID_CLIENTE) REFERENCES USUARIO (ID), FOREIGN KEY (ID_MEDICAMENTO) REFERENCES MEDICAMENTO (ID)) ");
      }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_MED);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_VENDA);
        onCreate(db);

    }

    //METODOS TABELA USUÁRIO

    public boolean insertData(String nome, String cpf, String email, String telefone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, cpf);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, telefone);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String nome, String cpf, String email, String telefone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, nome);
        contentValues.put(COL_3, cpf);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, telefone);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id } );
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }




    //METODOS TABELA MEDICAMENTO

    public boolean insertDataMed(String medicamento, float preco){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7, medicamento);
        contentValues.put(COL_8, preco);
        long result = db.insert(TABLE_MED, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllDataMed(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_MED, null);
        return res;
    }

    public boolean updateDataMed(String id, String medicamento, float preco) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, id);
        contentValues.put(COL_7, medicamento);
        contentValues.put(COL_8, preco);
        db.update(TABLE_MED, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteDataMed(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_MED, "ID = ?", new String[] {id});
    }



    //METODOS TABELA VENDA

    public boolean insertDataVenda(String  idCliente, String idMedicamento, float preco, String nomeMedicamento, String nomeCliente, String telefone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_10, idCliente);
        contentValues.put(COL_11, idMedicamento);
        contentValues.put(COL_12, preco);
        contentValues.put(COL_13, nomeMedicamento);
        contentValues.put(COL_14, nomeCliente);
        contentValues.put(COL_15, telefone);
        long result = db.insert(TABLE_VENDA, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllDataVenda(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_VENDA, null);
        return res;
    }

    public boolean updateDataVenda(String id, String idCliente, String idMedicamento, float preco, String nomeMedicamento, String nomeCliente, String telefone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_9, id);
        contentValues.put(COL_10, idCliente);
        contentValues.put(COL_11, idMedicamento);
        contentValues.put(COL_12, preco);
        contentValues.put(COL_13, nomeMedicamento);
        contentValues.put(COL_14, nomeCliente);
        contentValues.put(COL_15, telefone);
        db.update(TABLE_VENDA, contentValues, "ID = ?", new String[] { id } );
        return true;
    }

    public Integer deleteDataVenda(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_VENDA, "ID = ?", new String[] {id});
    }

    //METODOS DE BUSCA DAS INFORMAÇÕES DO CLIENTE E DO MEDICAMENTO PARA CADASTRAR VENDA
    public Cursor buscaCliente(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select " + COL_2 + "," + COL_5 + "," + COL_1 + " from " +TABLE_NAME  + " where ID=  " + id, null);
        return res;
    }

    public Cursor buscaMedicamento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =db.rawQuery("select " + COL_8 + "," + COL_7 + "," + COL_6 + " from " +TABLE_MED  + " where ID=  " + id, null);
        return res;
    }


}
