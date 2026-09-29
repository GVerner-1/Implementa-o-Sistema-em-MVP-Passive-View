package ufes.especificacao_mvp.seeder;

import java.time.LocalDate;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.HistoricoPreco;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;
import ufes.especificacao_mvp.servico.ProdutoService;

public class Seeder {
    private final ICategoriaRepository categorias;
    private final IProdutoRepository produtos;
    private final IHistoricoPrecoRepository historicos;

    public Seeder(ICategoriaRepository categorias, IProdutoRepository produtos, IHistoricoPrecoRepository historicos) {
        this.categorias = categorias;
        this.produtos = produtos;
        this.historicos = historicos;
    }

    public void popularBanco() {
        if (!categorias.buscarTodos().isEmpty()) return;
        Categoria educacao = categoria("Educação", 25);
        Categoria papelaria = categoria("Papelaria", 30);
        Categoria alimentacao = categoria("Alimentação", 22);
        Categoria lazer = categoria("Lazer", 35);
        Categoria entretenimento = categoria("Entretenimento", 40);
        Categoria higiene = categoria("Higiene", 28);
        Categoria limpeza = categoria("Limpeza", 25);

        produto("Livro didático", 45, educacao);
        produto("Livro paradidático", 30, educacao);
        produto("Mochila escolar", 70, educacao);
        produto("Caderno universitário", 16, papelaria);
        produto("Lápis grafite HB", 1.20, papelaria);
        produto("Caneta esferográfica azul", 2.20, papelaria);
        produto("Borracha branca", 1, papelaria);
        produto("Apontador com depósito", 3.50, papelaria);
        produto("Jogo de tabuleiro", 55, lazer);
        produto("Bola recreativa", 40, lazer);
        produto("Quebra-cabeça 500 peças", 35, lazer);
        produto("Fone de ouvido", 48, entretenimento);
        produto("Caixa de som portátil", 80, entretenimento);
        produto("Revista de passatempos", 12, entretenimento);
        produto("Biscoito integral", 5.50, alimentacao);
        produto("Suco de uva 1 L", 9, alimentacao);
        produto("Barra de cereal", 3.20, alimentacao);
        produto("Sabonete", 2.80, higiene);
        produto("Creme dental", 5.50, higiene);
        produto("Detergente líquido", 2.60, limpeza);
        produto("Esponja multiuso", 1.70, limpeza);
    }

    private Categoria categoria(String nome, double margem) {
        Categoria c = new Categoria(0, nome, margem);
        categorias.salvar(c);
        return c;
    }

    private void produto(String nome, double custo, Categoria categoria) {
        double margem = categoria.getPercentualLucro();
        double venda = ProdutoService.arredondar(custo * (1 + margem / 100.0));
        Produto p = new Produto(0, nome, custo, venda, categoria);
        p.setPercentualLucroCalculado(margem);
        produtos.salvar(p);
        historicos.salvar(new HistoricoPreco(LocalDate.now().minusDays(10), margem, venda, p));
    }
}
