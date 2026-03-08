package main.java.ui;

import java.util.Scanner;

import main.java.services.*;
import main.java.utils.InputUtil;
import main.java.utils.ConsoleUtil;

public class MenuPrincipal extends MenuBase {
    private final GestorMusicas gestorMusicas;
    private final GestorPlaylists gestorPlaylists;
    private final GestorEstatisticas gestorEstatisticas;
    private final LoginUI loginUI;
    private final EstadoUI estadoUI;

    public MenuPrincipal(Scanner scanner, GestorMusicas gestorMusicas, GestorPlaylists gestorPlaylists,
                        GestorReproducao gestorReproducao, GestorEstatisticas gestorEstatisticas,
                        GestorRecomendacoes gestorRecomendacoes, LoginUI loginUI, EstadoUI estadoUI) {
        super(scanner, "Menu Principal");
        this.gestorMusicas = gestorMusicas;
        this.gestorPlaylists = gestorPlaylists;
        this.gestorEstatisticas = gestorEstatisticas;
        this.loginUI = loginUI;
        this.estadoUI = estadoUI;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Gestão");
        System.out.println("2. Estatísticas");
        System.out.println("3. Login/Registo");
        System.out.println("4. Sair");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> mostrarMenuGestao();
            case 2 -> mostrarMenuEstatisticas();
            case 3 -> mostrarMenuLogin();
            case 4 -> System.out.println("A sair do programa...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 4;
    }

    private void mostrarMenuGestao() {
        boolean sair = false;
        while (!sair) {
            ConsoleUtil.limparTela();
            System.out.println("\n=== MENU DE GESTÃO ===");
            System.out.println("1. Gestão de Músicas");
            System.out.println("2. Gestão de Playlists");
            System.out.println("3. Gestão de Utilizadores");
            System.out.println("4. Gestão de Estado");
            System.out.println("5. Voltar");

            int opcao = InputUtil.lerInteiro(scanner, "Escolha uma opção: ");
            switch (opcao) {
                case 1 -> mostrarMenuMusicas();
                case 2 -> mostrarMenuPlaylists();
                case 3 -> mostrarMenuUtilizadores();
                case 4 -> estadoUI.mostrarMenu();
                case 5 -> sair = true;
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void mostrarMenuEstatisticas() {
        ConsoleUtil.limparTela();
        System.out.println("\n=== MENU DE ESTATÍSTICAS ===");
        System.out.println("1. Ver Estatísticas");
        System.out.println("2. Voltar");

        int opcao = InputUtil.lerInteiro(scanner, "Escolha uma opção: ");
        switch (opcao) {
            case 1 -> {
                EstatisticasUI estatisticasUI = new EstatisticasUI(scanner, gestorEstatisticas);
                estatisticasUI.mostrarMenu();
            }
            case 2 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    private void mostrarMenuMusicas() {
        MusicaUI musicaUI = new MusicaUI(scanner, gestorMusicas);
        musicaUI.mostrarMenu();
    }

    private void mostrarMenuPlaylists() {
        PlaylistUI playlistUI = new PlaylistUI(scanner, gestorPlaylists, gestorMusicas);
        playlistUI.mostrarMenu();
    }

    private void mostrarMenuLogin() {
        loginUI.mostrarMenu();
    }

    private void mostrarMenuUtilizadores() {
        UtilizadorUI utilizadorUI = new UtilizadorUI(scanner, loginUI.getGestorUtilizadores());
        utilizadorUI.mostrarMenu();
    }
}
