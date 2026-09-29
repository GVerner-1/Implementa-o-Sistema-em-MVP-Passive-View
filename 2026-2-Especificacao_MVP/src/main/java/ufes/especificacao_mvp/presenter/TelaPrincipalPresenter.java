package ufes.especificacao_mvp.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import ufes.especificacao_mvp.view.BuscaProdutos;
import ufes.especificacao_mvp.view.CalculoMargemLucro;
import ufes.especificacao_mvp.view.CategoriaProdutos;
import ufes.especificacao_mvp.view.ProdutoInclusaoEdicao;
import ufes.especificacao_mvp.view.TelaPrincipal;

public class TelaPrincipalPresenter {
    
    private final TelaPrincipal view;

    public TelaPrincipalPresenter(TelaPrincipal view) {
        this.view = view;
        this.initListeners();
    }

    private void initListeners() {
        this.view.getMiIncluirProduto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirInclusaoProduto();
            }
        });

        this.view.getMiBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirBuscaProdutos();
            }
        });

        this.view.getMiCategorias().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCategorias();
            }
        });

        this.view.getMiCalculoMargem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCalculoMargem();
            }
        });
    }

    private void abrirInclusaoProduto() {
        try {
            ProdutoInclusaoEdicao telaInclusao = new ProdutoInclusaoEdicao(view, true);
            telaInclusao.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                    "Erro ao abrir a tela de inclusão de produtos: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirBuscaProdutos() {
        try {
            BuscaProdutos telaBusca = new BuscaProdutos(view, true);
            telaBusca.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                    "Erro ao abrir a tela de busca de produtos: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCategorias() {
        try {
            CategoriaProdutos telaCategorias = new CategoriaProdutos(view, true);
            telaCategorias.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                    "Erro ao abrir a tela de categorias: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCalculoMargem() {
        try {
            CalculoMargemLucro telaMargem = new CalculoMargemLucro(view, true);
            telaMargem.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, 
                    "Erro ao abrir a tela de cálculo de margem: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exibeTela() {
        this.view.setLocationRelativeTo(null);
        this.view.setVisible(true);
    }
}