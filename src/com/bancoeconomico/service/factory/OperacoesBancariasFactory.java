package com.bancoeconomico.service.factory;

import com.bancoeconomico.exceptions.TipoClienteInvalidoException;
import com.bancoeconomico.model.Cliente;
import com.bancoeconomico.model.ClientePF;
import com.bancoeconomico.model.ClientePJ;
import com.bancoeconomico.service.OperacoesBancarias;
import com.bancoeconomico.service.impl.OperacoesContaClientePF;
import com.bancoeconomico.service.impl.OperacoesContaClientePJ;

public final class OperacoesBancariasFactory {

    private final OperacoesBancarias<ClientePF> opeBancPF;
    private final OperacoesBancarias<ClientePJ> opeBancPJ;

    private static OperacoesBancariasFactory instance;

    private OperacoesBancariasFactory() {
        this.opeBancPF = new OperacoesContaClientePF();
        this.opeBancPJ = new OperacoesContaClientePJ();
    }

    public static OperacoesBancariasFactory getInstance() {
        if (instance == null) {
            instance = new OperacoesBancariasFactory();
        }
        return instance;
    }

    public OperacoesBancarias get(Cliente cliente) {
        if (cliente instanceof ClientePF) {
            return this.opeBancPF;
        } else if (cliente instanceof ClientePJ) {
            return this.opeBancPJ;
        } else {
            throw new TipoClienteInvalidoException();
        }
    }

}
