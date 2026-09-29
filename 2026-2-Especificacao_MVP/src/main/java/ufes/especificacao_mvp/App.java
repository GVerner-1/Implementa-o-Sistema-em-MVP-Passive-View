package ufes.especificacao_mvp;

import ufes.especificacao_mvp.repositorio.CategoriaRepositoryMock;
import ufes.especificacao_mvp.repositorio.HistoricoPrecoRepositoryMock;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;
import ufes.especificacao_mvp.repositorio.ProdutoRepositoryMock;
import ufes.especificacao_mvp.seeder.Seeder;
import ufes.especificacao_mvp.servico.ProdutoService;
import ufes.especificacao_mvp.presenter.TelaPrincipalPresenter;
import ufes.especificacao_mvp.view.TelaPrincipal;

public class App {

    public static void main(String[] args) {
        ICategoriaRepository categoriaRepo = new CategoriaRepositoryMock();
        IProdutoRepository produtoRepo = new ProdutoRepositoryMock();
        IHistoricoPrecoRepository historicoRepo = new HistoricoPrecoRepositoryMock();

        Seeder seeder = new Seeder(categoriaRepo, produtoRepo, historicoRepo);
        seeder.popularBanco();
        ProdutoService produtos = new ProdutoService(produtoRepo, historicoRepo, categoriaRepo);
        javax.swing.SwingUtilities.invokeLater(() ->
            new TelaPrincipalPresenter(new TelaPrincipal(), produtos).exibeTela());
    }
}
