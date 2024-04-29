package com.bancoeconomico.model;

import com.bancoeconomico.model.enums.StatusClienteEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {

    private String nome;
    private LocalDate dataCadastro;
    private StatusClienteEnum status;

    private List<Conta> contas;

    public Cliente(String nome) {
        this.nome = nome;
        this.dataCadastro = LocalDate.now();
        this.status = StatusClienteEnum.ATIVO;
        this.contas = new ArrayList<>();
        this.contas.add(new ContaCorrente(this));
    }

    public abstract String getId();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public StatusClienteEnum getStatus() {
        return status;
    }

    public void setStatus(StatusClienteEnum status) {
        this.status = status;
    }

    public List<Conta> getContas() {
        return contas;
    }

}
