package br.com.fiap.eco.model;


public class Consumo {
    private int idConsumoCli;
    private int idCliente;
    private double custoMensal;
    private double kwMes;
    private String distribuidora;
    private double tarifa;
    private String dataConsumo;
    private int idEndereco;

    public Consumo(int idConsumoCli, int idCliente, double custoMensal, double kwMes, String distribuidora, double tarifa, String dataConsumo, int idEndereco) {
        this.idConsumoCli = idConsumoCli;
        this.idCliente = idCliente;
        this.custoMensal = custoMensal;
        this.kwMes = kwMes;
        this.distribuidora = distribuidora;
        this.tarifa = tarifa;
        this.dataConsumo = dataConsumo;
        this.idEndereco = idEndereco;
    }

    public Consumo(){}

    public int getIdConsumoCli() {
        return idConsumoCli;
    }

    public void setIdConsumoCli(int idConsumoCli) {
        this.idConsumoCli = idConsumoCli;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public double getCustoMensal() {
        return custoMensal;
    }

    public void setCustoMensal(double custoMensal) {
        this.custoMensal = custoMensal;
    }

    public double getKwMes() {
        return kwMes;
    }

    public void setKwMes(double kwMes) {
        this.kwMes = kwMes;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public String getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }
}
