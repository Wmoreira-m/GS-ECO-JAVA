package br.com.fiap.eco.model;

public class Endereco {

    private int idEndereco;
    private String cep;
    private String apelidoLocal;
    private String localidade;
    private String uf;
    private String regiao;
    private String insolacaoMd;
    private int idCliente;

    public Endereco(int idEndereco, String cep, String apelidoLocal, String localidade, String uf,
                    String regiao, String insolacaoMd, int idCliente) {
        this.idEndereco = idEndereco;
        this.cep = cep;
        this.apelidoLocal = apelidoLocal;
        this.localidade = localidade;
        this.uf = uf;
        this.regiao = regiao;
        this.insolacaoMd = insolacaoMd;
        this.idCliente = idCliente;
    }


    public Endereco() {
    }


    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getApelidoLocal() {
        return apelidoLocal;
    }

    public void setApelidoLocal(String apelidoLocal) {
        this.apelidoLocal = apelidoLocal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getInsolacaoMd() {
        return insolacaoMd;
    }

    public void setInsolacaoMd(String insolacaoMd) {
        this.insolacaoMd = insolacaoMd;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
