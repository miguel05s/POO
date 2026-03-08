package main.java.ui;

import main.java.models.utilizador.Utilizador;
import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.services.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Menu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inicialização dos gestores
        List<Utilizador> utilizadores = new ArrayList<>();
        GestorMusicas gestorMusicas = new GestorMusicas();
        GestorAlbuns gestorAlbuns = new GestorAlbuns();
        GestorPlaylists gestorPlaylists = new GestorPlaylists(gestorMusicas);
        GestorReproducao gestorReproducao = new GestorReproducao();
        GestorUtilizadores gestorUtilizadores = new GestorUtilizadores(utilizadores);
        GestorEstatisticas gestorEstatisticas = new GestorEstatisticas(gestorMusicas, gestorPlaylists, gestorReproducao, utilizadores);
        GestorRecomendacoes gestorRecomendacoes = new GestorRecomendacoes(gestorMusicas.listarMusicas(), gestorPlaylists.listarPlaylists(), gestorMusicas);
        GestorPersistencia gestorPersistencia = new GestorPersistencia();
        
        // Carregar dados salvos
        File dirDados = new File("dados");
        if (!dirDados.exists()) {
            dirDados.mkdirs();
            System.out.println("Diretório de dados criado em: " + dirDados.getAbsolutePath());
        }

        List<Musica> musicas = gestorPersistencia.carregarMusicas();
        List<Playlist> playlists = gestorPersistencia.carregarPlaylists();
        List<Utilizador> utilizadoresSalvos = gestorPersistencia.carregarUtilizadores();

        if (!musicas.isEmpty()) {
            gestorMusicas.setMusicas(musicas);
            System.out.println("+ Dados de músicas carregados");
        }
        if (!playlists.isEmpty()) {
            gestorPlaylists.setPlaylists(playlists);
            System.out.println("+ Dados de playlists carregados");
        }
        if (!utilizadoresSalvos.isEmpty()) {
            utilizadores.clear();
            utilizadores.addAll(utilizadoresSalvos);
            System.out.println("+ Dados de utilizadores carregados");
        }
        
        LoginUI loginUI = new LoginUI(scanner, gestorUtilizadores, gestorPlaylists, gestorMusicas, 
            gestorRecomendacoes, gestorReproducao, gestorEstatisticas);
        
        // Inicialização do menu principal
        EstadoUI estadoUI = new EstadoUI(scanner, gestorMusicas, gestorAlbuns, gestorPlaylists, utilizadores);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, gestorMusicas, gestorPlaylists, 
            gestorReproducao, gestorEstatisticas, gestorRecomendacoes, loginUI, estadoUI);
        
        // Execução do programa
        menuPrincipal.mostrarMenu();

        // Salvar dados antes de sair
        gestorPersistencia.salvarMusicas(gestorMusicas.listarMusicas());
        gestorPersistencia.salvarPlaylists(gestorPlaylists.listarPlaylists());
        gestorPersistencia.salvarUtilizadores(utilizadores);
        
        scanner.close();
    }
} 