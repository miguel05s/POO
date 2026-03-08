package main.java.ui;

import java.util.Scanner;
import main.java.utils.InputUtil;
import main.java.utils.ConsoleUtil;

public abstract class MenuBase {
    protected final Scanner scanner;
    protected final String titulo;

    public MenuBase(Scanner scanner, String titulo) {
        this.scanner = scanner;
        this.titulo = titulo;
    }

    public void mostrarMenu() {
        boolean sair = false;
        while (!sair) {
            ConsoleUtil.limparAteFim();
            exibirCabecalho();
            mostrarOpcoes();
            int opcao = InputUtil.lerInteiro(scanner, "Escolha uma opção: ");
            processarOpcao(opcao);
            sair = deveSair(opcao);
        }
    }

    protected void exibirCabecalho() {
        System.out.println("\n=== " + titulo + " ===\n");
    }

    protected void mostrarResultado(String mensagem) {
        ConsoleUtil.limparTela(); // Limpa a tela antes de mostrar o resultado
        System.out.println("\n" + mensagem);
        System.out.println("\nPressione ENTER para voltar ao menu...");
        scanner.nextLine();
    }

    protected abstract void mostrarOpcoes();
    protected abstract void processarOpcao(int opcao);
    protected abstract boolean deveSair(int opcao);
} 