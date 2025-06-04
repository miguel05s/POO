package main.java.ui;

import main.java.models.musica.Musica;
import main.java.models.utilizador.Utilizador;
import main.java.services.GestorRecomendacoes;
import java.util.List;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class RecomendacoesUI extends MenuBase {
    private final GestorRecomendacoes gestorRecomendacoes;
    private Utilizador utilizadorAtual;

    public RecomendacoesUI(Scanner scanner, GestorRecomendacoes gestorRecomendacoes) {
        super(scanner, "Recomendações");
        this.gestorRecomendacoes = gestorRecomendacoes;
    }

    public void setUtilizadorAtual(Utilizador utilizador) {
        this.utilizadorAtual = utilizador;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Ver Recomendações por Gênero");
        System.out.println("2. Ver Recomendações por Artista");
        System.out.println("3. Ver Recomendações por Popularidade");
        System.out.println("4. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> verRecomendacoesPorGenero();
            case 2 -> verRecomendacoesPorArtista();
            case 3 -> verRecomendacoesPorPopularidade();
            case 4 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 4;
    }

    private void verRecomendacoesPorGenero() {
        String genero = InputUtil.lerTexto(scanner, "Gênero: ");
        List<Musica> recomendacoes = gestorRecomendacoes.getRecomendacoesPorGenero(genero, utilizadorAtual);
        exibirRecomendacoes(recomendacoes, "Recomendações por Gênero: " + genero);
    }

    private void verRecomendacoesPorArtista() {
        String artista = InputUtil.lerTexto(scanner, "Artista: ");
        List<Musica> recomendacoes = gestorRecomendacoes.getRecomendacoesPorArtista(artista, utilizadorAtual);
        exibirRecomendacoes(recomendacoes, "Recomendações por Artista: " + artista);
    }

    private void verRecomendacoesPorPopularidade() {
        List<Musica> recomendacoes = gestorRecomendacoes.getRecomendacoesPorPopularidade(utilizadorAtual);
        exibirRecomendacoes(recomendacoes, "Músicas Populares");
    }

    private void exibirRecomendacoes(List<Musica> recomendacoes, String titulo) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("\n").append(titulo);
        
        if (recomendacoes.isEmpty()) {
            resultado.append("\nNenhuma recomendação encontrada.");
        } else {
            recomendacoes.forEach(musica -> 
                resultado.append("\n- ").append(musica.getNome()).append(" (").append(musica.getInterprete()).append(")"));
        }
        
        mostrarResultado(resultado.toString());
    }
}
