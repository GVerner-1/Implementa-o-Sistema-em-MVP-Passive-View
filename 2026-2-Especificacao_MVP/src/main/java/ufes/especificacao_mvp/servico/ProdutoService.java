package ufes.especificacao_mvp.servico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import ufes.especificacao_mvp.model.Categoria;
import ufes.especificacao_mvp.model.HistoricoPreco;
import ufes.especificacao_mvp.model.Produto;
import ufes.especificacao_mvp.repositorio.ICategoriaRepository;
import ufes.especificacao_mvp.repositorio.IHistoricoPrecoRepository;
import ufes.especificacao_mvp.repositorio.IProdutoRepository;

public class ProdutoService {
    private final IProdutoRepository produtos;
    private final IHistoricoPrecoRepository historicos;
    private final ICategoriaRepository categorias;

    public ProdutoService(IProdutoRepository produtos, IHistoricoPrecoRepository historicos,
                          ICategoriaRepository categorias) {
        this.produtos = Objects.requireNonNull(produtos);
        this.historicos = Objects.requireNonNull(historicos);
        this.categorias = Objects.requireNonNull(categorias);
    }

    public void incluir(String nome, double custo, Categoria categoria) {
        validar(nome, custo, categoria);
        produtos.salvar(new Produto(0, nome.trim(), custo, 0, categoria));
    }

    public void atualizar(Produto produto, String nome, double custo, Categoria categoria) {
        Objects.requireNonNull(produto, "Produto não informado.");
        if (produtos.buscarTodas().stream().noneMatch(p -> p.getId() == produto.getId())) {
            throw new IllegalArgumentException("O produto não está cadastrado.");
        }
        validar(nome, custo, categoria);
        produto.setNome(nome.trim());
        produto.setPrecoCusto(custo);
        produto.setCategoria(categoria);
        produtos.atualizar(produto);
    }

    private void validar(String nome, double custo, Categoria categoria) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Informe o nome do produto.");
        if (!Double.isFinite(custo) || custo <= 0) throw new IllegalArgumentException("O preço de custo deve ser maior que zero.");
        if (categoria == null || categorias.buscarTodos().stream().noneMatch(c -> c.getId() == categoria.getId())) {
            throw new IllegalArgumentException("Selecione uma categoria cadastrada.");
        }
    }

    public List<Produto> buscarTodos() {
        return produtos.buscarTodas();
    }

    public List<Categoria> buscarCategorias() {
        return categorias.buscarTodos();
    }

    public void incluirCategoria(String nome, double percentual) {
        validarCategoria(nome, percentual, null);
        categorias.salvar(new Categoria(0, nome.trim(), percentual));
    }

    public void atualizarCategoria(Categoria categoria, String nome, double percentual) {
        verificarCategoriaCadastrada(categoria);
        validarCategoria(nome, percentual, categoria);
        categoria.setNome(nome.trim());
        categoria.setPercentualLucro(percentual);
        categorias.atualizar(categoria);
    }

    public void excluirCategoria(Categoria categoria) {
        verificarCategoriaCadastrada(categoria);
        if (!validarExclusaoCategoria(categoria, produtos.buscarTodas())) {
            throw new IllegalArgumentException("Existem produtos associados a esta categoria.");
        }
        categorias.excluir(categoria);
    }

    private void verificarCategoriaCadastrada(Categoria categoria) {
        if (categoria == null || categorias.buscarTodos().stream().noneMatch(c -> c.getId() == categoria.getId())) {
            throw new IllegalArgumentException("Selecione uma categoria cadastrada.");
        }
    }

    private void validarCategoria(String nome, double percentual, Categoria atual) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Informe o nome da categoria.");
        if (!Double.isFinite(percentual) || percentual < 0) {
            throw new IllegalArgumentException("O percentual de lucro deve ser maior ou igual a zero.");
        }
        boolean duplicado = categorias.buscarTodos().stream().anyMatch(c ->
            (atual == null || c.getId() != atual.getId()) && c.getNome().trim().equalsIgnoreCase(nome.trim()));
        if (duplicado) throw new IllegalArgumentException("Já existe uma categoria com esse nome.");
    }

    public List<Produto> buscar(String termo, boolean porCategoria) {
        String consulta = termo == null ? "" : termo.strip().toLowerCase(Locale.ROOT);
        return buscarTodos().stream().filter(p -> {
            String alvo = porCategoria ? p.getCategoria().getNome() : p.getNome();
            return alvo.toLowerCase(Locale.ROOT).contains(consulta);
        }).toList();
    }

    public List<HistoricoPreco> historico(Produto produto) {
        if (produto == null) throw new IllegalArgumentException("Selecione um produto.");
        return historicos.buscarTodos().stream()
            .filter(h -> h.getProduto().getId() == produto.getId())
            .sorted(Comparator.comparing(HistoricoPreco::getData).reversed()
                .thenComparing(Comparator.comparingInt(HistoricoPreco::getId).reversed()))
            .toList();
    }

    public List<Produto> calcularPrecos(LocalDate data) {
        if (data == null) throw new IllegalArgumentException("Informe a data do cálculo.");
        LocalDate ultimo = historicos.buscarTodos().stream().map(HistoricoPreco::getData)
            .max(LocalDate::compareTo).orElse(null);
        if (ultimo != null && ChronoUnit.DAYS.between(ultimo, data) < 10) {
            throw new IllegalArgumentException("O novo cálculo só pode ser realizado após 10 dias do último cálculo global.");
        }

        List<Produto> lista = produtos.buscarTodas();
        List<Categoria> categoriasAtuais = categorias.buscarTodos();
        List<HistoricoPreco> novos = new ArrayList<>();
        for (Produto p : lista) {
            Categoria categoriaAtual = p.getCategoria() == null ? null : categoriasAtuais.stream()
                .filter(c -> c.getId() == p.getCategoria().getId()).findFirst().orElse(null);
            if (categoriaAtual == null) throw new IllegalArgumentException("Categoria inválida para o produto: " + p.getNome());
            double percentual = categoriaAtual.getPercentualLucro();
            if (!Double.isFinite(p.getPrecoCusto()) || p.getPrecoCusto() <= 0 ||
                !Double.isFinite(percentual) || percentual < 0) {
                throw new IllegalArgumentException("Valores inválidos para o produto: " + p.getNome());
            }
            double preco = arredondar(p.getPrecoCusto() * (1 + percentual / 100.0));
            novos.add(new HistoricoPreco(data, percentual, preco, p));
        }
        for (int i = 0; i < lista.size(); i++) {
            Produto p = lista.get(i);
            HistoricoPreco h = novos.get(i);
            p.setPercentualLucroCalculado(h.getPercentualLucro());
            p.setPrecoVenda(h.getValorVenda());
            produtos.atualizar(p);
            historicos.salvar(h);
        }
        return lista;
    }

    public void calcularPrecoVenda(Produto produto) throws Exception {
        if (produto == null) throw new IllegalArgumentException("O produto não pode ser nulo.");
        LocalDate hoje = LocalDate.now();
        LocalDate ultimo = historicos.buscarTodos().stream().map(HistoricoPreco::getData)
            .max(LocalDate::compareTo).orElse(null);
        if (ultimo != null && ChronoUnit.DAYS.between(ultimo, hoje) < 10) {
            throw new Exception("Operação bloqueada: A última alteração de preço ocorreu há menos de 10 dias.");
        }
        validar(produto.getNome(), produto.getPrecoCusto(), produto.getCategoria());
        double margem = produto.getCategoria().getPercentualLucro();
        double preco = arredondar(produto.getPrecoCusto() * (1 + margem / 100.0));
        produto.setPercentualLucroCalculado(margem);
        produto.setPrecoVenda(preco);
        produtos.atualizar(produto);
        historicos.salvar(new HistoricoPreco(hoje, margem, preco, produto));
    }

    public boolean validarExclusaoCategoria(Categoria categoria, List<Produto> todosProdutos) {
        if (categoria == null || todosProdutos == null) throw new IllegalArgumentException("Categoria ou lista não informada.");
        return todosProdutos.stream().noneMatch(p -> p.getCategoria() != null && p.getCategoria().getId() == categoria.getId());
    }

    public static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
