package ufes.especificacao_mvp.model;

public class Categoria {
    private int id;
    private String nome;
    private double percentualLucro;

    public Categoria(String nome, double percentualLucro) {
        this.nome = nome;
        this.percentualLucro = percentualLucro;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPercentualLucro() {
        return percentualLucro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPercentualLucro(double percentualLucro) {
        this.percentualLucro = percentualLucro;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }
}
