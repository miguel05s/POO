package main.java.services;

import main.java.models.utilizador.Utilizador;
import main.java.models.utilizador.UtilizadorOcasional;
import main.java.models.utilizador.UtilizadorPremium;
import main.java.models.subscricao.PremiumBase;
import main.java.models.subscricao.PremiumTop;
import main.java.utils.DadosImportacao;
import main.java.utils.ImportadorJSON;
import java.util.ArrayList;
import java.util.List;

public class GestorUtilizadores {
    private final List<Utilizador> utilizadores;

    public GestorUtilizadores(List<Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public GestorUtilizadores() {
        this.utilizadores = new ArrayList<>();
    }

    public boolean importarDeJSON(String caminho) {
        DadosImportacao dados = ImportadorJSON.importar(caminho);
        if (dados == null || dados.utilizadores == null) {
            return false;
        }

        for (var dto : dados.utilizadores) {
            if (!existeUtilizador(dto.nome)) {
                Utilizador utilizador;
                switch (dto.tipo.toLowerCase()) {
                    case "premiumbase":
                        utilizador = new UtilizadorPremium(dto.nome, dto.email, dto.morada, dto.senha, new PremiumBase());
                        break;
                    case "premiumtop":
                    case "premiumplus":
                        utilizador = new UtilizadorPremium(dto.nome, dto.email, dto.morada, dto.senha, new PremiumTop());
                        break;
                    default:
                        utilizador = new UtilizadorOcasional(dto.nome, dto.email, dto.senha);
                }
                utilizadores.add(utilizador);
            }
        }
        return true;
    }

    public boolean existeUtilizador(String nome) {
        return utilizadores.stream()
                .anyMatch(u -> u.getNome().equalsIgnoreCase(nome));
    }

    public List<Utilizador> listarUtilizadores() {
        return new ArrayList<>(utilizadores);
    }

    public Utilizador buscarUtilizador(String email) {
        return utilizadores.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Utilizador fazerLogin(String email, String senha) {
        return utilizadores.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.verificarSenha(senha))
                .findFirst()
                .orElse(null);
    }

    public Utilizador registar(String nome, String senha, String email) {
        if (existeUtilizador(nome)) {
            return null;
        }

        Utilizador utilizador = new UtilizadorOcasional(nome, email, senha);
        utilizadores.add(utilizador);
        return utilizador;
    }

    public boolean atualizarUtilizador(String email, String novoEmail, String novaSenha) {
        Utilizador utilizador = buscarUtilizador(email);
        if (utilizador == null) {
            return false;
        }

        utilizador.setEmail(novoEmail);
        utilizador.setSenha(novaSenha);
        return true;
    }

    public boolean removerUtilizador(String email) {
        Utilizador utilizador = buscarUtilizador(email);
        if (utilizador == null) {
            return false;
        }

        return utilizadores.remove(utilizador);
    }

    public boolean mudarPlano(String email, String tipoPlano) {
        Utilizador utilizador = buscarUtilizador(email);
        if (utilizador == null) {
            return false;
        }

        // Verifica se o utilizador já tem o plano desejado
        if (utilizador instanceof UtilizadorPremium && 
            ((UtilizadorPremium) utilizador).getPlano().getNomePlano().equalsIgnoreCase(tipoPlano)) {
            return true; // Já tem o plano desejado
        }

        // Atualiza o plano do utilizador
        switch (tipoPlano.toLowerCase()) {
            case "free" -> {
                if (utilizador instanceof UtilizadorPremium) {
                    int index = utilizadores.indexOf(utilizador);
                    utilizadores.set(index, new UtilizadorOcasional(utilizador.getNome(), utilizador.getEmail(), utilizador.getSenha()));
                }
            }
            case "premiumbase" -> {
                if (!(utilizador instanceof UtilizadorPremium) || 
                    !((UtilizadorPremium) utilizador).getPlano().getNomePlano().equals("PremiumBase")) {
                    int index = utilizadores.indexOf(utilizador);
                    utilizadores.set(index, new UtilizadorPremium(utilizador.getNome(), utilizador.getEmail(), utilizador.getMorada(), utilizador.getSenha(), new PremiumBase()));
                }
            }
            case "premiumtop" -> {
                if (!(utilizador instanceof UtilizadorPremium) || 
                    !((UtilizadorPremium) utilizador).getPlano().getNomePlano().equals("PremiumTop")) {
                    int index = utilizadores.indexOf(utilizador);
                    utilizadores.set(index, new UtilizadorPremium(utilizador.getNome(), utilizador.getEmail(), utilizador.getMorada(), utilizador.getSenha(), new PremiumTop()));
                }
            }
            default -> {
                return false;
            }
        }
        return true;
    }
} 