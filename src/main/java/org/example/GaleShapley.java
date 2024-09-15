package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GaleShapley {
    public static int[] galeShapley(int[][] preferenciasHomens, int[][] preferenciasMulheres, int n) {
        List<Integer> homensSolteiros = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            homensSolteiros.add(i);
        }

        int[] parceiroHomem = new int[n];
        int[] parceiraMulher = new int[n];
        int[] proximaEscolhaHomem = new int[n];

        // Inicializando arrays com valores nulos
        for (int i = 0; i < n; i++) {
            parceiroHomem[i] = -1;
            parceiraMulher[i] = -1;
            proximaEscolhaHomem[i] = 0;
        }

        while (!homensSolteiros.isEmpty()) {
            int homem = homensSolteiros.get(0);
            int[] preferenciasHomem = preferenciasHomens[homem];
            int mulher = preferenciasHomem[proximaEscolhaHomem[homem]];
            int[] preferenciasMulher = preferenciasMulheres[mulher];
            int maridoAtual = parceiraMulher[mulher];

            if (maridoAtual == -1) {
                parceiroHomem[homem] = mulher;
                parceiraMulher[mulher] = homem;
                homensSolteiros.remove(0);
            } else if (indiceDe(preferenciasMulher, maridoAtual) > indiceDe(preferenciasMulher, homem)) {
                parceiroHomem[maridoAtual] = -1;
                parceiroHomem[homem] = mulher;
                parceiraMulher[mulher] = homem;
                homensSolteiros.remove(0);
                homensSolteiros.add(maridoAtual);
            }
            proximaEscolhaHomem[homem]++;
        }

        return parceiroHomem;
    }

    private static int indiceDe(int[] arr, int valor) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == valor) {
                return i;
            }
        }
        return -1;  // Retorna -1 se o valor não for encontrado
    }

    public static void gerarPreferenciasAleatorias(int[][] preferencias, int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            List<Integer> lista = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                lista.add(j);
            }
            Collections.shuffle(lista, random); // Embaralha a lista aleatoriamente
            for (int j = 0; j < n; j++) {
                preferencias[i][j] = lista.get(j);
            }
        }
    }

    public static void main(String[] args) {
        int n = 10;  // Número de casais

        // Inicializar arrays de preferências
        int[][] preferenciasHomens = new int[n][n];
        int[][] preferenciasMulheres = new int[n][n];

        // Gerar preferências aleatórias
        gerarPreferenciasAleatorias(preferenciasHomens, n);
        gerarPreferenciasAleatorias(preferenciasMulheres, n);

        // Executa o algoritmo de Gale-Shapley
        int[] resultado = galeShapley(preferenciasHomens, preferenciasMulheres, n);

        // Imprime a lista de pares
        System.out.println("Lista de pares:");
        for (int i = 0; i < resultado.length; i++) {
            System.out.println("Homem " + i + " com Mulher " + resultado[i]);
        }

        // Imprime com formato "homem*" e "mulher*"
        System.out.println("\nCorrespondências detalhadas:");
        for (int i = 0; i < resultado.length; i++) {
            System.out.println("homem" + i + " com mulher" + resultado[i]);
        }

        // Exibe as preferências geradas aleatoriamente (opcional)
        System.out.println("\nPreferências dos homens:");
        for (int i = 0; i < n; i++) {
            System.out.print("Homem " + i + ": ");
            for (int j = 0; j < n; j++) {
                System.out.print(preferenciasHomens[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nPreferências das mulheres:");
        for (int i = 0; i < n; i++) {
            System.out.print("Mulher " + i + ": ");
            for (int j = 0; j < n; j++) {
                System.out.print(preferenciasMulheres[i][j] + " ");
            }
            System.out.println();
        }
    }
}
