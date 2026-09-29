package ufes.especificacao_mvp.repositorio;

import java.util.List;
import ufes.especificacao_mvp.model.Categoria;

public interface ICategoriaRepository {
    void salvar(Categoria categoria);
    List<Categoria> buscarTodas();
    void atualizar(Categoria categoria);
    void excluir(Categoria categoria);
}
