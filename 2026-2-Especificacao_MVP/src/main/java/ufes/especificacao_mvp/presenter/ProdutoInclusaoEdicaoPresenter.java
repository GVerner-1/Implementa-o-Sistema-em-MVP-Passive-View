package ufes.especificacao_mvp.presenter;

import javax.swing.JOptionPane;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.ProdutoInclusaoEdicao;

public class ProdutoInclusaoEdicaoPresenter {
    private final ProdutoInclusaoEdicao view;
    private final ProdutoService produtos;
    private final Produto editando;
    private final Runnable aposSalvar;

    public ProdutoInclusaoEdicaoPresenter(ProdutoInclusaoEdicao view, ProdutoService produtos,
            Produto editando, Runnable aposSalvar) {
        this.view = view;
        this.produtos = produtos;
        this.editando = editando;
        this.aposSalvar = aposSalvar;
        view.getJTextField3().setEditable(false);
        view.getJTextField4().setEditable(false);
        view.getCbCategorias().removeAllItems();
        produtos.buscarCategorias().forEach(c -> view.getCbCategorias().addItem(c.getNome()));
        view.getCbCategorias().setSelectedIndex(-1);
        if (editando != null) {
            view.setTitle("Produto - Edição");
            view.getTxtNome().setText(editando.getNome());
            view.getTxtValor().setText(TelaPrincipalPresenter.numero(editando.getPrecoCusto()));
            view.getCbCategorias().setSelectedItem(editando.getCategoria().getNome());
            if (editando.getPercentualLucroCalculado() != null) {
                view.getJTextField3().setText(TelaPrincipalPresenter.numero(editando.getPercentualLucroCalculado()));
                view.getJTextField4().setText(TelaPrincipalPresenter.numero(editando.getPrecoVenda()));
            }
        }
        view.getBtnSalvar().addActionListener(e -> salvar());
        view.getBtnCancelar().addActionListener(e -> view.dispose());
    }

    private void salvar() {
        try {
            String nome = view.getTxtNome().getText();
            double custo = TelaPrincipalPresenter.lerNumero(view.getTxtValor().getText(), "preço de custo");
            Categoria categoria = produtos.buscarCategorias().stream()
                .filter(c -> c.getNome().equals(view.getCbCategorias().getSelectedItem()))
                .findFirst().orElse(null);
            if (editando == null) produtos.incluir(nome, custo, categoria);
            else produtos.atualizar(editando, nome, custo, categoria);
            JOptionPane.showMessageDialog(view, "Produto salvo com sucesso.");
            view.dispose();
            if (aposSalvar != null) aposSalvar.run();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
