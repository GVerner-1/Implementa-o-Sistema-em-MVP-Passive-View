package ufes.especificacao_mvp.repositorio;

import java.util.ArrayList;
import java.util.List;
import ufes.especificacao_mvp.model.Categoria;

public class CategoriaRepositoryMock implements ICategoriaRepository{
    private List<Categoria> categorias = new ArrayList<>();
    private int proximoId = 1;
    
    @Override
    public void salvar(Categoria categoria){
        categoria.setId(proximoId++);
        categorias.add(categoria);
    }
    
    @Override
    public List<Categoria> buscarTodas(){
        return new ArrayList<>(categorias);
    }
    
    @Override
    public void atualizar(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == categoria.getId()){
                categorias.set(i, categoria);
                break;
            }  
        }
    }
    
    @Override
    public void excluir(Categoria categoria){
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId() == categoria.getId()) {
                categorias.remove(i);
                break;
            }
        }
    }
}
