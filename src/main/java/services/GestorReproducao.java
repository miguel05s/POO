package main.java.services;

import main.java.models.musica.Musica;
import main.java.models.musica.Reproducao;
import main.java.models.playlist.Playlist;
import main.java.models.utilizador.Utilizador;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class GestorReproducao {
    private final List<Musica> filaReproducao;
    private final List<Reproducao> historicoReproducoes;
    private int indiceAtual;
    private boolean reproduzindo;
    private Utilizador utilizadorAtual;

    public GestorReproducao() {
        this.filaReproducao = new ArrayList<>();
        this.historicoReproducoes = new ArrayList<>();
        this.indiceAtual = -1;
        this.reproduzindo = false;
    }

    public void setUtilizadorAtual(Utilizador utilizador) {
        this.utilizadorAtual = utilizador;
    }

    public void reproduzirMusica(Musica musica, Utilizador utilizador) {
        filaReproducao.clear();
        filaReproducao.add(musica);
        indiceAtual = 0;
        reproduzindo = true;
        musica.reproduzir();
        if (utilizador != null) {
            historicoReproducoes.add(new Reproducao(musica, utilizador.getEmail()));
        }
    }

    public void reproduzirPlaylist(Playlist playlist, Utilizador utilizador) {
        filaReproducao.clear();
        filaReproducao.addAll(playlist.getMusicas());
        indiceAtual = 0;
        reproduzindo = true;
        System.out.println("Reproduzindo playlist: " + playlist.getNome());
        playlist.reproduzir();
        if (utilizador != null) {
            for (Musica musica : playlist.getMusicas()) {
                historicoReproducoes.add(new Reproducao(musica, utilizador.getEmail()));
            }
        }
    }

    public void pausarRetomar() {
        if (indiceAtual >= 0 && indiceAtual < filaReproducao.size()) {
            reproduzindo = !reproduzindo;
            System.out.println(reproduzindo ? "Retomando reprodução" : "Reprodução pausada");
        }
    }

    public void parar() {
        reproduzindo = false;
        indiceAtual = -1;
        System.out.println("Reprodução parada");
    }

    public void proximaMusica() {
        if (indiceAtual < filaReproducao.size() - 1) {
            indiceAtual++;
            reproduzindo = true;
            Musica musica = filaReproducao.get(indiceAtual);
            musica.reproduzir();
            if (utilizadorAtual != null) {
                historicoReproducoes.add(new Reproducao(musica, utilizadorAtual.getEmail()));
            }
        } else {
            System.out.println("Não há mais músicas na fila");
        }
    }

    public void musicaAnterior() {
        if (indiceAtual > 0) {
            indiceAtual--;
            reproduzindo = true;
            Musica musica = filaReproducao.get(indiceAtual);
            musica.reproduzir();
            if (utilizadorAtual != null) {
                historicoReproducoes.add(new Reproducao(musica, utilizadorAtual.getEmail()));
            }
        } else {
            System.out.println("Não há músicas anteriores");
        }
    }

    public boolean estaReproduzindo() {
        return reproduzindo;
    }

    public Musica getMusicaAtual() {
        if (indiceAtual >= 0 && indiceAtual < filaReproducao.size()) {
            return filaReproducao.get(indiceAtual);
        }
        return null;
    }

    public List<Musica> getFilaReproducao() {
        return new ArrayList<>(filaReproducao);
    }

    public List<Reproducao> getHistoricoReproducoes() {
        return new ArrayList<>(historicoReproducoes);
    }

    public List<Reproducao> getHistoricoReproducoesPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return historicoReproducoes.stream()
            .filter(r -> !r.getDataHora().isBefore(inicio) && !r.getDataHora().isAfter(fim))
            .collect(Collectors.toList());
    }

    public String getUtilizadorMaisAtivo(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Long> reproducoesPorUtilizador = getHistoricoReproducoesPorPeriodo(inicio, fim).stream()
            .collect(Collectors.groupingBy(
                Reproducao::getEmailUtilizador,
                Collectors.counting()
            ));

        return reproducoesPorUtilizador.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("Nenhum utilizador encontrado");
    }
}

