package com.bancoeconomico.exceptions;

public class ContaNaoEncontradaException extends RuntimeException {

    public ContaNaoEncontradaException() {
        super("Conta não encontrada!");
    }

}
