package ufes.especificacao_mvp.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.view.BuscaProdutos;
import ufes.especificacao_mvp.view.CalculoMargemLucro;
import ufes.especificacao_mvp.view.CategoriaProdutos;
import ufes.especificacao_mvp.view.ProdutoInclusaoEdicao;
import ufes.especificacao_mvp.view.TelaPrincipal;

public class TelaPrincipalPresenter {

    private final TelaPrincipal view;
    private final ProdutoService produtoService;
    private final IProdutoRepository produtoRepository;
    private final ICategoriaRepository categoriaRepository;
    private final IHistoricoPrecoRepository historicoRepository;

    public TelaPrincipalPresenter(TelaPrincipal view, 
                                  ProdutoService produtoService, 
                                  IProdutoRepository produtoRepository, 
                                  ICategoriaRepository categoriaRepository, 
                                  IHistoricoPrecoRepository historicoRepository) {
        this.view = view;
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.historicoRepository = historicoRepository;

        this.initListeners();
        this.view.setVisible(true);
    }

    private void initListeners() {
        view.getMiIncluirProduto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaInclusaoProduto();
            }
        });

        view.getMiBuscarProdutos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaBuscaProdutos();
            }
        });

        view.getMiCategorias().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCategorias();
            }
        });

        view.getMiCalculoMargem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCalculoMargem();
            }
        });
    }

    private void abrirTelaInclusaoProduto() {
        try {
            ProdutoInclusaoEdicao inclusaoView = new ProdutoInclusaoEdicao();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir inclusão de produtos: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaBuscaProdutos() {
        try {
            BuscaProdutos buscaView = new BuscaProdutos();
            new ProdutoPresenter(buscaView, produtoService, produtoRepository, categoriaRepository);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir busca de produtos: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaCategorias() {
        try {
            CategoriaProdutos categoriaView = new CategoriaProdutos();
            new CategoriaPresenter(categoriaView, categoriaRepository);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir categorias: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirTelaCalculoMargem() {
        try {
            CalculoMargemLucro calculoView = new CalculoMargemLucro();
            new CalculoMargemLucroPresenter(calculoView, produtoService);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir cálculo de margem: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}