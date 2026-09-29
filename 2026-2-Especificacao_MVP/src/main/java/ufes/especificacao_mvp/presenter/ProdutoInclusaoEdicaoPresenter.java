package ufes.especificacao_mvp.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.repositorio.CategoriaRepositoryMock;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.ProdutoInclusaoEdicao;

public class ProdutoInclusaoEdicaoPresenter {
    
    private final ProdutoInclusaoEdicao view;
    private final ProdutoService produtoService;
    private final ICategoriaRepository categoriaRepository;

    public ProdutoInclusaoEdicaoPresenter(ProdutoInclusaoEdicao view) {
        this.view = view;
        this.produtoService = new ProdutoService();
        this.categoriaRepository = CategoriaRepositoryMock.getInstance();
        
        this.carregarCategorias();
        this.initListeners();
    }

    private void carregarCategorias() {
        try {
            view.getCbCategorias().removeAllItems();
            for (Categoria categoria : categoriaRepository.listar()) {
                view.getCbCategorias().addItem(categoria);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar categorias: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initListeners() {
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @org.jetbrains.annotations.NotNull
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarProduto();
            }
        });

        this.view.getBtnCancelar().addActionListener(e -> view.dispose());
    }

    private void salvarProduto() {
        try {
            String nome = view.getTxtNome().getText();
            BigDecimal valor = new BigDecimal(view.getTxtValor().getText());
            Categoria categoria = (Categoria) view.getCbCategorias().getSelectedItem();

            Produto produto = new Produto(nome, valor, categoria);
            produtoService.incluir(produto);

            JOptionPane.showMessageDialog(view, "Produto salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "O valor do produto deve ser um número válido.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
}