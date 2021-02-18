package com.bruno.tarefasapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bruno.tarefasapp.model.Tarefa;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDao {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("status", tarefa.getStatus());

        try {
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv );
            Log.i("INFO", "Tarefa salva com sucesso");
        } catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("status", tarefa.getStatus());

        try {
            String[] args= {tarefa.getId().toString()};
            escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?" , args );
            Log.i("INFO", "Tarefa atualizada com sucesso");
        } catch (Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try {
            String[] args= {tarefa.getId().toString()};

            escreve.delete(DbHelper.TABELA_TAREFAS,"id=?" , args );
            Log.i("INFO", "Tarefa excluida com sucesso");
        } catch (Exception e){
            Log.e("INFO", "Erro ao excluida tarefa" + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()){
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            int status = c.getInt(c.getColumnIndex("status"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setStatus(status);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
