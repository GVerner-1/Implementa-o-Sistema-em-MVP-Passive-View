package ufes.especificacao_mvp.presenter;

import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.*;

public class TelaPrincipalPresenter {
    static String numero(double valor) {
        return String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", valor);
    }

    static double lerNumero(String texto, String campo) {
        try {
            return Double.parseDouble(texto.trim().replace(',', '.'));
        } catch (NullPointerException | NumberFormatException ex) {
            throw new IllegalArgumentException("Informe um número válido para " + campo + ".");
        }
    }
    private final TelaPrincipal view;
    private final ProdutoService produtos;

    public TelaPrincipalPresenter(TelaPrincipal view, ProdutoService produtos) {
        this.view = view;
        this.produtos = produtos;
        view.getMiIncluirProduto().addActionListener(e ->
            new ProdutoInclusaoEdicaoPresenter(new ProdutoInclusaoEdicao(), produtos, null, null).exibeTela());
        view.getMiBuscarProdutos().addActionListener(e ->
            new BuscaProdutosPresenter(new BuscaProdutos(), produtos).exibeTela());
        view.getMiCategorias().addActionListener(e ->
            new CategoriaProdutosPresenter(new CategoriaProdutos(), produtos).exibeTela());
        view.getMiCalculoMargem().addActionListener(e ->
            new CalculoMargemLucroPresenter(new CalculoMargemLucro(), produtos).exibeTela());
    }

    public void exibeTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
