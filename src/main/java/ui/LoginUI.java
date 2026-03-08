package main.java.ui;

import main.java.models.utilizador.Utilizador;
import main.java.services.*;
import java.util.Scanner;
import main.java.utils.InputUtil;
import main.java.utils.ConsoleUtil;

public class LoginUI extends MenuBase {
    private final GestorUtilizadores gestorUtilizadores;
    private final GestorPlaylists gestorPlaylists;
    private final GestorMusicas gestorMusicas;
    private final GestorRecomendacoes gestorRecomendacoes;
    private final GestorReproducao gestorReproducao;
    private final GestorEstatisticas gestorEstatisticas;
    private Utilizador utilizadorAtual;

    public LoginUI(Scanner scanner, GestorUtilizadores gestorUtilizadores, 
                  GestorPlaylists gestorPlaylists, GestorMusicas gestorMusicas,
                  GestorRecomendacoes gestorRecomendacoes, GestorReproducao gestorReproducao,
                  GestorEstatisticas gestorEstatisticas) {
        super(scanner, "Login");
        this.gestorUtilizadores = gestorUtilizadores;
        this.gestorPlaylists = gestorPlaylists;
        this.gestorMusicas = gestorMusicas;
        this.gestorRecomendacoes = gestorRecomendacoes;
        this.gestorReproducao = gestorReproducao;
        this.gestorEstatisticas = gestorEstatisticas;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Login");
        System.out.println("2. Registar");
        System.out.println("3. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> fazerLogin();
            case 2 -> registar();
            case 3 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 3;
    }

    private void fazerLogin() {
        String email = InputUtil.lerTexto(scanner, "Email: ");
        String senha = InputUtil.lerTexto(scanner, "Senha: ");

        utilizadorAtual = gestorUtilizadores.fazerLogin(email, senha);
        if (utilizadorAtual != null) {
            System.out.println("Login realizado com sucesso!");
            mostrarMenuUtilizador();
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private void mostrarMenuUtilizador() {
        boolean sair = false;
        while (!sair) {
            ConsoleUtil.limparTela();
            System.out.println("\n=== MENU DO UTILIZADOR ===");
            System.out.println("1. Biblioteca");
            System.out.println("2. Playlists");
            System.out.println("3. Reproduzir");
            System.out.println("4. Ver Recomendações");
            System.out.println("5. Ver Estatísticas Pessoais");
            System.out.println("6. Logout");

            int opcao = InputUtil.lerInteiro(scanner, "Escolha uma opção: ");
            switch (opcao) {
                case 1 -> {
                    BibliotecaUI bibliotecaUI = new BibliotecaUI(scanner, gestorMusicas);
                    bibliotecaUI.mostrarMenu();
                }
                case 2 -> {
                    PlaylistUI playlistUI = new PlaylistUI(scanner, gestorPlaylists, gestorMusicas);
                    playlistUI.setUtilizadorAtual(utilizadorAtual);
                    playlistUI.mostrarMenu();
                }
                case 3 -> {
                    ReproducaoUI reproducaoUI = new ReproducaoUI(scanner, gestorReproducao, gestorMusicas, gestorPlaylists);
                    reproducaoUI.setUtilizadorAtual(utilizadorAtual);
                    reproducaoUI.mostrarMenu();
                }
                case 4 -> {
                    RecomendacoesUI recomendacoesUI = new RecomendacoesUI(scanner, gestorRecomendacoes);
                    recomendacoesUI.setUtilizadorAtual(utilizadorAtual);
                    recomendacoesUI.mostrarMenu();
                }
                case 5 -> {
                    EstatisticasUI estatisticasUI = new EstatisticasUI(scanner, gestorEstatisticas);
                    estatisticasUI.mostrarMenu();
                }
                case 6 -> {
                    utilizadorAtual = null;
                    sair = true;
                    System.out.println("Logout realizado com sucesso!");
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void registar() {
        String nome = InputUtil.lerTexto(scanner, "Nome: ");
        if (gestorUtilizadores.existeUtilizador(nome)) {
            System.out.println("Já existe um utilizador com esse nome.");
            return;
        }

        String senha = InputUtil.lerTexto(scanner, "Senha: ");
        String email = InputUtil.lerTexto(scanner, "Email: ");

        utilizadorAtual = gestorUtilizadores.registar(nome, senha, email);
        if (utilizadorAtual != null) {
            System.out.println("Registo realizado com sucesso!");
        } else {
            System.out.println("Erro ao registar utilizador.");
        }
    }

    public Utilizador getUtilizadorAtual() {
        return utilizadorAtual;
    }

    public boolean estaLogado() {
        return utilizadorAtual != null;
    }

    public GestorUtilizadores getGestorUtilizadores() {
        return gestorUtilizadores;
    }
}
