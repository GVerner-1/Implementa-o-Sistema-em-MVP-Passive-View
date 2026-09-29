package ufes.especificacao_mvp.presenter;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.CategoriaProdutos;

public class CategoriaProdutosPresenter {
    private enum Modo { VISUALIZACAO, INCLUSAO, EDICAO }
    private final CategoriaProdutos view;
    private final ProdutoService categorias;
    private List<Categoria> lista;
    private Categoria selecionada;
    private Modo modo = Modo.VISUALIZACAO;

    public CategoriaProdutosPresenter(CategoriaProdutos view, ProdutoService categorias) {
        this.view = view;
        this.categorias = categorias;
        view.getJTable1().setModel(new DefaultTableModel(new String[]{"Categoria", "Percentual de lucro (%)"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        });
        view.getJTable1().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && modo == Modo.VISUALIZACAO) selecionar();
        });
        view.getBtnNovo().addActionListener(e -> {
            selecionada = null;
            view.getJTable1().clearSelection();
            view.getTxtNomeCategoria().setText("");
            view.getTxtPercentualLucro().setText("");
            mudarModo(Modo.INCLUSAO);
        });
        view.getBtnEditar().addActionListener(e -> mudarModo(Modo.EDICAO));
        view.getBtnCancelar().addActionListener(e -> {
            mudarModo(Modo.VISUALIZACAO);
            apresentarSelecao();
        });
        view.getBtnSalvar().addActionListener(e -> salvar());
        view.getBtnExcluir().addActionListener(e -> excluir());
        view.getBtnFechar().addActionListener(e -> view.dispose());
        carregar(null);
    }

    private void carregar(Integer idSelecionado) {
        lista = categorias.buscarCategorias();
        DefaultTableModel model = (DefaultTableModel) view.getJTable1().getModel();
        model.setRowCount(0);
        for (Categoria c : lista) model.addRow(new Object[]{c.getNome(), TelaPrincipalPresenter.numero(c.getPercentualLucro())});
        int indice = -1;
        if (idSelecionado != null) for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == idSelecionado) { indice = i; break; }
        }
        if (indice >= 0) {
            selecionada = lista.get(indice);
            view.getJTable1().setRowSelectionInterval(indice, indice);
            apresentarSelecao();
        }
        else {
            selecionada = null;
            view.getJTable1().clearSelection();
            apresentarSelecao();
        }
        mudarModo(Modo.VISUALIZACAO);
    }

    private void selecionar() {
        int linha = view.getJTable1().getSelectedRow();
        selecionada = linha >= 0 && linha < lista.size() ? lista.get(linha) : null;
        apresentarSelecao();
        mudarModo(Modo.VISUALIZACAO);
    }

    private void apresentarSelecao() {
        view.getTxtNomeCategoria().setText(selecionada == null ? "" : selecionada.getNome());
        view.getTxtPercentualLucro().setText(selecionada == null ? "" : TelaPrincipalPresenter.numero(selecionada.getPercentualLucro()));
    }

    private void mudarModo(Modo novo) {
        modo = novo;
        boolean editavel = novo != Modo.VISUALIZACAO;
        view.getTxtNomeCategoria().setEditable(editavel);
        view.getTxtPercentualLucro().setEditable(editavel);
        view.getJTable1().setEnabled(!editavel);
        view.getBtnNovo().setEnabled(!editavel);
        view.getBtnEditar().setEnabled(!editavel && selecionada != null);
        view.getBtnExcluir().setEnabled(!editavel && selecionada != null);
        view.getBtnFechar().setEnabled(!editavel);
        view.getBtnSalvar().setEnabled(editavel);
        view.getBtnCancelar().setEnabled(editavel);
        view.getLblModo().setText("Modo: " + (novo == Modo.INCLUSAO ? "Inclusão" :
            novo == Modo.EDICAO ? "Edição" : "Visualização"));
    }

    private void salvar() {
        try {
            String nome = view.getTxtNomeCategoria().getText();
            double percentual = TelaPrincipalPresenter.lerNumero(view.getTxtPercentualLucro().getText(), "percentual de lucro");
            Integer id = modo == Modo.EDICAO ? selecionada.getId() : null;
            if (modo == Modo.INCLUSAO) categorias.incluirCategoria(nome, percentual);
            else if (modo == Modo.EDICAO) categorias.atualizarCategoria(selecionada, nome, percentual);
            else return;
            if (id == null) id = categorias.buscarCategorias().stream()
                .filter(c -> c.getNome().equals(nome.trim())).findFirst().orElseThrow().getId();
            carregar(id);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar categoria: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluir() {
        if (selecionada == null) return;
        if (JOptionPane.showConfirmDialog(view, "Excluir a categoria " + selecionada.getNome() + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            categorias.excluirCategoria(selecionada);
            carregar(null);
            JOptionPane.showMessageDialog(view, "Categoria excluída com sucesso.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Exclusão bloqueada", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro ao excluir categoria: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
