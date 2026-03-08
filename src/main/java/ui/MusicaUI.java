package main.java.ui;

import main.java.models.musica.Musica;
import main.java.models.musica.MusicaExplicita;
import main.java.services.GestorMusicas;
import java.util.List;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class MusicaUI extends MenuBase {
    private final GestorMusicas gestorMusicas;

    public MusicaUI(Scanner scanner, GestorMusicas gestorMusicas) {
        super(scanner, "Gestão de Músicas");
        this.gestorMusicas = gestorMusicas;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Criar Música");
        System.out.println("2. Listar Músicas");
        System.out.println("3. Importar de JSON");
        System.out.println("4. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> criarMusica();
            case 2 -> listarMusicas();
            case 3 -> importarDeJSON();
            case 4 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 4;
    }

    private void criarMusica() {
        String nome = lerNome();
        if (gestorMusicas.existeMusica(nome)) {
            System.out.println("Já existe uma música com esse nome.");
            return;
        }

        String interprete = lerInterprete();
        String editora = lerEditora();
        String letra = lerLetra();
        String genero = lerGenero();
        int duracao = lerDuracao();
        boolean explicita = lerExplicita();

        Musica musica;
        if (explicita) {
            musica = new MusicaExplicita(nome, interprete, editora, letra, genero, duracao);
        } else {
            musica = new Musica(nome, interprete, editora, letra, genero, duracao);
        }
        gestorMusicas.adicionarMusica(musica);
        System.out.println("Música criada com sucesso!");
    }

    private String lerNome() {
        return InputUtil.lerTexto(scanner, "Nome da música: ");
    }

    private String lerInterprete() {
        return InputUtil.lerTexto(scanner, "Intérprete: ");
    }

    private String lerEditora() {
        return InputUtil.lerTexto(scanner, "Editora: ");
    }

    private String lerLetra() {
        return InputUtil.lerTexto(scanner, "Letra: ");
    }

    private String lerGenero() {
        return InputUtil.lerTexto(scanner, "Género: ");
    }

    private int lerDuracao() {
        while (true) {
            int duracao = InputUtil.lerInteiro(scanner, "Duração (segundos): ");
            if (duracao > 0) {
                return duracao;
            }
            System.out.println("Por favor, introduza uma duração válida (número positivo).");
        }
    }

    private boolean lerExplicita() {
        while (true) {
            String resposta = InputUtil.lerTexto(scanner, "A música é explícita? (s/n): ").toLowerCase();
            if (resposta.equals("s")) {
                return true;
            } else if (resposta.equals("n")) {
                return false;
            }
            System.out.println("Por favor, responda com 's' para sim ou 'n' para não.");
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = gestorMusicas.listarMusicas();
        StringBuilder resultado = new StringBuilder();
        
        if (musicas.isEmpty()) {
            resultado.append("Nenhuma música registada.");
        } else {
            musicas.forEach(m -> {
                resultado.append("\nNome: ").append(m.getNome())
                        .append("\nIntérprete: ").append(m.getInterprete())
                        .append("\nGênero: ").append(m.getGenero())
                        .append("\nDuração: ").append(m.getDuracao()).append(" segundos")
                        .append("\nReproduções: ").append(m.getReproducoes())
                        .append("\n");
            });
        }
        
        mostrarResultado(resultado.toString());
    }

    private void importarDeJSON() {
        String caminho = "src/main/resources/dados.json";
        if (gestorMusicas.importarDeJSON(caminho)) {
            mostrarResultado("Músicas importadas com sucesso!");
        } else {
            mostrarResultado("Erro ao importar músicas.");
        }
    }
}
