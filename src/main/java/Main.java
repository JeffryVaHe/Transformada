import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        VBox LayVert=new VBox();
        HBox LayHor=new HBox();
        HBox Ingresar=new HBox();

        //--
        ToggleGroup grupo1= new ToggleGroup();
        RadioButton Rn = new RadioButton();
        Rn.setToggleGroup(grupo1);
        Rn.setSelected(false);
        Rn.setText("Transformada Discreta de Fourier");


        RadioButton Rr = new RadioButton();
        Rr.setToggleGroup(grupo1);
        Rr.setSelected(false);
        Rr.setText("Transformada Rapida(fiuuum) de Fourier");

        TextField InForm=new TextField();
        InForm.setPromptText("Formula");
        InForm.setPrefWidth(200);



        Label res= new Label("Resultado");
       // Label Indice = new Label("Indices ");
        Button calcular=new Button("Calcular");


        //--
        LayVert.getChildren().add(LayHor);
        LayHor.getChildren().addAll(Rn, Rr);
        LayVert.getChildren().add(Ingresar);
        Ingresar.getChildren().addAll(InForm);
        LayVert.getChildren().addAll(res);
        LayVert.getChildren().add(calcular);

        //--
        LayVert.setPadding(new Insets(18,18,18,18));
        LayVert.setSpacing(20);
        LayHor.setSpacing(20);
        Ingresar.setSpacing(15);





        calcular.setOnAction((event -> {
            if(Rn.isSelected()){
                String valor="";
                String vector=InForm.getText();
                String [] arregloC= vector.split(",");
                Double[] arregloI= new  Double[arregloC.length];
                for (int i = 0; i < arregloC.length; i++) {
                    arregloI[i] = Double.parseDouble(arregloC[i]);
                }
                Double[] Matriz = TDF.CalcSerie1(arregloI);
                for(int j=0;j<arregloC.length;j++) {
                    valor+="K["+j+"]= "+Matriz[j]+"\n";
                    res.setText(valor);
                }
                final ProgressIndicator progreso = new ProgressIndicator();
                LayVert.getChildren().add(progreso);

                new Thread(() -> {
                    if(grupo1.getSelectedToggle() == Rn){



                        Platform.runLater(() -> {

                            new CartesianPlot(InForm.getText());
                            LayVert.getChildren().remove(progreso);
                        });
                    }
                }).start();

            }
            if(Rr.isSelected()){
                String valor="";
                String vector=InForm.getText();
                String [] arregloC= vector.split(",");
                Double[] arregloI= new  Double[arregloC.length];
            TRF.Complejo[] cinput = new TRF.Complejo[arregloC.length];
            for(int i=0; i<arregloC.length;i++){
                arregloI[i] = Double.parseDouble(arregloC[i]);
                cinput[i]= new TRF.Complejo(arregloI[i],0.0);
            }
            TRF.trapida(cinput);
                int i=0;
                for(TRF.Complejo c : cinput){
                    i++;
                    valor+="K["+i+"]= "+c+"i\n";
                    res.setText(valor);
                }
            }


        }));
        //--
        Scene escenapoderosamentemamalona = new Scene(LayVert,500,260);
        primaryStage.setScene(escenapoderosamentemamalona);
        primaryStage.show();


    }
}