
import javafx.beans.binding.Bindings;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import org.apache.commons.math3.complex.Complex;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.function.Function;

// Java 8 code
public class CartesianPlot extends Stage {


    public CartesianPlot(String InPar1, String InPar2, String InForm) {
        char [] arregloC= InForm.toCharArray();
        Double[] arregloI= new  Double[InForm.length()/2];
        for(int i=0;i<InForm.length();i++){
            if(arregloC[i]!=',')
            arregloI[i]=(double)arregloC[i];
        }
        double a = 0;
        double b = Double.parseDouble(InPar2);
        double paso = (b)/128;
        double min=Double.MAX_VALUE, max=Double.MIN_VALUE;
        Double[] arreglo = TDF.CalcSerie1(arregloI);
        for(int i=0;i<arreglo.length;i++){


            if (arreglo[i] < min) {
                min= arreglo[i];
            }
            if(arreglo[i]>max){
                max=arreglo[i];
            }
        }

        double yBounds = 10.0;//Math.max(Math.abs(min), Math.abs(max));
        double xBounds = Math.max(Math.abs(a), Math.abs(b));

        indice = 0;

        Axes axes = new Axes(
                600, 600,
                -xBounds-0.5, xBounds+0.5, xBounds/8,
                -yBounds-0.5, yBounds+0.5, yBounds/8
        );

        Plot plot = new Plot(
                new Function<Double, Double>() {
                    int aux=0;
                    @Override
                    public Double apply(Double aDouble) {
                        return arreglo[aux++];
                    }
                },
                a, b, paso*2,
                axes
        );

        final org.mariuszgromada.math.mxparser.Function f1 =new org.mariuszgromada.math.mxparser.Function("f(x)="+InForm);


        Plot plot2= new Plot(
                aDouble -> {

                    double res;
                    Argument ax = new Argument("x=" + aDouble);
                    Expression e1 = new Expression("f(x)", f1, ax);
                    res = e1.calculate();
                    return res;
                },
                a, b+1,paso/4,
                axes
        );

        Plot plotlimitea= new Plot(
                new Function<Double, Double>() {
                    boolean limitea = false;

                    @Override
                    public Double apply(Double aDouble) {
                        if (limitea) {
                            return yBounds+0.5;
                        } else {
                            limitea = true;
                            return -yBounds-0.5;
                        }
                    }
                },
                a - 0.000001, a + 0.000001, 0.000001,
                axes
        );

        Plot plotlimiteb= new Plot(
                new Function<Double, Double>() {
                    boolean limiteb = false;

                    @Override
                    public Double apply(Double aDouble) {
                        if (limiteb) {
                            return yBounds+0.5;
                        } else {
                            limiteb = true;
                            return -yBounds-0.5;
                        }
                    }
                },
                b - 0.000001, b+ 0.000001, 0.000001,
                axes
        );

        StackPane layout = new StackPane(
                plot,plot2, plotlimitea, plotlimiteb
        );
        layout.setPadding(new Insets(20));

        this.setTitle("f(x)="+InForm);
        this.setScene(new Scene(layout, Color.rgb(35, 39, 50)));
        this.show();
    }

    class Axes extends Pane {
        private NumberAxis xAxis;
        private NumberAxis yAxis;

        public Axes(
                int width, int height,
                double xLow, double xHi, double xTickUnit,
                double yLow, double yHi, double yTickUnit
        ) {
            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(width, height);
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            xAxis = new NumberAxis(xLow, xHi, xTickUnit);
            xAxis.setSide(Side.BOTTOM);
            xAxis.setMinorTickVisible(false);
            xAxis.setPrefWidth(width);
            xAxis.setLayoutY(height / 2);

            yAxis = new NumberAxis(yLow, yHi, yTickUnit);
            yAxis.setSide(Side.LEFT);
            yAxis.setMinorTickVisible(false);
            yAxis.setPrefHeight(height);
            yAxis.layoutXProperty().bind(
                    Bindings.subtract(
                            (width / 2) + 1,
                            yAxis.widthProperty()
                    )
            );

            getChildren().setAll(xAxis, yAxis);
        }

        public NumberAxis getXAxis() {
            return xAxis;
        }

        public NumberAxis getYAxis() {
            return yAxis;
        }
    }

    private static int indice = 0;
    private static Color[] colores = {
            Color.ORANGE,
            Color.RED,
            Color.GREY.brighter(),
            Color.GREY.brighter()
    };

    class Plot extends Pane {


        public Plot(
                Function<Double, Double> f,
                double xMin, double xMax, double xInc,
                Axes axes
        ) {
            Path path = new Path();
            path.setStroke(colores[indice++]);
            path.setStrokeWidth(2);

            path.setClip(
                    new Rectangle(
                            0, 0,
                            axes.getPrefWidth(),
                            axes.getPrefHeight()
                    )
            );

            double x = xMin;
            double y = f.apply(x);

            path.getElements().add(
                    new MoveTo(
                            mapX(x, axes), mapY(y, axes)
                    )
            );

            x += xInc;
            while (x < xMax) {
                y = f.apply(x);

                path.getElements().add(
                        new LineTo(
                                mapX(x, axes), mapY(y, axes)
                        )
                );

                x += xInc;
            }

            setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
            setPrefSize(axes.getPrefWidth(), axes.getPrefHeight());
            setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);

            getChildren().setAll(axes, path);
        }

        private double mapX(double x, Axes axes) {
            double tx = axes.getPrefWidth() / 2;
            double sx = axes.getPrefWidth() /
                    (axes.getXAxis().getUpperBound() -
                            axes.getXAxis().getLowerBound());

            return x * sx + tx;
        }

        private double mapY(double y, Axes axes) {
            double ty = axes.getPrefHeight() / 2;
            double sy = axes.getPrefHeight() /
                    (axes.getYAxis().getUpperBound() -
                            axes.getYAxis().getLowerBound());
            return -y * sy + ty;
        }
    }
}