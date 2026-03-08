package main.java.utils;

import java.util.List;

public class UtilizadorDTO {
    public String nome;
    public String email;
    public String morada;
    public String tipo;
    public String senha;
    public List<ReproducaoDTO> historicoReproducoes;
}

class ReproducaoDTO {
    public String musica;
    public String dataHora;
    public int duracaoReproduzida;
} 