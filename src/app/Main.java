package app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            MethodesAffichage m = new MethodesAffichage(sc);
            do {
                m.choisir();
            } while (!m.finir());
        }
    }
}
