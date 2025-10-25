package domain;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Jogo {

    // tivermos que mudar de array para List a entrada dos personagens para que eles pudessem ser
        // removidos do tabuleiro após morrer: VER O PORQUÊ DISSO PARA EXPLICAR NA APRESENTAÇÃO


    // falta :
        // mudar para List
        // posicionamento inicial


    private Tabuleiro tabuleiro;
    private Scanner scanner;

    // Lista para gerenciar personagens de cada time (simplificando para Times 1 e 2)
    private Personagem[] time1;
    private Personagem[] time2;
    private final int MAX_PERSONAGENS = 3; // Exemplo de limite

    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.scanner = new Scanner(System.in);
        // Inicializa os arrays de personagens com um tamanho fixo
        this.time1 = new Personagem[MAX_PERSONAGENS];
        this.time2 = new Personagem[MAX_PERSONAGENS];
    }

    /**
     * Ponto de entrada do jogo.
     */
    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("     Início do Jogo - A Batalha de Westeros");
        System.out.println("=========================================");

        int escolha = 0;
        while (escolha != 1 && escolha != 2) {
            System.out.println("Escolha o Modo de Jogo:");
            System.out.println("1. Jogador Humano vs. Jogador Humano");
            System.out.println("2. Jogador Humano vs. Bot (Máquina)");
            System.out.print("Sua escolha: ");
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
            }
            scanner.nextLine(); // Consumir a nova linha
            if (escolha != 1 && escolha != 2) {
                System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
            }
        }

        // 1. Configuração dos Personagens para o Time 1 (Sempre Humano)
        configurarTime(time1, "Jogador 1 (Time Stark/Lannister)");

        if (escolha == 1) {
            // 2. Configuração dos Personagens para o Time 2 (Humano)
            configurarTime(time2, "Jogador 2 (Time Targaryen/Lannister)");
            iniciarPartida(ModoJogo.HUMANO_VS_HUMANO);
        } else {
            // 2. Configuração dos Personagens para o Time 2 (Bot)
            configurarTimeBot(time2, "Bot (Máquina)"); // Método para criar o time do Bot
            iniciarPartida(ModoJogo.HUMANO_VS_BOT);
        }
    }

    // ====================================================================
    // MÉTODOS DE CONFIGURAÇÃO
    // ====================================================================

    private void configurarTime(Personagem[] time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " ---");
        // Exemplo: Criar 3 personagens para o time
        // OBS: Você precisará ter a Casa "Casa" disponível para a criação.

        // Exemplo simples de criação, você expandiria isso para escolher nome e casa
        Casa casaPadrao = Casa.getSTARK();
        Posicao posPadrao = new Posicao(0, 0); // Posição temporária antes de ir para o tabuleiro

        for (int i = 0; i < MAX_PERSONAGENS; i++) {
            System.out.print("Digite o nome para o Personagem " + (i + 1) + " (Casa " + casaPadrao.getNome() + "): ");
            String nome = scanner.nextLine();

            // Cria o personagem e o adiciona ao time
            time[i] = new Personagem(nome, casaPadrao, posPadrao);
        }
    }

    private void configurarTimeBot(Personagem[] time, String nomeJogador) {
        System.out.println("\n--- Configuração de " + nomeJogador + " (Automático) ---");
        // Exemplo: Bot sempre usa a casa TARGARYEN
        Casa casaBot = Casa.getTARGARYEN();
        Posicao posPadrao = new Posicao(0, 0);

        time[0] = new Personagem("Dragão", casaBot, posPadrao);
        time[1] = new Personagem("MãeDosDragões", casaBot, posPadrao);
        time[2] = new Personagem("Imaculado", casaBot, posPadrao);

        System.out.println("Time do Bot criado com sucesso.");
    }

    // ====================================================================
    // MÉTODO PRINCIPAL DO JOGO
    // ====================================================================

    private void iniciarPartida(ModoJogo modo) {
        System.out.println("\n--- INICIANDO PARTIDA: " + modo + " ---");

        // 1. Posicionar Personagens no Tabuleiro
        // OBS: Você precisa implementar o método Tabuleiro.posicicaoPInicial
        // Ex: tabuleiro.posicicaoPInicial(time1, time2);

        // 2. Loop principal do jogo
        // while (verificaVitoria() == null) {
        //
        //     // Turno do Time 1 (Sempre Humano)
        //     gerenciarTurnoHumano(time1, "Jogador 1");
        //
        //     // Checa vitória após o turno
        //     if (verificaVitoria() != null) break;
        //
        //     // Turno do Time 2
        //     if (modo == ModoJogo.HUMANO_VS_HUMANO) {
        //         gerenciarTurnoHumano(time2, "Jogador 2");
        //     } else {
        //         // OBS: É aqui que você usaria sua classe Bot
        //         Bot bot = new Bot();
        //         // bot.realizarJogada(tabuleiro, time2);
        //     }
        // }

        System.out.println("\nFIM DA DEMONSTRAÇÃO DO FLUXO.");
    }

    // ====================================================================
    // EXEMPLO DE GERENCIAMENTO DE TURNO
    // ====================================================================

    private void gerenciarTurnoHumano(Personagem[] time, String nomeJogador) {
        System.out.println("\n--- TURNO DE " + nomeJogador + " ---");

        // Exemplo: Seleciona o primeiro personagem vivo para o turno
        Personagem personagemAtivo = time[0]; // Lógica de seleção a ser implementada

        // 1. MOVIMENTO:
        // Lógica para pedir W, A, S, D e chamar tabuleiro.moviPersonagem

        // 2. ATAQUE:
        // Depois do movimento, se o jogador quiser, ele escolhe um alvo.
        // Personagem alvo = ... // Lógica de seleção de alvo

        // if (alvo != null) {
        //     Acoes.atacar(personagemAtivo, alvo, tabuleiro);
        // }

        tabuleiro.ImprimeTabuleiro();
    }


    // Enum para melhor legibilidade
    private enum ModoJogo {
        HUMANO_VS_HUMANO, HUMANO_VS_BOT
    }

    public static void main(String[] args) {
        // A classe Jogo precisa ser instanciada para começar
        new Jogo().iniciar();
    }
}