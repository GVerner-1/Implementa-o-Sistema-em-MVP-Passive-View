package ufes.especificacao_mvp.presenter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.CalculoMargemLucro;

public class CalculoMargemLucroPresenter {
    private final CalculoMargemLucro view;
    private final ProdutoService produtos;
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withResolverStyle(ResolverStyle.STRICT);

    public CalculoMargemLucroPresenter(CalculoMargemLucro view, ProdutoService produtos) {
        this.view = view;
        this.produtos = produtos;
        view.getJComboBox1().setEditable(true);
        view.getJComboBox1().addItem(LocalDate.now().format(DATA));
        view.getJTable2().setModel(new DefaultTableModel(new String[]{
            "Nome do produto", "Preço unitário", "Categoria", "Percentual de lucro", "Preço de venda calculado"
        }, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        });
        view.getJButton1().addActionListener(e -> calcular());
        view.getJButton2().addActionListener(e -> view.dispose());
    }

    private void calcular() {
        try {
            Object valor = view.getJComboBox1().getSelectedItem();
            if (valor == null || valor.toString().isBlank()) throw new IllegalArgumentException("Informe a data do cálculo.");
            LocalDate data;
            try { data = LocalDate.parse(valor.toString().trim(), DATA); }
            catch (DateTimeParseException ex) { throw new IllegalArgumentException("Informe a data no formato dd/MM/aaaa."); }
            var lista = produtos.calcularPrecos(data);
            DefaultTableModel model = (DefaultTableModel) view.getJTable2().getModel();
            model.setRowCount(0);
            for (Produto p : lista) model.addRow(new Object[]{
                p.getNome(), TelaPrincipalPresenter.numero(p.getPrecoCusto()), p.getCategoria().getNome(),
                TelaPrincipalPresenter.numero(p.getPercentualLucroCalculado()), TelaPrincipalPresenter.numero(p.getPrecoVenda())
            });
            JOptionPane.showMessageDialog(view, "Preços calculados para " + lista.size() + " produtos.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Cálculo bloqueado", JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "Erro no cálculo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
