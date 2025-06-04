package main.java.ui;

import main.java.models.musica.Musica;
import main.java.services.GestorMusicas;
import java.util.List;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class BibliotecaUI extends MenuBase {
    private final GestorMusicas gestorMusicas;

    public BibliotecaUI(Scanner scanner, GestorMusicas gestorMusicas) {
        super(scanner, "Biblioteca");
        this.gestorMusicas = gestorMusicas;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Listar Músicas");
        System.out.println("2. Buscar Música");
        System.out.println("3. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> listarMusicas();
            case 2 -> buscarMusica();
            case 3 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 3;
    }

    private void listarMusicas() {
        List<Musica> musicas = gestorMusicas.listarMusicas();
        StringBuilder resultado = new StringBuilder();
        
        if (musicas.isEmpty()) {
            resultado.append("Nenhuma música registada.");
        } else {
            musicas.forEach(m -> 
                resultado.append("\n- ").append(m.getNome()).append(" (").append(m.getInterprete()).append(")"));
        }
        
        mostrarResultado(resultado.toString());
    }

    private void buscarMusica() {
        String nome = InputUtil.lerTexto(scanner, "Nome da música: ");
        Musica musica = gestorMusicas.buscarMusica(nome);
        StringBuilder resultado = new StringBuilder();
        
        if (musica == null) {
            resultado.append("Música não encontrada.");
        } else {
            resultado.append("\nMúsica encontrada:")
                    .append("\nNome: ").append(musica.getNome())
                    .append("\nIntérprete: ").append(musica.getInterprete())
                    .append("\nDuração: ").append(musica.getDuracao()).append(" segundos")
                    .append("\nGénero: ").append(musica.getGenero());
        }
        
        mostrarResultado(resultado.toString());
    }
} 