package ufes.especificacao_mvp.servico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.HistoricoPreco;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;

public class ProdutoService {
    
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public ProdutoService(IProdutoRepository produtoRepository, IHistoricoPrecoRepository historicoRepository) {
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
    }

    public void calcularPrecoVenda(Produto produto) throws Exception {
        List<HistoricoPreco> historicos = historicoRepository.buscarTodos();
        
        for (int i = 0; i < historicos.size(); i++) {
            HistoricoPreco hp = historicos.get(i);
            
            if (hp.getProduto().getId() == produto.getId()) {
                long diasDecorridos = ChronoUnit.DAYS.between(hp.getData(), LocalDate.now());
                
                if (diasDecorridos < 10) {
                    throw new Exception("Operação bloqueada: A última alteração de preço ocorreu há menos de 10 dias.");
                }
            }
        }

        double precoCusto = produto.getPrecoCusto();
        double percentualLucro = produto.getCategoria().getPercentualLucro();
        
        double precoVendaCalculado = precoCusto * (1 + (percentualLucro / 100.0));
        precoVendaCalculado = Math.round(precoVendaCalculado * 100.0) / 100.0;
        
        produto.setPrecoVenda(precoVendaCalculado);
        produtoRepository.atualizar(produto);

        HistoricoPreco novoHistorico = new HistoricoPreco(LocalDate.now(), precoVendaCalculado, produto);
        historicoRepository.salvar(novoHistorico);
    }

    public boolean validarExclusaoCategoria(Categoria categoria, List<Produto> todosProdutos) {
        for (int i = 0; i < todosProdutos.size(); i++) {
            Produto p = todosProdutos.get(i);
            if (p.getCategoria().getId() == categoria.getId()) {
                return false; 
            }
        }
        return true;
    }
}