package domain;

import java.lang.Math;
//Math.abs, Math.max, Math.round

//realizando teste
public class Acoes {

    public static int atacar(Personagem atacante, Personagem alvo, Tabuleiro tabuleiro) {

        //  1º: verificando o alcançe para a possibilidade de um ataque, observando seu máximo, como seguinte as regras
        int dLinha = Math.abs(atacante.getLinha() - alvo.getLinha());
        int dColuna = Math.abs(atacante.getColuna() - alvo.getColuna());

        int distancia = Math.max(dLinha, dColuna);
        int alcance = atacante.getCasaPersonagem().getAlcanceMaximo();

        if (distancia > alcance) {
            System.out.println(atacante.getNomePersonagem() + " está fora do alcance para atacar " +
                    alvo.getNomePersonagem() + " (Distância: " + distancia + ", Alcance: " + alcance + ")");
            return 0;
        }

        // 2º: inicializamos o ataque e defesa, caso esteja tudo dentro do alcançe
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

        // 3º: aplicação do dano + atualização da vida do personagem
        int vidaAnterior = alvo.getVidaAtual();
        int novaVida = vidaAnterior - danoArredondado;

        if (novaVida < 0) {
            novaVida = 0;
        }

        alvo.setVidaAtual(novaVida);

        // 4º: registro
        System.out.println(atacante.getNomePersonagem() + " atacou " + alvo.getNomePersonagem() +
                " e causou " + danoArredondado + " de dano!");

        System.out.println("  " + alvo.getNomePersonagem() + " [Vida: " + vidaAnterior + " -> " + novaVida + "]");



        // 5º: verificando se por acaso podemos remover do tabuleiro : vida = 0
        if (novaVida == 0) {
            System.out.println("\n*** " + alvo.getNomePersonagem() + " FOI DERROTADO! ***");
            tabuleiro.removerPersonagem(alvo);
        }

        return danoArredondado;
    }
}