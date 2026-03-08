package main.java.models.playlist;

import main.java.models.musica.Musica;

import java.util.List;

public class PlaylistPorGeneroEDuracao extends Playlist {

    public PlaylistPorGeneroEDuracao(String nome, List<Musica> todas, String genero, int maxSegundos) {
        super(nome);
        this.publica = false;
        int acumulado = 0;
        for (Musica m : todas) {
            if (m.getGenero().equalsIgnoreCase(genero) && acumulado + m.getDuracao() <= maxSegundos) {
                musicas.add(m);
                acumulado += m.getDuracao();
            }
        }
    }

    @Override
    public void reproduzir() {
        for (Musica m : musicas) {
            m.reproduzir();
        }
    }
}