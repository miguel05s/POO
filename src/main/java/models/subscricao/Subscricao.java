package main.java.models.subscricao;

import main.java.models.utilizador.Utilizador;

import java.math.BigDecimal;

public interface Subscricao {
    String getNomePlano();
    boolean podeCriarPlaylist();
    boolean podeAvancarMusica();
    boolean podeRetrocederMusica();
    boolean podeGerarPlaylistFavoritos();
    /**
     * Retorna os pontos iniciais atribuídos a um utilizador quando adere a este plano.
     * Por omissão, devolve 0.
     */
    default int pontosIniciais() {
        return 0; // Valor padrão para planos sem pontos iniciais
    }

    default BigDecimal calcularPontos(Utilizador utilizador) {
        return new BigDecimal(0); // Valor padrão para planos que não atribuem pontos adicionais
    }
}
