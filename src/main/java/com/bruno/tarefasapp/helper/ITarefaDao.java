package com.bruno.tarefasapp.helper;

import com.bruno.tarefasapp.model.Tarefa;

import java.util.List;

public interface ITarefaDao {

    public boolean salvar(Tarefa tarefa);

    public boolean atualizar(Tarefa tarefa);

    public boolean deletar(Tarefa tarefa);

    public List<Tarefa> listar();

}
