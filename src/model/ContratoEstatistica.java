package model;

public class ContratoEstatistica {
    private double mediaValorMensal;

    public ContratoEstatistica(double mediaValorMensal) {
        this.mediaValorMensal = mediaValorMensal;
    }

    public double getMediaValorMensal() {
        return mediaValorMensal;
    }

    @Override
    public String toString() {
        return "Média do Valor Mensal dos Contratos: R$ " + mediaValorMensal;
    }
}
