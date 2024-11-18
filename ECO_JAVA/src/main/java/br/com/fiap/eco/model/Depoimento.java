package br.com.fiap.eco.model;

public class Depoimento {
    private int idDepoimento;
    private int idCliente;
    private String descricao;
    private String dataDepoimento;

    // Getters e Setters
    public int getIdDepoimento() {
        return idDepoimento;
    }

    public void setIdDepoimento(int idDepoimento) {
        this.idDepoimento = idDepoimento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataDepoimento() {
        return dataDepoimento;
    }

    public void setDataDepoimento(String dataDepoimento) {
        this.dataDepoimento = dataDepoimento;
    }
}
