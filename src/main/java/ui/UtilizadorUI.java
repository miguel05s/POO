package main.java.ui;

import main.java.models.utilizador.Utilizador;
import main.java.models.utilizador.UtilizadorPremium;
import main.java.services.GestorUtilizadores;
import java.util.Scanner;
import main.java.utils.InputUtil;

public class UtilizadorUI extends MenuBase {
    private final GestorUtilizadores gestorUtilizadores;

    public UtilizadorUI(Scanner scanner, GestorUtilizadores gestorUtilizadores) {
        super(scanner, "Gestão de Utilizadores");
        this.gestorUtilizadores = gestorUtilizadores;
    }

    @Override
    protected void mostrarOpcoes() {
        System.out.println("1. Listar Utilizadores");
        System.out.println("2. Ver Detalhes do Utilizador");
        System.out.println("3. Editar Utilizador");
        System.out.println("4. Remover Utilizador");
        System.out.println("5. Mudar Plano de Subscrição");
        System.out.println("6. Importar Utilizadores do JSON");
        System.out.println("7. Voltar");
    }

    @Override
    protected void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> listarUtilizadores();
            case 2 -> verDetalhesUtilizador();
            case 3 -> editarUtilizador();
            case 4 -> removerUtilizador();
            case 5 -> mudarPlano();
            case 6 -> importarDeJSON();
            case 7 -> System.out.println("A voltar ao menu principal...");
            default -> System.out.println("Opção inválida!");
        }
    }

    @Override
    protected boolean deveSair(int opcao) {
        return opcao == 7;
    }

    private void listarUtilizadores() {
        System.out.println("\nLista de Utilizadores:");
        gestorUtilizadores.listarUtilizadores().forEach(utilizador -> 
            System.out.println("- " + utilizador.getNome() + " (" + utilizador.getEmail() + ")"));
    }

    private void verDetalhesUtilizador() {
        String email = InputUtil.lerTexto(scanner, "Email do utilizador: ");
        Utilizador utilizador = gestorUtilizadores.buscarUtilizador(email);
        if (utilizador != null) {
            System.out.println("\nDetalhes do Utilizador:");
            System.out.println("Nome: " + utilizador.getNome());
            System.out.println("Email: " + utilizador.getEmail());
            System.out.println("Data de Registro: " + utilizador.getDataRegistro());
            if (utilizador instanceof UtilizadorPremium) {
                System.out.println("Plano: " + ((UtilizadorPremium) utilizador).getPlano().getNomePlano());
            } else {
                System.out.println("Plano: Free");
            }
        } else {
            System.out.println("Utilizador não encontrado.");
        }
    }

    private void editarUtilizador() {
        String email = InputUtil.lerTexto(scanner, "Email do utilizador: ");
        Utilizador utilizador = gestorUtilizadores.buscarUtilizador(email);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        String novoEmail = InputUtil.lerTexto(scanner, "Novo email: ");
        String novaSenha = InputUtil.lerTexto(scanner, "Nova senha: ");

        if (gestorUtilizadores.atualizarUtilizador(email, novoEmail, novaSenha)) {
            System.out.println("Utilizador atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar utilizador.");
        }
    }

    private void removerUtilizador() {
        String email = InputUtil.lerTexto(scanner, "Email do utilizador: ");
        if (gestorUtilizadores.removerUtilizador(email)) {
            System.out.println("Utilizador removido com sucesso!");
        } else {
            System.out.println("Erro ao remover utilizador.");
        }
    }

    private void mudarPlano() {
        String email = InputUtil.lerTexto(scanner, "Email do utilizador: ");
        Utilizador utilizador = gestorUtilizadores.buscarUtilizador(email);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        System.out.println("\nPlanos disponíveis:");
        System.out.println("1. Free");
        System.out.println("2. Premium Base");
        System.out.println("3. Premium Top");
        
        int opcao = InputUtil.lerInteiro(scanner, "Escolha o novo plano (1-3): ");
        String tipoPlano;
        
        switch (opcao) {
            case 1 -> tipoPlano = "free";
            case 2 -> tipoPlano = "premiumbase";
            case 3 -> tipoPlano = "premiumtop";
            default -> {
                System.out.println("Opção inválida!");
                return;
            }
        }

        if (gestorUtilizadores.mudarPlano(email, tipoPlano)) {
            System.out.println("Plano alterado com sucesso!");
        } else {
            System.out.println("Erro ao alterar plano.");
        }
    }

    private void importarDeJSON() {
        String caminho = "src/main/resources/dados.json";
        if (gestorUtilizadores.importarDeJSON(caminho)) {
            System.out.println("Utilizadores importados com sucesso!");
        } else {
            System.out.println("Erro ao importar utilizadores.");
        }
    }
}
