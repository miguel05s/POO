package main.java.ui;

import java.util.List;
import java.util.Scanner;

import main.java.models.utilizador.Utilizador;
import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.services.GestorAlbuns;
import main.java.services.GestorMusicas;
import main.java.services.GestorPlaylists;
import main.java.services.GestorPersistencia;

public class EstadoUI extends MenuBase {
    private final GestorPersistencia gestorPersistencia;
    private final GestorMusicas gestorMusicas;
    private final GestorPlaylists gestorPlaylists;
    private final List<Utilizador> utilizadores;

    public EstadoUI(Scanner scanner, GestorMusicas gestorMusicas, GestorAlbuns gestorAlbuns,
                   GestorPlaylists gestorPlaylists, List<Utilizador> utilizadores) {
        super(scanner, "Gestão de Estado");
        this.gestorPersistencia = new GestorPersistencia();
        this.gestorMusicas = gestorMusicas;
        this.gestorPlaylists = gestorPlaylists;
        this.utilizadores = utilizadores;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("\n=== Gestão de Estado ===");
        System.out.println("1. Guardar estado");
        System.out.println("2. Carregar estado");
        System.out.println("0. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> guardarEstado();
            case 2 -> carregarEstado();
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 0;
    }

    private void guardarEstado() {
        try {
            gestorPersistencia.salvarMusicas(gestorMusicas.listarMusicas());
            gestorPersistencia.salvarPlaylists(gestorPlaylists.listarPlaylists());
            gestorPersistencia.salvarUtilizadores(utilizadores);
            System.out.println("Estado guardado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao guardar estado: " + e.getMessage());
        }
    }

    private void carregarEstado() {
        try {
            List<Musica> musicas = gestorPersistencia.carregarMusicas();
            List<Playlist> playlists = gestorPersistencia.carregarPlaylists();
            List<Utilizador> utilizadoresSalvos = gestorPersistencia.carregarUtilizadores();

            if (!musicas.isEmpty()) gestorMusicas.setMusicas(musicas);
            if (!playlists.isEmpty()) gestorPlaylists.setPlaylists(playlists);
            if (!utilizadoresSalvos.isEmpty()) {
                utilizadores.clear();
                utilizadores.addAll(utilizadoresSalvos);
            }
            System.out.println("Estado carregado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar estado: " + e.getMessage());
        }
    }
}
