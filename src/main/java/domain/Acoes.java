package domain;
import java.lang.Math;

public class Acoes {

    public static int atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        //  Verificando o alcançe para caso haja uma possibilidade de ataque
        int dLinha = Math.abs(atacante.getLinha() - alvo.getLinha());
        int dColuna = Math.abs(atacante.getColuna() - alvo.getColuna());

        int distancia = Math.max(dLinha, dColuna);
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return 0;
        }

        Casa casaAtacante = atacante.getCasaPersonagem();
        Casa casaAlvo = alvo.getCasaPersonagem();

        // realizando um ataque
        double ataque = casaAtacante.getAtaqueBase() * (1 + casaAtacante.getModificadorOfensivo());
        double defesa = casaAlvo.getDefesaBase();

        // Caso especial para a casa TARGARYEN
        if (casaAtacante.getNome().equalsIgnoreCase("TARGARYEN")) {
            defesa = 0;
            System.out.println("  (TARGARYEN ignora a defesa!)");
        }

        double danoBruto = ataque - defesa;

        // Caso especial para a defesa usando o modificador Defensivo : casa STARK
        if (casaAlvo.getNome().equalsIgnoreCase("STARK")) {
            danoBruto *= 0.80;
            System.out.println("  (Defensor da casa STARK reduziu o dano em 20%.)");
        }

        double danoFinal = Math.max(danoBruto, 0);
        int danoArredondado = (int) Math.round(danoFinal);

        // Aplicação do dano + atualização da vida do personagem
        int vidaAnterior = alvo.getVidaAtual();
        int novaVida = vidaAnterior - danoArredondado;

        if (novaVida < 0) {
            novaVida = 0;
        }

        alvo.setVidaAtual(novaVida);

        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + danoArredondado + " de dano!");

        System.out.println("  " + alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");

        if (novaVida == 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            tabuleiro.removerPersonagem(alvo);
        }
        return danoArredondado;
    }
}