package com.bancoeconomico.service.impl;

import com.bancoeconomico.model.ClientePJ;
import com.bancoeconomico.model.Conta;
import com.bancoeconomico.model.ContaInvestimento;
import com.bancoeconomico.service.OperacoesBancarias;

import java.math.BigDecimal;

public class OperacoesContaClientePJ implements OperacoesBancarias<ClientePJ> {

    private static final BigDecimal RENDIMENTO_INVESTIMENTO = BigDecimal.valueOf(1.02);
    private static final BigDecimal TAXA_RETIRADA = BigDecimal.valueOf(1.005);

    @Override
    public void investir(ClientePJ cliente, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaInvestimentoCliente(cliente);
        if (conta == null) {
            conta = new ContaInvestimento(cliente);
            cliente.getContas().add(conta);
        }
        valor = valor.multiply(RENDIMENTO_INVESTIMENTO);
        conta.setSaldo(conta.getSaldo().add(valor));
    }

    @Override
    public void sacar(ClientePJ cliente, Integer numeroConta, BigDecimal valor) {
        Conta conta = OperacoesBancarias.super.getContaCliente(cliente, numeroConta);
        valor = valor.multiply(TAXA_RETIRADA);
        OperacoesBancarias.super.verificarSaldo(conta, valor);
        conta.setSaldo(conta.getSaldo().subtract(valor));
    }

}
