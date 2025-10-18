package app;

public class NotasObservacoesAjudas {

    // Remover classe no final do projeto

    /*

        Luís
            As classes até então criadas são àquelas em que já é prevista sua funcionalidade.

            Vamos definindo com o tempo o que implementar e, se possível,
            repassar notas quanto à funcionalidade do que foi implementado.

            Pode deixar o máximo de comentários possível, não há problema.
            É bom que dá para interagir.

            Isso também pode ser feito ao subir no github.

            Git Teste: add, commit, push
            Estamos utilizando a branch master
                veja a branch atual: git branch

 CASA
package modelo;

public class Casa {
    private String nome;
    private int vidaMaxima;
    private int ataqueBase;
    private int defesaBase;
    private int alcance;
    private double modificadorDefensivo;
    private double modificadorOfensivo;

    // Construtor
    public Casa(String nome, int vidaMaxima, int ataqueBase, int defesaBase,
                int alcance, double modificadorDefensivo, double modificadorOfensivo) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.ataqueBase = ataqueBase;
        this.defesaBase = defesaBase;
        this.alcance = alcance;
        this.modificadorDefensivo = modificadorDefensivo;
        this.modificadorOfensivo = modificadorOfensivo;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public int getDefesaBase() {
        return defesaBase;
    }

    public int getAlcance() {
        return alcance;
    }

    public double getModificadorDefensivo() {
        return modificadorDefensivo;
    }

    public double getModificadorOfensivo() {
        return modificadorOfensivo;
    }

    // Método estático para obter as casas pré-definidas
    public static Casa getCasaStark() {
        return new Casa("STARK", 60, 20, 10, 1, 0.20, 0);
    }

    public static Casa getCasaLannister() {
        return new Casa("LANNISTER", 50, 20, 10, 2, 0, 0.15);
    }

    public static Casa getCasaTargaryen() {
        return new Casa("TARGARYEN", 45, 20, 10, 3, 0, 0);
    }

    @Override
    public String toString() {
        return nome;
    }
}










PERSONAGEM

package modelo;

public class Personagem {
    private String nome;
    private Casa casa;
    private int vidaAtual;
    private int linha;
    private int coluna;
    private boolean vivo;

    // Construtor
    public Personagem(String nome, Casa casa, int linha, int coluna) {
        this.nome = nome;
        this.casa = casa;
        this.linha = linha;
        this.coluna = coluna;
        this.vidaAtual = casa.getVidaMaxima(); // Começa com vida máxima
        this.vivo = true;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public Casa getCasa() {
        return casa;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public boolean isVivo() {
        return vivo;
    }

    // Métodos que delegam para a Casa
    public int getVidaMaxima() {
        return casa.getVidaMaxima();
    }

    public int getAtaqueBase() {
        return casa.getAtaqueBase();
    }

    public int getDefesaBase() {
        return casa.getDefesaBase();
    }

    public int getAlcance() {
        return casa.getAlcance();
    }

    public double getModificadorDefensivo() {
        return casa.getModificadorDefensivo();
    }

    public double getModificadorOfensivo() {
        return casa.getModificadorOfensivo();
    }

    // Setters para atributos que mudam
    public void setVidaAtual(int vidaAtual) {
        this.vidaAtual = vidaAtual;
        if (this.vidaAtual <= 0) {
            this.vidaAtual = 0;
            this.vivo = false;
        }
    }

    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    // Métodos de utilidade
    public void receberDano(int dano) {
        if (vivo) {
            setVidaAtual(vidaAtual - dano);
        }
    }

    public void curar(int cura) {
        if (vivo) {
            int novaVida = vidaAtual + cura;
            if (novaVida > getVidaMaxima()) {
                novaVida = getVidaMaxima();
            }
            setVidaAtual(novaVida);
        }
    }

    public boolean estaNoAlcance(Personagem alvo) {
        if (!alvo.isVivo()) return false;

        int distanciaLinha = Math.abs(this.linha - alvo.getLinha());
        int distanciaColuna = Math.abs(this.coluna - alvo.getColuna());
        int distancia = Math.max(distanciaLinha, distanciaColuna);

        return distancia <= getAlcance();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Vida: %d/%d - Pos: (%d,%d) - %s",
                nome, casa.getNome(), vidaAtual, getVidaMaxima(), linha, coluna,
                vivo ? "VIVO" : "MORTO");
    }
}









    */

}
