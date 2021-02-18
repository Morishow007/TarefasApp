package com.bruno.tarefasapp.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bruno.tarefasapp.R;
import com.bruno.tarefasapp.activity.AdicionarTarefaActivity;
import com.bruno.tarefasapp.adapter.TarefaAdapter;
import com.bruno.tarefasapp.helper.DbHelper;
import com.bruno.tarefasapp.helper.RecyclerItemClickListener;
import com.bruno.tarefasapp.helper.TarefaDAO;
import com.bruno.tarefasapp.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerListaTarefas;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerListaTarefas = findViewById(R.id.recyclerListaTarefas);



        recyclerListaTarefas.addOnItemTouchListener(
                    new RecyclerItemClickListener(
                            getApplicationContext(),
                            recyclerListaTarefas,
                            new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    Tarefa tarefaSelecionada = listaTarefas.get(position);
                                    Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                                    intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                                    startActivity( intent );

                                }

                                @Override
                                public void onLongItemClick(View view, int position) {

                                    tarefaSelecionada = listaTarefas.get(position);

                                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                    dialog.setTitle("Confirmar exclusao");
                                    dialog.setMessage("Deseja excluir a tarefa "
                                            + tarefaSelecionada.getNomeTarefa()+" ?");
                                    dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            TarefaDAO  tarefaDAO = new TarefaDAO(getApplicationContext());
                                            if(tarefaDAO.deletar(tarefaSelecionada)){

                                                carregarListaTarefas();
                                                Toast.makeText(getApplicationContext(),
                                                        "Sucesso ao excluir tarefa",
                                                        Toast.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(getApplicationContext(),
                                                        "Erro ao excluir tarefa",
                                                        Toast.LENGTH_SHORT).show();
                                            };

                                        }
                                    });
                                    dialog.setNegativeButton("Nao", null);
                                    dialog.create();
                                    dialog.show();

                                }

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                }
                            }
                    )
        );

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

            }
        });
    }

    public void  carregarListaTarefas(){

        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listaTarefas = tarefaDAO.listar();
        tarefaAdapter = new TarefaAdapter(listaTarefas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerListaTarefas.setLayoutManager(layoutManager);
        recyclerListaTarefas.setHasFixedSize(true);
        recyclerListaTarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerListaTarefas.setAdapter(tarefaAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}