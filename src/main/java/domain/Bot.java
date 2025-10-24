package domain;

import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList; // CORREÇÃO: Import faltando

public class Bot {

    private Random random = new Random();


    public void realizarJogada(Tabuleiro tabuleiro, Personagem[] timedoBot, Personagem[] timeInimigo) {

        Personagem personagemEscolhido = escolherPersonagemAleatorio(timedoBot);
        if (personagemEscolhido == null) {
            System.out.println("Bot não tem personagem suficientes para joga");
            return;
        }

        System.out.println("--- Turno do Bot: " + personagemEscolhido.getNomePersonagem() + " ---");

        realizarMovimentoAleatorio(tabuleiro, personagemEscolhido);


        System.out.println("Bot (" + personagemEscolhido.getNomePersonagem() + ") procura um alvo para ataque...");


        Personagem alvo = encontrarAlvoMaisProximo(personagemEscolhido, timeInimigo);

        if (alvo != null) {

            int distancia = personagemEscolhido.getPosicaoPersonagem().CalcularDistancia(alvo.getPosicaoPersonagem());


            if (distancia <= personagemEscolhido.getAlcanceMaximo()) {
                System.out.println(personagemEscolhido.getNomePersonagem() +
                        " ATACA " + alvo.getNomePersonagem() +
                        " (Distância: " + distancia + ", Alcance: " + personagemEscolhido.getAlcanceMaximo() + ")");

                //
                // AQUI VOCÊ DEVE CHAMAR SEU MÉTODO DE ATAQUE (ex: Acoes.realizarAtaque(personagemEscolhido, alvo))
                //

            } else {
                System.out.println(alvo.getNomePersonagem() + " é o mais próximo, mas está fora de alcance (Distância: " + distancia + ").");
            }
        } else {
            System.out.println("Nenhum alvo inimigo vivo encontrado.");
        }
    }


    private Personagem encontrarAlvoMaisProximo(Personagem atacante, Personagem[] timeInimigo) {
        Personagem alvoMaisProximo = null;
        int menorDistancia = Integer.MAX_VALUE;

        for (Personagem inimigo : timeInimigo) {

            if (inimigo != null && inimigo.isVivo()) {
                int distancia = atacante.getPosicaoPersonagem().CalcularDistancia(inimigo.getPosicaoPersonagem());

                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    alvoMaisProximo = inimigo;
                }
            }
        }
        return alvoMaisProximo;
    }

    private Personagem escolherPersonagemAleatorio(Personagem[] timeDoBot) {
        List<Personagem> personagensVivos = new ArrayList<>();
        for (Personagem p : timeDoBot) {

            if (p != null && p.isVivo()) {
                personagensVivos.add(p);
            }
        }

        if (personagensVivos.isEmpty()) {
            return null;
        }
        return personagensVivos.get(random.nextInt(personagensVivos.size()));
    }

    private void realizarMovimentoAleatorio(Tabuleiro tabuleiro, Personagem personagem) {
        int linhaAtual = personagem.getLinha();
        int colunaAtual = personagem.getColuna();
        List<int[]> direcoes = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                direcoes.add(new int[]{i, j});
            }
        }

        Collections.shuffle(direcoes);
        boolean movimentoRealizado = false;
        for (int[] dir : direcoes) {
            int novaLinha = linhaAtual + dir[0];
            int novaColuna = colunaAtual + dir[1];
            if (tabuleiro.moverPersonagem(personagem, novaLinha, novaColuna)) {

                System.out.println("Bot moveu " + personagem.getNomePersonagem() + " para (" + novaLinha + ", " + novaColuna + ").");
                movimentoRealizado = true;
                break;
            }
        }
        if (!movimentoRealizado) {

            System.out.println(personagem.getNomePersonagem() + " não conseguiu se mover (cercado ou sem opções válidas).");
        }
    }
}