package main.java.ui;

import main.java.models.musica.Musica;
import main.java.services.GestorReproducao;
import main.java.utils.ConsoleUtil;
import java.util.Scanner;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerUI {
    private final GestorReproducao gestorReproducao;
    private final Scanner scanner;
    private ScheduledExecutorService executor;
    private boolean reproduzindo;
    private int linhaAtual;
    private List<String> letras;

    public PlayerUI(Scanner scanner, GestorReproducao gestorReproducao) {
        this.scanner = scanner;
        this.gestorReproducao = gestorReproducao;
        this.reproduzindo = false;
        this.linhaAtual = 0;
    }

    public void reproduzirMusica(Musica musica) {
        if (musica == null) return;
        
        reproduzindo = true;
        letras = List.of(musica.getLetra().split("\n"));
        linhaAtual = 0;

        // Limpa a tela e mostra informações da música
        ConsoleUtil.limparTela();
        System.out.println("\n=== " + musica.getNome() + " - " + musica.getInterprete() + " ===\n");
        System.out.println("Pressione:");
        System.out.println("  [p] - Pausar/Retomar");
        System.out.println("  [n] - Próxima música");
        System.out.println("  [a] - Música anterior");
        System.out.println("  [q] - Sair\n");
        System.out.println("=== Letra ===\n");

        // Inicia a reprodução das letras
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::atualizarLetra, 0, 1, TimeUnit.SECONDS);

        // Loop de controle
        while (reproduzindo) {
            if (scanner.hasNextLine()) {
                String comando = scanner.nextLine().toLowerCase();
                switch (comando) {
                    case "p" -> pausarRetomar();
                    case "n" -> proximaMusica();
                    case "a" -> musicaAnterior();
                    case "q" -> {
                        reproduzindo = false;
                        executor.shutdown();
                    }
                }
            }
        }
    }

    private void atualizarLetra() {
        if (linhaAtual < letras.size()) {
            ConsoleUtil.limparLinha();
            System.out.println(letras.get(linhaAtual));
            linhaAtual++;
        } else {
            reproduzindo = false;
            executor.shutdown();
        }
    }

    private void pausarRetomar() {
        if (executor.isShutdown()) {
            // Se estava pausado, retoma a reprodução
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::atualizarLetra, 0, 1, TimeUnit.SECONDS);
            System.out.println("Retomando reprodução...");
        } else {
            // Se estava reproduzindo, pausa
            executor.shutdown();
            System.out.println("Reprodução pausada...");
        }
    }

    private void proximaMusica() {
        gestorReproducao.proximaMusica();
        reproduzindo = false;
        executor.shutdown();
    }

    private void musicaAnterior() {
        gestorReproducao.musicaAnterior();
        reproduzindo = false;
        executor.shutdown();
    }
} 