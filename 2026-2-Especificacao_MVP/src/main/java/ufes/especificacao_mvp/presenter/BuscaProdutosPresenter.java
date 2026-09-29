package ufes.especificacao_mvp.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.BuscaProdutos;

public class BuscaProdutosPresenter {
    
    private final BuscaProdutos view;
    private final ProdutoService produtoService;

    public BuscaProdutosPresenter(BuscaProdutos view) {
        this.view = view;
        this.produtoService = new ProdutoService();
        
        this.initListeners();
        this.carregarProdutos();
    }

    private void initListeners() {
        this.view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProdutos();
            }
        });

        this.view.getBtnFechar().addActionListener(e -> view.dispose());
    }

    private void carregarProdutos() {
        try {
            List<Produto> produtos = produtoService.buscarTodos();
            atualizarTabela(produtos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProdutos() {
        try {
            String termo = view.getTxtTermoBusca().getText();
            List<Produto> produtos = produtoService.buscarPorNome(termo);
            atualizarTabela(produtos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro na busca: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela(List<Produto> produtos) {
        DefaultTableModel model = (DefaultTableModel) view.getTabelaProdutos().getModel();
        model.setRowCount(0);

        for (Produto p : produtos) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getValor(),
                p.getCategoria() != null ? p.getCategoria().getNome() : "Sem Categoria"
            });
        }
    }

    public void exibeTela() {
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
}