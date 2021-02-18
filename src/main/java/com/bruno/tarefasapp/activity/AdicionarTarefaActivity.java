package com.bruno.tarefasapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bruno.tarefasapp.R;
import com.bruno.tarefasapp.helper.TarefaDAO;
import com.bruno.tarefasapp.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;
    private RadioButton urgent, completed, padrao;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);
        editTarefa = findViewById(R.id.textTarefa);
        tarefaAtual = (Tarefa)getIntent().getSerializableExtra("tarefaSelecionada");

        radioGroup = findViewById(R.id.radioGroupADD);

        urgent = findViewById(R.id.radioUrgent);
        completed = findViewById(R.id.radioCompleted);
        padrao = findViewById((R.id.radioPadrao));



        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    public void radioSET(Tarefa tarefa){

        if(radioGroup.getCheckedRadioButtonId() == -1){
            tarefa.setStatus(tarefaAtual.getStatus());

        }else if(urgent.isChecked()){
            tarefa.setStatus(2);
        } else if(completed.isChecked()){
            tarefa.setStatus(1);
        } else if (padrao.isChecked()){
            tarefa.setStatus(0);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.itemSalvar){

            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            String nomeTarefa = editTarefa.getText().toString();

          if(tarefaAtual != null){
              if(!nomeTarefa.isEmpty()){

                  Tarefa tarefa = new Tarefa();

                  tarefa.setNomeTarefa(nomeTarefa);
                  tarefa.setId(tarefaAtual.getId());
                  radioSET(tarefa);

                  if(tarefaDAO.atualizar(tarefa)){
                      finish();
                      Toast.makeText(getApplicationContext(),
                              "Sucesso ao atualizar tarefa",
                              Toast.LENGTH_SHORT).show();
                  } else {Toast.makeText(getApplicationContext(),
                          "Erro ao atualizar tarefa",
                          Toast.LENGTH_SHORT).show();
                  }
              }

          }else{

              if(!nomeTarefa.isEmpty()){
                  Tarefa tarefa = new Tarefa();
                  tarefa.setNomeTarefa(nomeTarefa);
                  if(radioGroup.getCheckedRadioButtonId() == -1){
                      tarefa.setStatus(0);
                  }else if(urgent.isChecked()){
                      tarefa.setStatus(2);
                  } else if(completed.isChecked()){
                      tarefa.setStatus(1);
                  } else if (padrao.isChecked()){
                      tarefa.setStatus(0);
                  }


                  if(tarefaDAO.salvar(tarefa)){
                      finish();
                      Toast.makeText(getApplicationContext(),
                              "Sucesso ao salvar tarefa",
                              Toast.LENGTH_SHORT).show();
                  } else {
                      Toast.makeText(getApplicationContext(),
                              "Sucesso ao salvar tarefa",
                              Toast.LENGTH_SHORT).show();
                  }

              }
          }
        }
        return super.onOptionsItemSelected(item);
    }
}