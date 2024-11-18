package br.com.fiap.eco.model;


public class Cliente {

    private int idCliente;
    private String nome;
    private String email;
    private String senha;
    private String dtAtivacao;

    public Cliente(int idCliente, String nome, String email, String senha, String dtAtivacao) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dtAtivacao = dtAtivacao;
    }

    public Cliente(int idCliente, String nome, String email, String dtAtivacao) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.email = email;
        this.dtAtivacao = dtAtivacao;
    }

    public Cliente() {
    }



    // Getters e Setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDtAtivacao() {
        return dtAtivacao;
    }

    public void setDtAtivacao(String dtAtivacao) {
        this.dtAtivacao = dtAtivacao;
    }
}
