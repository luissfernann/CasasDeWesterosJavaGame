package domain;
import java.util.Random;
import java.util.List;
import java.util.Collections;

public class Bot extends personagem {

private Random random = new Random();

public void realizarJogada(Tabuleiro tabuleiro, Personagem [] timedoBot) {

    Personagem personagemEscolhido = escolherPersonagemAleatorio(timeDoBot);
    if (personagemEscolhido == null) {
        System.out.println ("Bot não tem personagem suficientes para joga");
        return;
    }
    System.out.println("--- Turno do Bot: " + personagemEscolhido.getNome() + " ---");

    realizarMovimentoAleatorio(tabuleiro, personagemEscolhido);

    System.out.println("Bot (" + personagemEscolhido.getNome() + ") procura um alvo para ataque");

    //FAlta logica de ataquye
}
    private Personagem escolherPersonagemAleatorio(Personagem[] timeDoBot) {
        List<Personagem> personagensVivos = new ArrayList<>();
        for (Personagem p : timeDoBot) {
            if (p != null && p.getVidaAtual() > 0) {
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
    }
     Collections.shuffle(direcoes); // embaralha os elementos de uma lista (P1ka!)
    boolean movimentoRealizado = false;
        for (int[] dir : direcoes) {
        int novaLinha = linhaAtual + dir[0];
        int novaColuna = colunaAtual + dir[1];
        if (tabuleiro.moverPersonagem(personagem, novaLinha, novaColuna)) {
            System.out.println("Bot moveu " + personagem.getNome() + " para (" + novaLinha + ", " + novaColuna + ").");
            movimentoRealizado = true;
            break;
        }
    }
        if (!movimentoRealizado) {
        System.out.println(personagem.getNome() + " não conseguiu se mover (cercado ou sem opções válidas).");
    }
    }

