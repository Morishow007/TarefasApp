package com.bruno.tarefasapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.tarefasapp.R;
import com.bruno.tarefasapp.R.color;
import com.bruno.tarefasapp.model.Tarefa;

import java.util.List;

import static com.bruno.tarefasapp.R.drawable.ic_green_check_24;
import static com.bruno.tarefasapp.R.drawable.ic_red_warning_24;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {

    private List<Tarefa> listaTarefas;

    public TarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_tarefa_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa;
        TextView viewTarefa = holder.itemView.findViewById(R.id.textTarefaAdapter);
        int colorRed = color.red;
        int colorGreen = color.green;
        int colorDefault = color.white;
        tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());

        if(tarefa.getStatus() == 1){
            holder.itemView.setBackgroundResource(colorGreen);
            viewTarefa.setCompoundDrawablesWithIntrinsicBounds(0,0, ic_green_check_24,0);
        } else if(tarefa.getStatus() == 2) {
            holder.itemView.setBackgroundResource(colorRed);
            viewTarefa.setCompoundDrawablesWithIntrinsicBounds(0,0, ic_red_warning_24,0);
        } else if(tarefa.getStatus() == 0){
            holder.itemView.setBackgroundResource(colorDefault);
            viewTarefa.setCompoundDrawablesWithIntrinsicBounds(0,0, 0,0);
        }

    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tarefa = itemView.findViewById(R.id.textTarefaAdapter);

        }
    }


}
