package com.bancoeconomico.service;

import com.bancoeconomico.model.Cliente;

import java.math.BigDecimal;

public interface Investimento<T extends Cliente> {

    void investir(T cliente, BigDecimal valor);

}
