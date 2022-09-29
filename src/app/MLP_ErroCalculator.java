package app;

public class MLP_ErroCalculator {

    public double somaErroAmostraAprox(double[] y, double[] out){
        double soma=0;
        for (int i = 0; i < y.length; i++) {
            soma+=Math.abs(y[i]-out[i]);
        }
        return soma;
    }

    public double somaErroAmostraClass(double[] y, double[] out){
        double soma=0;
        double value = 0;
        for (int i = 0; i < y.length; i++) {
            value = Math.round(out[i]);
            soma+=Math.abs(y[i]-value);
        }
        return soma;
    }


}
