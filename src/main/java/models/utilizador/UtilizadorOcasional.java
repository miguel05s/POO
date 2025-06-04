package main.java.models.utilizador;

import java.math.BigDecimal;

public class UtilizadorOcasional extends Utilizador {
    public UtilizadorOcasional(String nome, String email, String senha) {
        super(nome, email, "", senha);
    }

    @Override
    public void adicionarPontos() {
        setPontos(getPontos().add(new BigDecimal("5"))); // Utilizadores ocasionais ganham 5 pontos por música
    }
}