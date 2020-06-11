//import org.apache.commons.math3.FunctionEvaluationException;
//import org.apache.commons.math3.analysis.UnivariateRealFunction;


import java.util.ArrayList;
import org.apache.commons.math3.complex.*;
public class TDF {

    public static Double[] CalcSerie1 (Double[] arreglo){
        double aux=0, aux2=0;

        int n = arreglo.length;

        Complex[][] w = new Complex[n][n];
        Double[] k = new Double[n];
//        for (int i = 0; i < n; i++)
//            if (arreglo[i] < 0 || arreglo[i] > (n-1))
//                arreglo[i] = 0.0;





        for(int i=0;i<arreglo.length;i++){
            for(int j=0;j<arreglo.length;j++){
                aux=Math.cos((-2 * Math.PI)/n);
                aux2=Math.sin((-2 * Math.PI)/n);
                Complex complejo =new Complex(aux,aux2);

                complejo=complejo.pow(i*j);

//               complejo= complejo.sqrt();
                w[i][j]=complejo;


            }

        }

        double sumador = 0.0;
        double aux1=0.0;
        for(int i=0;i<arreglo.length;i++){
            for(int j=0;j<arreglo.length;j++) {

                aux1+=arreglo[j]*w[i][j].getReal();
                sumador+=arreglo[j]*w[i][j].getImaginary();


            }

            k[i]=Math.sqrt(Math.pow(aux1,2)+Math.pow(sumador,2));
            aux1=0;
            sumador=0;


        }

        return k;
    }



//    public double theta(){
//
//
//        if (getParteReal() == 0 && getParteImaginaria() == 0) {
//            this.theta = 0;
//        } else if (getParteReal() == 0 && getParteImaginaria() != 0) {
//            if (getParteImaginaria() > 0) {
//                this.theta = 90;
//            } else {
//                this.theta = 270;
//            }
//        } else if (getParteReal() != 0 && getParteImaginaria() == 0) {
//            if (getParteReal() > 0) {
//                this.theta = 0;
//            } else {
//                this.theta = 180;
//            }
//        } else {
//            this.theta = Math.atan(( getParteImaginaria()/getParteReal() ));}
//
//
//        return this.theta ;
//
//
//    }

    //this.r = (Math.sqrt(Math.pow(getParteReal(), 2) + Math.pow(getParteImaginaria(), 2)));
    //        theta();
    //        return this.r+"(Cos"+this.theta+")+ iSen("+this.theta+")";
}
