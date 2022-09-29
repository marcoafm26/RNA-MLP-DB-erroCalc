    package domain;

    import java.util.Random;

    public class MLP {
        private double u;
        private int qtdIn;
        private int qtdOut;
        private int qtdH;
        private double[][] wh;
        private double[][] wo;
        private double interA=-0.3;
        private double interB= 0.3;




        public MLP(int qtdIn, int qtdOut,int qtdH, double u){
            this.u=u;
            this.qtdIn =qtdIn;
            this.qtdOut=qtdOut;
            this.qtdH = qtdH;
            wh = new double[qtdIn+1][qtdH];
            wo = new double[qtdH+1][qtdOut];
            wh = gerarRandomW(wh);
            wo = gerarRandomW(wo);
        }

        public double[] learn(double[] xIn,double[] y){

            // Copia o vetor x e acrescenta o bias
            double[] x = fill(xIn);
            x[x.length-1] = 1;

            // inicializa o vetor h
            double[] h = new double[qtdH+1];

            // 1º camada
            for (int j = 0; j < qtdH; j++) {
                for (int i = 0; i < x.length; i++) {
                    h[j] += x[i] * wh[i][j];
                }
                h[j] = sigmoid(h[j]);
            }
            h[qtdH]= 1;

            // inicializa o vetor out
            double[] out = new double[qtdOut];

            // 2º camada
            for (int j = 0; j < qtdOut; j++) {
                for (int i = 0; i < h.length; i++) {
                    out[j] += h[i] * wo[i][j];
                }
                out[j] = sigmoid(out[j]);
            }

            // variacao de pesos do out
            double[] delta_out = new double[qtdOut];
            for (int j = 0; j < qtdOut; j++) {
                delta_out[j] = out[j]*(1-out[j])*(y[j]-out[j]);
            }

            // variacao de pesos do h
            double[] delta_h = new double[qtdH];
            for (int h_pos = 0; h_pos<qtdH ; h_pos++) {
                double soma=0;
                for (int j = 0; j < qtdOut; j++) {
                    soma += delta_out[j]*wo[h_pos][j];
                }
                delta_h[h_pos] = h[h_pos]*(1-h[h_pos])*soma;
            }

            //recalculando pesos wh
            wh=recalcular(wh,delta_h,x,x.length,qtdH);

            //recalculando pesos wo
            wo=recalcular(wo,delta_out,h,h.length,qtdOut);



            return out;
        }
        public double[] test(double[]xIn){
            // Copia o vetor x e acrescenta o bias
            double[] x = fill(xIn);
            x[x.length-1] = 1;

            // inicializa o vetor h
            double[] h = new double[qtdH+1];

            // 1º camada
            for (int j = 0; j < qtdH; j++) {
                for (int i = 0; i < x.length; i++) {
                    h[j] += x[i] * wh[i][j];
                }
                h[j] = sigmoid(h[j]);
            }
            h[qtdH]= 1;

            // inicializa o vetor out
            double[] out = new double[qtdOut];

            // 2º camada
            for (int j = 0; j < qtdOut; j++) {
                for (int i = 0; i < h.length; i++) {
                    out[j] += h[i] * wo[i][j];
                }
                out[j] = sigmoid(out[j]);
            }
            return out;
        }
        
        private double[] fill(double[] x){
            double [] x_new = new double[x.length+1];
            for (int i = 0; i < x.length; i++) {
                x_new[i] = x[i];
            }
            return x_new;
        }
        private double[][] gerarRandomW(double[][] w) {
            Random random = new Random();
            for (int i = 0; i < w.length; i++) {
                for (int j = 0; j < w[0].length ; j++) {
                    w[i][j]= random.nextDouble() * interB * 2 + interA;
                }
            }
            return w;
        }

        private double[][] recalcular(double[][] w,double[] delta_w,double[] x,int i, int j){
            for (int k = 0; k < j; k++) {
                for (int l = 0; l < i; l++) {
                  w[l][k] += u * delta_w[k] * x[l];
                }
            }
            return w;
        }

        private double sigmoid(double u){
            double value = 1/(1+Math.exp(-u));
            return value;
        }
    }

