package ufes.especificacao_mvp.presenter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.BuscaProdutos;
import ufes.especificacao_mvp.view.HistoricoPrecosProduto;
import ufes.especificacao_mvp.view.ProdutoInclusaoEdicao;
import ufes.especificacao_mvp.view.ProdutoVisualizacao;

public class BuscaProdutosPresenter {
    private final BuscaProdutos view;
    private final ProdutoService produtos;
    private List<Produto> resultado;

    public BuscaProdutosPresenter(BuscaProdutos view, ProdutoService produtos) {
        this.view = view;
        this.produtos = produtos;
        view.getTabelaProdutos().setModel(new DefaultTableModel(
            new String[]{"Nome do produto", "Preço de custo", "Categoria", "Margem de lucro (%)", "Preço de venda"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        });
        view.getBtnVisualizar().setEnabled(false);
        view.getTabelaProdutos().getSelectionModel().addListSelectionListener(e ->
            view.getBtnVisualizar().setEnabled(view.getTabelaProdutos().getSelectedRow() >= 0));
        view.getBtnBuscar().addActionListener(e -> carregar());
        view.getBtnNovo().addActionListener(e ->
            new ProdutoInclusaoEdicaoPresenter(new ProdutoInclusaoEdicao(), produtos, null, this::carregar).exibeTela());
        view.getBtnVisualizar().addActionListener(e -> visualizar());
        view.getBtnFechar().addActionListener(e -> view.dispose());
        carregar();
    }

    private void carregar() {
        try {
            resultado = produtos.buscar(view.getTxtTermoBusca().getText(), view.getJComboBox1().getSelectedIndex() == 1);
            DefaultTableModel model = (DefaultTableModel) view.getTabelaProdutos().getModel();
            model.setRowCount(0);
            for (Produto p : resultado) {
                boolean calculado = p.getPercentualLucroCalculado() != null;
                model.addRow(new Object[]{p.getNome(), TelaPrincipalPresenter.numero(p.getPrecoCusto()),
                    p.getCategoria().getNome(), calculado ? TelaPrincipalPresenter.numero(p.getPercentualLucroCalculado()) : "",
                    calculado ? TelaPrincipalPresenter.numero(p.getPrecoVenda()) : ""});
            }
            view.getBtnVisualizar().setEnabled(false);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro na busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void visualizar() {
        int linha = view.getTabelaProdutos().getSelectedRow();
        if (linha < 0 || linha >= resultado.size()) return;
        Produto produto = resultado.get(linha);
        ProdutoVisualizacao detalhes = new ProdutoVisualizacao();
        detalhes.getJTextField1().setEditable(false);
        detalhes.getJTextField2().setEditable(false);
        detalhes.getJTextField3().setEditable(false);
        detalhes.getJTextField4().setEditable(false);
        detalhes.getJComboBox1().setEnabled(false);
        detalhes.getJButton1().addActionListener(e -> detalhes.dispose());
        detalhes.getJButton2().addActionListener(e -> new ProdutoInclusaoEdicaoPresenter(
            new ProdutoInclusaoEdicao(), produtos, produto, () -> {
                mostrarProduto(detalhes, produto);
                carregar();
            }).exibeTela());
        detalhes.getJButton3().addActionListener(e -> mostrarHistorico(produto));
        mostrarProduto(detalhes, produto);
        detalhes.setLocationRelativeTo(view);
        detalhes.setVisible(true);
    }

    private void mostrarProduto(ProdutoVisualizacao detalhes, Produto produto) {
        detalhes.getJTextField1().setText(produto.getNome());
        detalhes.getJTextField2().setText(TelaPrincipalPresenter.numero(produto.getPrecoCusto()));
        detalhes.getJComboBox1().removeAllItems();
        detalhes.getJComboBox1().addItem(produto.getCategoria().getNome());
        detalhes.getJTextField3().setText(produto.getPercentualLucroCalculado() == null ?
            "" : TelaPrincipalPresenter.numero(produto.getPercentualLucroCalculado()));
        detalhes.getJTextField4().setText(produto.getPercentualLucroCalculado() == null ?
            "" : TelaPrincipalPresenter.numero(produto.getPrecoVenda()));
    }

    private void mostrarHistorico(Produto produto) {
        HistoricoPrecosProduto tela = new HistoricoPrecosProduto();
        tela.getJTextField2().setText(produto.getNome());
        tela.getJTextField1().setText(produto.getCategoria().getNome());
        tela.getJTextField2().setEditable(false);
        tela.getJTextField1().setEditable(false);
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Data", "Percentual de lucro (%)", "Preço de venda"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        produtos.historico(produto).forEach(h -> model.addRow(new Object[]{
            h.getData().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")),
            TelaPrincipalPresenter.numero(h.getPercentualLucro()),
            TelaPrincipalPresenter.numero(h.getValorVenda())
        }));
        tela.getJTable1().setModel(model);
        tela.getJButton1().addActionListener(e -> tela.dispose());
        tela.setLocationRelativeTo(view);
        tela.setVisible(true);
    }

    public void exibeTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
