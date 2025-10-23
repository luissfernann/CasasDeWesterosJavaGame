package app;

import domain.Jogo; // Importa a classe Jogo que está no pacote 'domain'

public class Main {
    /**
     * Ponto de entrada da aplicação.
     */
    public static void main(String[] args) {

        // 1. Cria uma instância da classe Jogo
        Jogo meuJogo = new Jogo();

        // 2. Chama o método 'iniciar()' para começar a rodada de configuração e o loop do jogo
        meuJogo.iniciar();

        System.out.println("\nFim da Aplicação.");
    }
}