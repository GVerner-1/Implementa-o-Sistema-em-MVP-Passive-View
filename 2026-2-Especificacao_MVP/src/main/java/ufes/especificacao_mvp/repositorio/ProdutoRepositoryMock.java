package ufes.especificacao_mvp.repositorio;

import java.util.ArrayList;
import java.util.List;
import ufes.especificacao_mvp.model.Produto;

public class ProdutoRepositoryMock implements IProdutoRepository{
    private List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;
    
    @Override
    public void salvar(Produto produto){
        produto.setId(proximoId++);
        produtos.add(produto);
    }
    
    @Override
    public List<Produto> buscarTodas(){
        return new ArrayList<>(produtos);
    }
    
    @Override
    public void atualizar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()){
                produtos.set(i, produto);
                break;
            }  
        }
    }
    
    @Override
    public void excluir(Produto produto){
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.remove(i);
                break;
            }
        }
    }
}
