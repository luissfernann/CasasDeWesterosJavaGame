package domain;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Bot {

    private Random random = new Random();

    public void realizarJogada(Tabuleiro tabuleiro, Personagem[] timeDoBot, Personagem[] timeInimigo) {

        Personagem personagemEscolhido = escolherPersonagemAleatorio(timeDoBot);
        if (personagemEscolhido == null) {
            System.out.println("Bot não tem personagens vivos para jogar.");
            return;
        }

        System.out.println("\n--- Turno do Bot: " + personagemEscolhido.getNomePersonagem() + " ---");

        // 1. Movimento
        realizarMovimentoAleatorio(tabuleiro, personagemEscolhido);

        // 2. Ataque
        Personagem alvo = escolherAlvoParaAtaque(personagemEscolhido, timeInimigo, tabuleiro);
        if (alvo != null) {
            Acoes.atacar(personagemEscolhido, alvo, tabuleiro);
        } else {
            System.out.println("Bot (" + personagemEscolhido.getNomePersonagem() + ") não encontrou um alvo no alcance para atacar.");
        }
    }

    private Personagem escolherAlvoParaAtaque(Personagem atacante, Personagem[] timeInimigo, Tabuleiro tabuleiro) {
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();
        List<Personagem> alvosValidos = new ArrayList<>();

        for (Personagem inimigo : timeInimigo) {
            if (inimigo != null && inimigo.estaVivo()) {
                int distancia = tabuleiro.calcularDistancia(atacante, inimigo);
                if (distancia <= alcance) {
                    alvosValidos.add(inimigo);
                }
            }
        }

        if (alvosValidos.isEmpty()) {
            return null;
        }

        // Escolhe alvo aleatório
        return alvosValidos.get(random.nextInt(alvosValidos.size()));
    }

    private Personagem escolherPersonagemAleatorio(Personagem[] timeDoBot) {
        List<Personagem> personagensVivos = new ArrayList<>();
        for (Personagem p : timeDoBot) {
            if (p != null && p.estaVivo()) {
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
                movimentoRealizado = true;
                break;
            }
        }

        if (!movimentoRealizado) {
            System.out.println(personagem.getNomePersonagem() + " não conseguiu se mover.");
        }
    }
}