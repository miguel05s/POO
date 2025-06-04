package main.java.utils;

public class ConsoleUtil {
    
    public static void limparTela() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Fallback: imprime várias linhas em branco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    public static void limparLinha() {
        // Move o cursor para o início da linha e limpa
        System.out.print("\r\033[K");
        System.out.flush();
    }
    
    public static void limparAteFim() {
        // Limpa do cursor até o fim da tela
        System.out.print("\033[J");
        System.out.flush();
    }
    
    public static void limparAteFimLinha() {
        // Limpa do cursor até o fim da linha
        System.out.print("\033[K");
        System.out.flush();
    }
    
    public static void sobrescreverLinha(String texto) {
        limparLinha();
        System.out.print(texto);
        System.out.flush();
    }
    
    public static void atualizarLinha(String texto) {
        // Primeiro limpa a linha atual
        limparLinha();
        // Imprime o novo texto
        System.out.print(texto);
        System.out.flush();
    }
    
    public static void moverCursor(int linhas) {
        if (linhas > 0) {
            System.out.print("\033[" + linhas + "A"); // Move para cima
        } else if (linhas < 0) {
            System.out.print("\033[" + (-linhas) + "B"); // Move para baixo
        }
        System.out.flush();
    }
} 