package com.bancoeconomico.service;


import com.bancoeconomico.exceptions.SaldoInsuficienteException;
import com.bancoeconomico.model.Cliente;
import com.bancoeconomico.model.Conta;

import java.math.BigDecimal;

public interface Saque<T extends Cliente> {

    void sacar(T cliente, Integer numeroConta, BigDecimal valor);

    default void verificarSaldo(Conta conta, BigDecimal valor) {
        if (valor.compareTo(conta.getSaldo()) > 0) {
            throw new SaldoInsuficienteException();
        }
    }

    default BigDecimal getTaxaSaque() {
        return BigDecimal.valueOf(1);
    }

}
