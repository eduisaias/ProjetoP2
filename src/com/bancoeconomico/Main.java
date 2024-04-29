package com.bancoeconomico;

import com.bancoeconomico.model.Cliente;
import com.bancoeconomico.model.ClientePF;
import com.bancoeconomico.model.ClientePJ;
import com.bancoeconomico.model.Conta;
import com.bancoeconomico.service.factory.OperacoesBancariasFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Main {

    private static final String TIPO_PF = "2";
    private static final String TIPO_PJ = "1";

    public static void main(String[] args) throws IOException {

        Path path = Path.of("pessoas.csv");

        Stream<String> linhas = Files.lines(path);


        List<String> saida = linhas
                .skip(1)
                .map(linha -> linha.split(","))
                .filter(linha -> verificarIdade(linha))
                .map(linha -> getCliente(linha)) //lista clientes
                .map(cliente -> {
                    deposito(cliente, BigDecimal.valueOf(50));
                    return cliente;
                })
                .map(cliente -> cliente.getContas().get(0))
                .map(conta -> {
                    String tipo = (conta.getCliente() instanceof ClientePF) ? "PF" : "PJ";
                    return conta.getCliente().getNome()+","+conta.getCliente().getId()+","
                            + tipo +","+conta.getNumero()+","+conta.getSaldo();
                })
                .toList();

        saida.forEach(System.out::println);

        Path pathSaida = Path.of("saida.csv");
        Files.write(pathSaida, saida);

        Cliente clientePF = new ClientePF("Cliente PF", "234143232");
        deposito(clientePF, BigDecimal.valueOf(10));
        saque(clientePF, BigDecimal.valueOf(10));
        deposito(clientePF, BigDecimal.valueOf(10));
        investimento(clientePF, BigDecimal.valueOf(10));


        Cliente clientePJ = new ClientePJ("Cliente PJ", "2158795");
        deposito(clientePJ, BigDecimal.valueOf(30));
        saque(clientePJ, BigDecimal.valueOf(10));
        deposito(clientePJ, BigDecimal.valueOf(30));
        investimento(clientePJ, BigDecimal.valueOf(10));

        transferir(clientePF, clientePJ, BigDecimal.valueOf(10));

        imprimirTodosSaldos(clientePF);
        imprimirTodosSaldos(clientePJ);

    }




    private static Cliente getCliente(String[] linha) {
        if ( Objects.equals(TIPO_PF, linha[3]) ) {

            return new ClientePF(linha[0], linha[2]);
        } else {
            return new ClientePJ(linha[0], linha[2]);
        }
    }

    private static boolean verificarIdade(String[] linha) {
        if (TIPO_PF.equals(linha[3])) {
            LocalDate dataNascimento = LocalDate.parse(linha[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return ChronoUnit.YEARS.between(dataNascimento, LocalDate.now()) >= 18;
        }
        return true;
    }

    static void deposito(Cliente cliente, BigDecimal valor) {
        Conta conta = cliente.getContas().get(0);
        OperacoesBancariasFactory.getInstance().get(cliente)
                .depositar(cliente, conta.getNumero(), valor);
        print("deposito: " + valor + " saldo " + conta.getSaldo());
    }

    static void saque(Cliente cliente, BigDecimal valor) {
        Conta conta = cliente.getContas().get(0);
        OperacoesBancariasFactory.getInstance().get(cliente)
                .sacar(cliente, conta.getNumero(), valor);
        print("saque: " + valor + " saldo " + conta.getSaldo());
    }

    static void transferir(Cliente clienteOrigem, Cliente clienteDestino, BigDecimal valor) {
        Conta contaOrigem = clienteOrigem.getContas().get(0);
        Conta contaDestino = clienteDestino.getContas().get(0);
        OperacoesBancariasFactory.getInstance().get(clienteOrigem)
                .transferir(clienteOrigem, contaOrigem.getNumero(), contaDestino, valor);
        print("transferencia origem: " + valor + " saldo " + contaOrigem.getSaldo());
        print("transferencia destino: " + valor + " saldo " + contaDestino.getSaldo());
    }

    static void investimento(Cliente cliente, BigDecimal valor) {
        Conta conta = cliente.getContas().get(0);
        OperacoesBancariasFactory.getInstance().get(cliente)
                .investir(cliente, valor);
        print("investimento: " + valor + " saldo " + conta.getSaldo());
    }

    static void imprimirTodosSaldos(Cliente cliente) {
        print("SALDOS ===============");
        print("Cliente: " + cliente.getNome());
        BigDecimal saldoTotal = BigDecimal.ZERO;
        for (Conta conta : cliente.getContas()) {
            print(conta.getClass().getSimpleName() + " - " + conta.getSaldo());
            saldoTotal = saldoTotal.add(conta.getSaldo());
        }
        print("Total: " + saldoTotal);
        print("SALDOS ===============");
    }

    static void print(Object o) {
        System.out.println(o);
    }

}