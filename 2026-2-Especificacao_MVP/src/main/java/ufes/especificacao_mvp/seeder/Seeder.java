package ufes.especificacao_mvp.seeder;

import java.time.LocalDate;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.HistoricoPreco;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;

public class Seeder {

    private ICategoriaRepository categoriaRepository;
    private IProdutoRepository produtoRepository;
    private IHistoricoPrecoRepository historicoRepository;

    public Seeder(ICategoriaRepository categoriaRepository, 
                  IProdutoRepository produtoRepository, 
                  IHistoricoPrecoRepository historicoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
        this.historicoRepository = historicoRepository;
    }

    public void popularBanco() {
        try {
            if (!categoriaRepository.buscarTodos().isEmpty()) {
                return;
            }

            Categoria catEletronicos = new Categoria(1, "Eletrônicos", 25.0);
            Categoria catAlimentos = new Categoria(2, "Alimentos", 15.0);
            Categoria catVestuario = new Categoria(3, "Vestuário", 50.0);

            categoriaRepository.salvar(catEletronicos);
            categoriaRepository.salvar(catAlimentos);
            categoriaRepository.salvar(catVestuario);

            Produto p1 = new Produto(1, "Smartphone", 1000.0, 1250.0, catEletronicos);
            Produto p2 = new Produto(2, "Arroz 5kg", 20.0, 23.0, catAlimentos);
            Produto p3 = new Produto(3, "Camiseta", 30.0, 45.0, catVestuario);

            produtoRepository.salvar(p1);
            produtoRepository.salvar(p2);
            produtoRepository.salvar(p3);

            LocalDate dataAntiga = LocalDate.now().minusDays(15);
            
            historicoRepository.salvar(new HistoricoPreco(dataAntiga, 1250.0, p1));
            historicoRepository.salvar(new HistoricoPreco(dataAntiga, 23.0, p2));
            historicoRepository.salvar(new HistoricoPreco(dataAntiga, 45.0, p3));

            System.out.println("Seeder executado com sucesso: Dados iniciais carregados!");

        } catch (Exception e) {
            System.err.println("Erro ao executar o Seeder: " + e.getMessage());
        }
    }
}