package main.java.ui;

import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.services.GestorMusicas;
import main.java.services.GestorPlaylists;
import main.java.models.utilizador.Utilizador;
import main.java.models.utilizador.UtilizadorPremium;
import java.util.List;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class PlaylistUI extends MenuBase {
    private final GestorPlaylists gestorPlaylists;
    private final GestorMusicas gestorMusicas;
    private Utilizador utilizadorAtual;

    public PlaylistUI(Scanner scanner, GestorPlaylists gestorPlaylists, GestorMusicas gestorMusicas) {
        super(scanner, "Gestão de Playlists");
        this.gestorPlaylists = gestorPlaylists;
        this.gestorMusicas = gestorMusicas;
    }

    public void setUtilizadorAtual(Utilizador utilizador) {
        this.utilizadorAtual = utilizador;
    }

    @Override
    protected void mostrarOpcoes() {
        if (utilizadorAtual == null) {
            System.out.println("1. Listar Playlists Públicas");
            System.out.println("2. Voltar");
        } else if (utilizadorAtual instanceof UtilizadorPremium) {
            System.out.println("1. Criar Playlist");
            System.out.println("2. Listar Playlists Públicas");
            System.out.println("3. Adicionar Música à Playlist");
            System.out.println("4. Remover Música da Playlist");
            System.out.println("5. Gerar Playlist por Preferências");
            System.out.println("6. Gerar Playlist por Preferências com Tempo");
            System.out.println("7. Gerar Playlist de Músicas Explícitas");
            System.out.println("8. Voltar");
        } else {
            System.out.println("1. Listar Playlists Públicas");
            System.out.println("2. Voltar");
        }
    }

    @Override
    protected void processarOpcao(int opcao) {
        if (utilizadorAtual == null || !(utilizadorAtual instanceof UtilizadorPremium)) {
            switch (opcao) {
                case 1 -> listarPlaylistsPublicas();
                case 2 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } else {
            switch (opcao) {
                case 1 -> criarPlaylist();
                case 2 -> listarPlaylistsPublicas();
                case 3 -> adicionarMusica();
                case 4 -> removerMusica();
                case 5, 6, 7 -> System.out.println("Funcionalidade em desenvolvimento...");
                case 8 -> System.out.println("A voltar ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        if (utilizadorAtual == null || !(utilizadorAtual instanceof UtilizadorPremium)) {
            return opcao == 2;
        } else {
            return opcao == 8;
        }
    }

    private void listarPlaylistsPublicas() {
        List<Playlist> playlists = gestorPlaylists.listarPlaylistsPublicas();
        StringBuilder resultado = new StringBuilder();
        
        if (playlists.isEmpty()) {
            resultado.append("Nenhuma playlist pública registada.");
        } else {
            playlists.forEach(p -> {
                resultado.append("\nNome: ").append(p.getNome())
                        .append("\nCriador: ").append(p.getCriador())
                        .append("\nTotal de músicas: ").append(p.getTotalMusicas())
                        .append("\nReproduções: ").append(p.getReproducoes())
                        .append("\nMúsicas na playlist:");
                p.getMusicas().forEach(m -> 
                    resultado.append("\n - ").append(m.getNome()).append(" (").append(m.getInterprete()).append(")"));
                resultado.append("\n");
            });
        }
        
        mostrarResultado(resultado.toString());
    }

    private void criarPlaylist() {
        String nome = InputUtil.lerTexto(scanner, "Nome da playlist: ");
        if (gestorPlaylists.existePlaylist(nome)) {
            System.out.println("Já existe uma playlist com esse nome.");
            return;
        }

        boolean publica = InputUtil.lerBoolean(scanner, "Playlist pública? (s/n): ");
        boolean aleatoria = InputUtil.lerBoolean(scanner, "Reprodução aleatória? (s/n): ");

        gestorPlaylists.criarPlaylistPersonalizada(nome, publica, aleatoria, utilizadorAtual.getEmail());
        System.out.println("Playlist criada com sucesso!");
    }

    private void adicionarMusica() {
        String nomePlaylist = InputUtil.lerTexto(scanner, "Nome da playlist: ");
        Playlist playlist = gestorPlaylists.buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            System.out.println("Playlist não encontrada.");
            return;
        }

        String nomeMusica = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nomeMusica);
        if (musica == null) {
            System.out.println("Música não encontrada.");
            return;
        }

        playlist.adicionarMusica(musica);
        System.out.println("Música adicionada com sucesso!");
    }

    private void removerMusica() {
        String nomePlaylist = InputUtil.lerTexto(scanner, "Nome da playlist: ");
        Playlist playlist = gestorPlaylists.buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            System.out.println("Playlist não encontrada.");
            return;
        }

        String nomeMusica = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nomeMusica);
        if (musica == null) {
            System.out.println("Música não encontrada.");
            return;
        }

        playlist.removerMusica(musica);
        System.out.println("Música removida com sucesso!");
    }

}
