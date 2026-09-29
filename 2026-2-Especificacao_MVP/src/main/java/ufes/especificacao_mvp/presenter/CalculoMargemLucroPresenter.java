package ufes.especificacao_mvp.presenter;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import ufes.especificacao_mvp.view.CalculoMargemLucro;

public class CalculoMargemLucroPresenter {
    
    private final CalculoMargemLucro view;

    public CalculoMargemLucroPresenter(CalculoMargemLucro view) {
        this.view = view;
        this.initListeners();
    }

    private void initListeners() {
        this.view.getBtnCalcular().addActionListener(e -> calcularMargem());
        this.view.getBtnFechar().addActionListener(e -> view.dispose());
    }

    private void calcularMargem() {
        try {
            BigDecimal custo = new BigDecimal(view.getTxtCusto().getText());
            BigDecimal venda = new BigDecimal(view.getTxtValorVenda().getText());

            if (custo.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(view, "O valor de custo deve ser maior que zero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BigDecimal lucro = venda.subtract(custo);
            BigDecimal margem = lucro.divide(custo, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));

            view.getLblResultado().setText(String.format("Margem de Lucro: %.2f%%", margem));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Insira valores numéricos válidos.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao calcular margem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
}