package main.java.ui;

import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.models.utilizador.Utilizador;
import main.java.services.GestorMusicas;
import main.java.services.GestorPlaylists;
import main.java.services.GestorReproducao;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class ReproducaoUI extends MenuBase {
    private final GestorReproducao gestorReproducao;
    private final GestorMusicas gestorMusicas;
    private final GestorPlaylists gestorPlaylists;
    private final PlayerUI playerUI;
    private Utilizador utilizadorAtual;

    public ReproducaoUI(Scanner scanner, GestorReproducao gestorReproducao, GestorMusicas gestorMusicas, GestorPlaylists gestorPlaylists) {
        super(scanner, "Reprodução de Músicas");
        this.gestorReproducao = gestorReproducao;
        this.gestorMusicas = gestorMusicas;
        this.gestorPlaylists = gestorPlaylists;
        this.playerUI = new PlayerUI(scanner, gestorReproducao);
    }

    public void setUtilizadorAtual(Utilizador utilizador) {
        this.utilizadorAtual = utilizador;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Reproduzir Música");
        System.out.println("2. Reproduzir Playlist");
        System.out.println("3. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> reproduzirMusica();
            case 2 -> reproduzirPlaylist();
            case 3 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 3;
    }

    private void reproduzirMusica() {
        String nome = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nome);
        if (musica == null) {
            System.out.println("Música não encontrada.");
            return;
        }
        gestorReproducao.reproduzirMusica(musica, utilizadorAtual);
        playerUI.reproduzirMusica(musica);
    }

    private void reproduzirPlaylist() {
        String nome = InputUtil.lerTexto(scanner, "Nome da playlist: ");
        Playlist playlist = gestorPlaylists.buscarPlaylist(nome);
        if (playlist == null) {
            System.out.println("Playlist não encontrada.");
            return;
        }
        gestorReproducao.reproduzirPlaylist(playlist, utilizadorAtual);
        if (!playlist.getMusicas().isEmpty()) {
            playerUI.reproduzirMusica(playlist.getMusicas().get(0));
        }
    }
}
