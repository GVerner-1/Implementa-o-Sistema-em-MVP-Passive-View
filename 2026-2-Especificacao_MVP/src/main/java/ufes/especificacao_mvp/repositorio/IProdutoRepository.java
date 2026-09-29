package ufes.especificacao_mvp.repositorio;

import java.util.List;
import ufes.especificacao_mvp.model.Produto;

public interface IProdutoRepository {
    void salvar(Produto produto);
    List<Produto> buscarTodas();
    void atualizar(Produto produto);
    void excluir(Produto produto);
}
