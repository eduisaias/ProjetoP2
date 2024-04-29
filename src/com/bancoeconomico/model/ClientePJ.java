package com.bancoeconomico.model;

public class ClientePJ extends Cliente {

    private String cnpj;

    public ClientePJ(String nome, String cnpj) {
        super(nome);
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String getId() {
        return getCnpj();
    }
}
