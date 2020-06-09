import static java.lang.Math.*;
public class TRF {




        public static int bitReverse(int n, int bits) {
            int reversa = n;
            int contador = bits - 1;

            n >>= 1;
            while (n > 0) {
                reversa = (reversa << 1) | (n & 1);
                contador--;
                n >>= 1;
            }

            return ((reversa << contador) & ((1 << bits) - 1));
        }

        static void trapida(Complejo[] buffer) {

            int bits = (int) (log(buffer.length) / log(2));
            for (int j = 1; j < buffer.length / 2; j++) {

                int cambioPos = bitReverse(j, bits);
                Complejo temp = buffer[j];
                buffer[j] = buffer[cambioPos];
                buffer[cambioPos] = temp;
            }

            for (int N = 2; N <= buffer.length; N <<= 1) {
                for (int i = 0; i < buffer.length; i += N) {
                    for (int k = 0; k < N / 2; k++) {

                        int evenIndex = i + k;
                        int oddIndex = i + k + (N / 2);
                        Complejo even = buffer[evenIndex];
                        Complejo odd = buffer[oddIndex];

                        double term = (-2 * PI * k) / (double) N;
                        Complejo exp = (new Complejo(cos(term), sin(term)).mult(odd));

                        buffer[evenIndex] = even.suma(exp);
                        buffer[oddIndex] = even.sub(exp);
                    }
                }
            }


//        public static void main(String[] args) {
//            //aqui se ingresan los valores a calcular
//            double[] input = {1.0, 2.0, -1.0, -1.0};
//
//            Complejo[] cinput = new Complejo[input.length];
//            for (int i = 0; i < input.length; i++)
//                cinput[i] = new Complejo(input[i], 0.0);
//
//            trapida(cinput);
//
//            System.out.println("Resultado:");
//            for (Complejo c : cinput) {
//                System.out.println(c);
//            }
        //}
    }

    static class Complejo {
        public final double re;
        public final double img;

        public Complejo() {
            this(0, 0);
        }

        public Complejo(double r, double i) {
            re = r;
            img = i;
        }

        public Complejo suma(Complejo b) {
            return new Complejo(this.re + b.re, this.img + b.img);
        }

        public Complejo sub(Complejo b) {
            return new Complejo(this.re - b.re, this.img - b.img);
        }

        public Complejo mult(Complejo b) {
            return new Complejo(this.re * b.re - this.img * b.img,
                    this.re * b.img + this.img * b.re);
        }

        @Override
        public String toString() {
            return String.format("(%f,%f)", re, img);
        }
    }
}
