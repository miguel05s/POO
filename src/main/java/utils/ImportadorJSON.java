package main.java.utils;

import com.google.gson.Gson;
import java.io.FileReader;

public class ImportadorJSON {
    public static DadosImportacao importar(String caminho) {
        try (FileReader reader = new FileReader(caminho)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, DadosImportacao.class);
        } catch (Exception e) {
            System.out.println("Erro ao importar JSON: " + e.getMessage());
            return null;
        }
    }
}