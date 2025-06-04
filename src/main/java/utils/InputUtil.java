package main.java.utils;

import java.util.Scanner;

public class InputUtil {
    public static int lerOpcao(Scanner scanner) {
        while (true) {
            System.out.print("\nEscolha uma opção: ");
            if (scanner.hasNextInt()) {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
                return opcao;
            }
            System.out.println("Por favor, introduza um número válido.");
            scanner.nextLine(); // Limpar entrada inválida
        }
    }

    public static String lerTexto(Scanner scanner, String prompt) {
        System.out.print("\n" + prompt);
        return scanner.nextLine().trim();
    }

    public static int lerInteiro(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print("\n" + prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    public static double lerDecimal(Scanner scanner, String prompt) {
        while (true) {
            System.out.print("\n" + prompt);
            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine(); // Limpar buffer
                return valor;
            }
            System.out.println("Por favor, introduza um número decimal válido.");
            scanner.nextLine(); // Limpar entrada inválida
        }
    }

    public static boolean lerBooleano(Scanner scanner, String prompt) {
        while (true) {
            System.out.print("\n" + prompt + " (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("S")) return true;
            if (resposta.equals("N")) return false;
            System.out.println("Por favor, responda com S ou N.");
        }
    }

    public static boolean lerBoolean(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String resposta = scanner.nextLine().toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            } else if (resposta.equals("n") || resposta.equals("não") || resposta.equals("nao")) {
                return false;
            }
            System.out.println("Por favor, responda com 's' ou 'n'.");
        }
    }
}
