package ufes.especificacao_mvp.presenter;

import javax.swing.JOptionPane;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.repositorio.CategoriaRepositoryMock;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.view.CategoriaProdutos;

public class CategoriaProdutosPresenter {
    
    private final CategoriaProdutos view;
    private final ICategoriaRepository categoriaRepository;

    public CategoriaProdutosPresenter(CategoriaProdutos view) {
        this.view = view;
        this.categoriaRepository = CategoriaRepositoryMock.getInstance();
        
        this.initListeners();
    }

    private void initListeners() {
        this.view.getBtnSalvar().addActionListener(e -> salvarCategoria());
        this.view.getBtnFechar().addActionListener(e -> view.dispose());
    }

    private void salvarCategoria() {
        try {
            String nome = view.getTxtNomeCategoria().getText();
            if (nome == null || nome.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "O nome da categoria não pode ser vazio.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Categoria categoria = new Categoria(nome);
            categoriaRepository.adicionar(categoria);

            JOptionPane.showMessageDialog(view, "Categoria salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            view.getTxtNomeCategoria().setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar categoria: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
}