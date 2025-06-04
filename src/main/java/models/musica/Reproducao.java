package main.java.models.musica;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reproducao implements Serializable {
    private final Musica musica;
    private final LocalDateTime dataHora;
    private final String emailUtilizador;

    public Reproducao(Musica musica, String emailUtilizador) {
        this.musica = musica;
        this.dataHora = LocalDateTime.now();
        this.emailUtilizador = emailUtilizador;
    }

    public Musica getMusica() {
        return musica;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getEmailUtilizador() {
        return emailUtilizador;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", 
            dataHora.toString(), 
            musica.getNome(), 
            emailUtilizador);
    }
} 