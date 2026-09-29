package ufes.especificacao_mvp.repositorio;

import java.util.ArrayList;
import java.util.List;
import ufes.especificacao_mvp.model.HistoricoPreco;

public class HistoricoPrecoRepositoryMock implements IHistoricoPrecoRepository{
    private List<HistoricoPreco> historicos = new ArrayList<>();
    private int proximoId = 1;
    
    @Override
    public void salvar(HistoricoPreco historico){
        historico.setId(proximoId++);
        historicos.add(historico);
    }
    
    @Override
    public List<HistoricoPreco> buscarTodos(){
        return new ArrayList<>(historicos);
    }
    
    @Override
    public void atualizar(HistoricoPreco historico) {
        for (int i = 0; i < historicos.size(); i++) {
            if (historicos.get(i).getId() == historico.getId()){
                historicos.set(i, historico);
                break;
            }  
        }
    }
    
    @Override
    public void excluir(HistoricoPreco historico){
        for (int i = 0; i < historicos.size(); i++) {
            if (historicos.get(i).getId() == historico.getId()) {
                historicos.remove(i);
                break;
            }
        }
    }
}
