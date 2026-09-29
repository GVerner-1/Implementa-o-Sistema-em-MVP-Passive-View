package ufes.especificacao_mvp.repositorio;

import java.util.List;
import ufes.especificacao_mvp.model.HistoricoPreco;

public interface IHistoricoPrecoRepository {
    void salvar(HistoricoPreco historico);
    public List<HistoricoPreco> buscarTodos();
    void atualizar(HistoricoPreco historico);
    void excluir(HistoricoPreco historico);
}
