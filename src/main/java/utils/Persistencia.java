package main.java.utils;

import java.io.*;
import java.util.List;

public class Persistencia {
    public static <T> void guardar(String ficheiro, List<T> lista) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(lista);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregar(String ficheiro) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (List<T>) ois.readObject();
        }
    }
} 