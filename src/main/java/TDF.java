//import org.apache.commons.math3.FunctionEvaluationException;
//import org.apache.commons.math3.analysis.UnivariateRealFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import java.math.*;

import java.util.Arrays;

public class TDF {
//    public static double integrar(double a, double b, String formula) {
//        final Function f1 =new Function("f(x)="+formula);
//
//        TrapezoidIntegrator integrador = new TrapezoidIntegrator();
//        integrador.setAbsoluteAccuracy(0.1);
//        integrador.setRelativeAccuracy(0.1);
//        try{
//            return integrador.integrate(x -> {
//                Argument ax= new Argument("x="+x);
//                Expression e1 =new Expression("f(x)",f1,ax);
//                return e1.calculate();
//            }, a, b);
//        }catch (Exception e){
//            System.out.println(e);
//            return 0;
//        }
//    }

//    public static double integrar(double a, double b, UnivariateRealFunction f) {
//        TrapezoidIntegrator integrador = new TrapezoidIntegrator();
//        integrador.setAbsoluteAccuracy(0.1);
//        integrador.setRelativeAccuracy(0.1);
//        try{
//            return integrador.integrate(f, a, b);
//        }catch (Exception e){
//            System.out.println(e);
//            return 0;
//        }
//    }
    public static Double[] CalcSerie1 ( Double[] arreglo){

        int n= arreglo.length;
        Double[] k = new Double[n-1];
        for(int i=0;i<n;i++)
        if(arreglo[i]<0 || arreglo[i]>=(n-1))
            arreglo[i]=0.0;
        for(int i=0;i<=n-1;i++){
            Double res=0.0;
        for(int j=0;j<=n;j++){
            res+=arreglo[j]*Math.pow((2.71828),(-2*3.141519*i*j)/n);



        }
        k[i]=res;


        }




        return k;
    }
    public static Complex[] CalcSerie(double a, double b, double x, int nMaximo, String formula){

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        final Function f1 =new Function("f(x)="+formula);


        try{
            Complex[] complejo= transformer.transform(t -> {
                Argument ax= new Argument("x="+t);
                Expression e1 =new Expression("f(x)",f1,ax);
                return e1.calculate();
            }, a, b,128, TransformType.FORWARD);
            System.out.println(Arrays.toString(complejo));
            return complejo;
        }catch (Exception e){
            System.out.println(e);
            return new Complex[]{};
        }
    }
    public static double sumatoria(int a, int b, Funcion funcion){
        double res = 0;
        for(int i = a; i <= b ; i++){
            res += funcion.f(i);
        }

        return res;
    }

}
