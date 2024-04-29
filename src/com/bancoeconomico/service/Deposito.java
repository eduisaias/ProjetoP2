package com.bancoeconomico.service;

import com.bancoeconomico.model.Cliente;

import java.math.BigDecimal;

public interface Deposito<T extends Cliente> {

    void depositar(T cliente, Integer numeroConta, BigDecimal valor);

}
