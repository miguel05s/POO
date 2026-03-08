package main.java.services;

import main.java.models.musica.Musica;
import main.java.models.playlist.Playlist;
import main.java.models.utilizador.Utilizador;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;

public class GestorRecomendacoes {
    private final List<Musica> musicas;
    private final List<Playlist> playlists;
    private final GestorMusicas gestorMusicas;

    public GestorRecomendacoes(List<Musica> musicas, List<Playlist> playlists, GestorMusicas gestorMusicas) {
        this.musicas = musicas;
        this.playlists = playlists;
        this.gestorMusicas = gestorMusicas;
    }

    public List<Musica> recomendarMusicas(Utilizador usuario) {
        // Obter gêneros favoritos do usuário
        Map<String, Long> generosFavoritos = usuario.getHistoricoReproducoes().stream()
                .collect(Collectors.groupingBy(
                    Musica::getGenero,
                    Collectors.counting()
                ));

        // Ordenar gêneros por preferência
        List<String> generosOrdenados = generosFavoritos.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Filtrar músicas que o usuário ainda não ouviu
        Set<Musica> musicasOuvidas = new HashSet<>(usuario.getHistoricoReproducoes());
        
        return musicas.stream()
                .filter(musica -> !musicasOuvidas.contains(musica))
                .sorted((m1, m2) -> {
                    // Priorizar músicas dos gêneros favoritos
                    int index1 = generosOrdenados.indexOf(m1.getGenero());
                    int index2 = generosOrdenados.indexOf(m2.getGenero());
                    
                    if (index1 == -1) index1 = Integer.MAX_VALUE;
                    if (index2 == -1) index2 = Integer.MAX_VALUE;
                    
                    return Integer.compare(index1, index2);
                })
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Playlist> recomendarPlaylists(Utilizador usuario) {
        // Obter gêneros favoritos do usuário
        Set<String> generosFavoritos = usuario.getHistoricoReproducoes().stream()
                .map(Musica::getGenero)
                .collect(Collectors.toSet());

        return playlists.stream()
                .filter(playlist -> {
                    // Verificar se a playlist contém músicas dos gêneros favoritos
                    return playlist.getMusicas().stream()
                            .anyMatch(musica -> generosFavoritos.contains(musica.getGenero()));
                })
                .sorted((p1, p2) -> {
                    // Ordenar por número de músicas dos gêneros favoritos
                    long count1 = p1.getMusicas().stream()
                            .filter(m -> generosFavoritos.contains(m.getGenero()))
                            .count();
                    long count2 = p2.getMusicas().stream()
                            .filter(m -> generosFavoritos.contains(m.getGenero()))
                            .count();
                    return Long.compare(count2, count1);
                })
                .limit(5)
                .collect(Collectors.toList());
    }

    public String getRecomendacoesUsuario(Utilizador usuario) {
        StringBuilder recomendacoes = new StringBuilder();
        recomendacoes.append("=== Recomendações para ").append(usuario.getNome()).append(" ===\n\n");

        // Recomendações de músicas
        recomendacoes.append("Músicas Recomendadas:\n");
        List<Musica> musicasRecomendadas = recomendarMusicas(usuario);
        if (musicasRecomendadas.isEmpty()) {
            recomendacoes.append("Nenhuma recomendação de música disponível.\n");
        } else {
            musicasRecomendadas.forEach(musica ->
                recomendacoes.append("- ").append(musica.getNome())
                           .append(" (").append(musica.getGenero()).append(")\n")
            );
        }

        // Recomendações de playlists
        recomendacoes.append("\nPlaylists Recomendadas:\n");
        List<Playlist> playlistsRecomendadas = recomendarPlaylists(usuario);
        if (playlistsRecomendadas.isEmpty()) {
            recomendacoes.append("Nenhuma recomendação de playlist disponível.\n");
        } else {
            playlistsRecomendadas.forEach(playlist ->
                recomendacoes.append("- ").append(playlist.getNome())
                           .append(" (").append(playlist.getMusicas().size()).append(" músicas)\n")
            );
        }

        return recomendacoes.toString();
    }

    public List<Musica> getRecomendacoesPorGenero(String genero, Utilizador utilizador) {
        return gestorMusicas.listarMusicas().stream()
                .filter(m -> m.getGenero().equalsIgnoreCase(genero))
                .filter(m -> !utilizador.getHistoricoReproducoes().contains(m))
                .sorted(Comparator.comparingInt(Musica::getReproducoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Musica> getRecomendacoesPorArtista(String artista, Utilizador utilizador) {
        return gestorMusicas.listarMusicas().stream()
                .filter(m -> m.getInterprete().equalsIgnoreCase(artista))
                .filter(m -> !utilizador.getHistoricoReproducoes().contains(m))
                .sorted(Comparator.comparingInt(Musica::getReproducoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Musica> getRecomendacoesPorPopularidade(Utilizador utilizador) {
        return gestorMusicas.listarMusicas().stream()
                .filter(m -> !utilizador.getHistoricoReproducoes().contains(m))
                .sorted(Comparator.comparingInt(Musica::getReproducoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
