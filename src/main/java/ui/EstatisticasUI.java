package main.java.ui;

import main.java.services.GestorEstatisticas;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class EstatisticasUI extends MenuBase {
    private final GestorEstatisticas gestorEstatisticas;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public EstatisticasUI(Scanner scanner, GestorEstatisticas gestorEstatisticas) {
        super(scanner, "Estatísticas");
        this.gestorEstatisticas = gestorEstatisticas;
    }


    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Ver Estatísticas Gerais");
        System.out.println("2. Ver Estatísticas por Período");
        System.out.println("3. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> exibirEstatisticasGerais();
            case 2 -> exibirEstatisticasPorPeriodo();
            case 3 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 3;
    }

    private void exibirEstatisticasGerais() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("\nEstatísticas Gerais");
        resultado.append("\nTotal de músicas: ").append(gestorEstatisticas.getTotalMusicas());
        
        resultado.append("\n\nMúsicas mais reproduzidas:");
        gestorEstatisticas.getMusicasMaisReproduzidas().forEach(m -> 
            resultado.append("\n- ").append(m.getNome()).append(" (").append(m.getInterprete()).append(")"));
        
        resultado.append("\n\nIntérprete mais escutado: ").append(gestorEstatisticas.getInterpreteMaisEscutado());
        resultado.append("\nUtilizador mais ativo: ").append(gestorEstatisticas.getUtilizadorMaisAtivo());
        
        mostrarResultado(resultado.toString());
    }

    private void exibirEstatisticasPorPeriodo() {
        LocalDateTime inicio = lerDataHora("Data e hora inicial (dd/MM/yyyy HH:mm): ");
        LocalDateTime fim = lerDataHora("Data e hora final (dd/MM/yyyy HH:mm): ");
        
        if (inicio != null && fim != null) {
            gestorEstatisticas.exibirEstatisticasPorPeriodo(inicio, fim);
        }
    }

    private LocalDateTime lerDataHora(String mensagem) {
        while (true) {
            String input = InputUtil.lerTexto(scanner, mensagem);
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use dd/MM/yyyy HH:mm");
            }
        }
    }
}
