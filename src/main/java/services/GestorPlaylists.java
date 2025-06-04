package main.java.services;

import main.java.models.playlist.*;
import main.java.models.musica.Musica;
import main.java.models.utilizador.Utilizador;
import main.java.utils.DadosImportacao;
import main.java.utils.ImportadorJSON;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorPlaylists {
    private List<Playlist> playlists = new ArrayList<>();
    private final GestorMusicas gestorMusicas;
    
    public GestorPlaylists() {
        this.gestorMusicas = new GestorMusicas();
    }
    
    public GestorPlaylists(GestorMusicas gestorMusicas) {
        this.gestorMusicas = gestorMusicas;
    }
    
    public void adicionarPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
    
    public List<Playlist> listarPlaylists() {
        return new ArrayList<>(playlists);
    }
    
    public boolean existePlaylist(String nome) {
        return playlists.stream().anyMatch(p -> p.getNome().equalsIgnoreCase(nome));
    }
    
    public Playlist buscarPlaylist(String nome) {
        return playlists.stream()
                       .filter(p -> p.getNome().equalsIgnoreCase(nome))
                       .findFirst()
                       .orElse(null);
    }

    public boolean importarDeJSON(String caminho) {
        DadosImportacao dados = ImportadorJSON.importar(caminho);
        if (dados == null || dados.playlists == null) {
            return false;
        }

        for (var dto : dados.playlists) {
            if (!existePlaylist(dto.nome)) {
                Playlist playlist = new PlaylistPersonalizada(dto.nome, dto.publica, false);
                
                // Adicionar músicas à playlist
                for (String nomeMusica : dto.musicas) {
                    Musica musica = gestorMusicas.buscarMusica(nomeMusica);
                    if (musica != null) {
                        playlist.adicionarMusica(musica);
                    }
                }
                
                adicionarPlaylist(playlist);
            }
        }
        return true;
    }

    public Playlist criarPlaylistPersonalizada(String nome, boolean publica, boolean aleatoria, String emailCriador) {
        if (existePlaylist(nome)) {
            throw new IllegalArgumentException("Já existe uma playlist com esse nome.");
        }
        Playlist playlist = new PlaylistPersonalizada(nome, publica, aleatoria);
        playlist.setCriador(emailCriador);
        adicionarPlaylist(playlist);
        return playlist;
    }

    public Playlist criarPlaylistAleatoria(String nome, String emailCriador) {
        if (existePlaylist(nome)) {
            throw new IllegalArgumentException("Já existe uma playlist com esse nome.");
        }
        Playlist playlist = new PlaylistAleatoria(nome);
        playlist.setCriador(emailCriador);
        adicionarPlaylist(playlist);
        return playlist;
    }

    public Playlist criarPlaylistPorGeneroEDuracao(String nome, List<Musica> todas, String genero, int maxSegundos, String emailCriador) {
        if (existePlaylist(nome)) {
            throw new IllegalArgumentException("Já existe uma playlist com esse nome.");
        }
        Playlist playlist = new PlaylistPorGeneroEDuracao(nome, todas, genero, maxSegundos);
        playlist.setCriador(emailCriador);
        adicionarPlaylist(playlist);
        return playlist;
    }

    public void adicionarMusicaAPlaylist(String nomePlaylist, Musica musica) {
        Playlist playlist = buscarPlaylist(nomePlaylist);
        if (playlist == null) {
            throw new IllegalArgumentException("Playlist não encontrada.");
        }
        playlist.adicionarMusica(musica);
    }

    public String obterInfoPlaylist(String nome) {
        Playlist playlist = buscarPlaylist(nome);
        if (playlist == null) {
            return "Playlist não encontrada.";
        }
        
        StringBuilder info = new StringBuilder();
        info.append("Playlist: ").append(playlist.getNome()).append("\n");
        info.append("Tipo: ").append(playlist.getClass().getSimpleName()).append("\n");
        info.append("Pública: ").append(playlist.isPublica()).append("\n");
        info.append("Número de músicas: ").append(playlist.getMusicas().size()).append("\n");
        info.append("Duração total: ").append(playlist.getDuracaoTotal()).append(" segundos\n");
        info.append("Músicas:\n");
        for (Musica m : playlist.getMusicas()) {
            info.append(" - ").append(m.getNome()).append("\n");
        }
        return info.toString();
    }

    public String listarPlaylistsComMusicas() {
        if (playlists.isEmpty()) {
            return "Nenhuma playlist registada.";
        }
        
        StringBuilder lista = new StringBuilder();
        for (Playlist p : playlists) {
            lista.append(obterInfoPlaylist(p.getNome())).append("\n");
        }
        return lista.toString();
    }

    public List<Playlist> listarPlaylistsPublicas() {
        return playlists.stream()
                       .filter(Playlist::isPublica)
                       .collect(Collectors.toList());
    }

    public List<Playlist> listarPlaylistsDoUtilizador(Utilizador utilizador) {
        return playlists.stream()
                       .filter(p -> p.getCriador().equals(utilizador.getEmail()))
                       .collect(Collectors.toList());
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists.clear();
        this.playlists.addAll(playlists);
    }
}
