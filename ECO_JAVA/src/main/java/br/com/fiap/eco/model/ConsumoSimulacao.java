package br.com.fiap.eco.model;

public class ConsumoSimulacao {
    private int idConsumoSimu;
    private int idCliente;
    private double custoFinal;
    private int qtdPlacas;
    private String modeloPlaca;
    private double kwTotalGerado;
    private double economiaGerada;
    private double totalInvestir;
    private int idConsumoCli;
    private int idEndereco;
    private String grid;
    private String dtSimulacao;


    public ConsumoSimulacao(int idConsumoSimu, int idCliente, double custoFinal, int qtdPlacas, String modeloPlaca,
                            double kwTotalGerado, double economiaGerada, double totalInvestir, int idConsumoCli,
                            int idEndereco, String grid, String dtSimulacao) {
        this.idConsumoSimu = idConsumoSimu;
        this.idCliente = idCliente;
        this.custoFinal = custoFinal;
        this.qtdPlacas = qtdPlacas;
        this.modeloPlaca = modeloPlaca;
        this.kwTotalGerado = kwTotalGerado;
        this.economiaGerada = economiaGerada;
        this.totalInvestir = totalInvestir;
        this.idConsumoCli = idConsumoCli;
        this.idEndereco = idEndereco;
        this.grid = grid;
        this.dtSimulacao = dtSimulacao;
    }

    public ConsumoSimulacao() {
    }

    public int getIdConsumoSimu() {
        return idConsumoSimu;
    }

    public void setIdConsumoSimu(int idConsumoSimu) {
        this.idConsumoSimu = idConsumoSimu;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getCustoFinal() {
        return custoFinal;
    }

    public void setCustoFinal(double custoFinal) {
        this.custoFinal = custoFinal;
    }

    public int getQtdPlacas() {
        return qtdPlacas;
    }

    public void setQtdPlacas(int qtdPlacas) {
        this.qtdPlacas = qtdPlacas;
    }

    public String getModeloPlaca() {
        return modeloPlaca;
    }

    public void setModeloPlaca(String modeloPlaca) {
        this.modeloPlaca = modeloPlaca;
    }

    public double getKwTotalGerado() {
        return kwTotalGerado;
    }

    public void setKwTotalGerado(double kwTotalGerado) {
        this.kwTotalGerado = kwTotalGerado;
    }

    public double getEconomiaGerada() {
        return economiaGerada;
    }

    public void setEconomiaGerada(double economiaGerada) {
        this.economiaGerada = economiaGerada;
    }

    public double getTotalInvestir() {
        return totalInvestir;
    }

    public void setTotalInvestir(double totalInvestir) {
        this.totalInvestir = totalInvestir;
    }

    public int getIdConsumoCli() {
        return idConsumoCli;
    }

    public void setIdConsumoCli(int idConsumoCli) {
        this.idConsumoCli = idConsumoCli;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getDtSimulacao() {
        return dtSimulacao;
    }

    public void setDtSimulacao(String dtSimulacao) {
        this.dtSimulacao = dtSimulacao;
    }
}