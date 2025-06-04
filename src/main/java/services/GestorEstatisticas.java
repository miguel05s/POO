package main.java.services;

import main.java.models.musica.Musica;
import main.java.models.musica.Reproducao;
import main.java.models.playlist.Playlist;
import main.java.models.utilizador.Utilizador;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestorEstatisticas {
    private final GestorMusicas gestorMusicas;
    private final GestorPlaylists gestorPlaylists;
    private final GestorReproducao gestorReproducao;
    private final List<Utilizador> utilizadores;

    public GestorEstatisticas(GestorMusicas gestorMusicas, GestorPlaylists gestorPlaylists, 
                             GestorReproducao gestorReproducao, List<Utilizador> utilizadores) {
        this.gestorMusicas = gestorMusicas;
        this.gestorPlaylists = gestorPlaylists;
        this.gestorReproducao = gestorReproducao;
        this.utilizadores = utilizadores;
    }

    public int getTotalMusicas() {
        return gestorMusicas.listarMusicas().size();
    }

    public List<Musica> getMusicasMaisReproduzidas() {
        return gestorMusicas.listarMusicas().stream()
                .sorted(Comparator.comparingInt(Musica::getReproducoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Musica> getMusicasMaisReproduzidasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        Map<Musica, Long> reproducoesPorMusica = gestorReproducao.getHistoricoReproducoesPorPeriodo(inicio, fim).stream()
            .collect(Collectors.groupingBy(
                Reproducao::getMusica,
                Collectors.counting()
            ));

        return reproducoesPorMusica.entrySet().stream()
            .sorted(Map.Entry.<Musica, Long>comparingByValue().reversed())
            .limit(10)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public String getInterpreteMaisEscutado() {
        return gestorMusicas.listarMusicas().stream()
                .collect(Collectors.groupingBy(
                    Musica::getInterprete,
                    Collectors.summingInt(Musica::getReproducoes)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum intérprete encontrado");
    }

    public String getInterpreteMaisEscutadoPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> reproducoesPorInterprete = gestorReproducao.getHistoricoReproducoesPorPeriodo(inicio, fim).stream()
            .collect(Collectors.groupingBy(
                r -> r.getMusica().getInterprete(),
                Collectors.counting()
            ));

        return reproducoesPorInterprete.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Nenhum intérprete encontrado");
    }

    public String getUtilizadorMaisAtivo() {
        return gestorReproducao.getUtilizadorMaisAtivo(
            LocalDateTime.MIN, 
            LocalDateTime.MAX
        );
    }

    public String getUtilizadorMaisAtivoPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return gestorReproducao.getUtilizadorMaisAtivo(inicio, fim);
    }

    public void exibirEstatisticasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        System.out.println("\nEstatísticas do período " + inicio + " até " + fim);
        System.out.println("\nMúsicas mais reproduzidas:");
        getMusicasMaisReproduzidasPorPeriodo(inicio, fim).forEach(m -> 
            System.out.println("- " + m.getNome() + " (" + m.getInterprete() + ")"));
        
        System.out.println("\nIntérprete mais escutado: " + getInterpreteMaisEscutadoPorPeriodo(inicio, fim));
        System.out.println("Utilizador mais ativo: " + getUtilizadorMaisAtivoPorPeriodo(inicio, fim));
    }

    public Utilizador getUtilizadorMaisAtivoPorHistorico() {
        return utilizadores.stream()
                .max(Comparator.comparingInt(u -> u.getHistoricoReproducoes().size()))
                .orElse(null);
    }

    public Utilizador getUtilizadorComMaisPontos() {
        return utilizadores.stream()
                .max(Comparator.comparing(u -> u.getPontos()))
                .orElse(null);
    }

    public String getGeneroMaisReproduzido() {
        return gestorMusicas.listarMusicas().stream()
                .collect(Collectors.groupingBy(
                    Musica::getGenero,
                    Collectors.summingInt(Musica::getReproducoes)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Nenhum gênero encontrado");
    }

    public int getTotalPlaylists() {
        return gestorPlaylists.listarPlaylists().size();
    }

    public int getTotalPlaylistsPublicas() {
        return (int) gestorPlaylists.listarPlaylists().stream()
                .filter(Playlist::isPublica)
                .count();
    }

    public List<Playlist> getPlaylistsMaisPopulares() {
        return gestorPlaylists.listarPlaylists().stream()
                .sorted(Comparator.comparingInt(Playlist::getReproducoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public Utilizador getUtilizadorComMaisPlaylists() {
        return utilizadores.stream()
                .max(Comparator.comparing(u -> gestorPlaylists.listarPlaylists().stream()
                        .filter(p -> p.getCriador().equals(u.getNome()))
                        .count()))
                .orElse(null);
    }

    public int getTotalUtilizadores() {
        return utilizadores.size();
    }

    public List<Map.Entry<Utilizador, Integer>> getUtilizadoresMaisAtivos() {
        return utilizadores.stream()
                .collect(Collectors.toMap(
                        u -> u,
                        u -> u.getHistoricoReproducoes().size()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Utilizador, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
