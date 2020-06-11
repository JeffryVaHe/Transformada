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
        Rn.setSelected(true);
        Rn.setText("Serie de Fourier");
        //RadioButton Rs = new RadioButton();
        //Rs.setToggleGroup(grupo1);
        //Rs.setText("Serie senos");
        //RadioButton Rc = new RadioButton();
        //Rc.setToggleGroup(grupo1);
        //Rc.setText("Serie Cosenos");
        //--
        TextField IngIndice=new TextField();
        IngIndice.setPromptText("N indices");
        IngIndice.setPrefWidth(60);

        TextField InForm=new TextField();
        InForm.setPromptText("Formula");
        InForm.setPrefWidth(200);

        Button Cambiar =new Button("Seno/Coseno");
        Cambiar.setDisable(true);


        TextField InPar1=new TextField();
        InPar1.setPromptText("Limite a");
        InPar1.setPrefWidth(75);


        TextField InPar2=new TextField();
        InPar2.setPromptText("Limite b");
        InPar2.setPrefWidth(75);


        Label res= new Label("Resultado");
        Label Indice = new Label("Indices ");
        Button calcular=new Button("Calcular");


        //--
        LayVert.getChildren().add(LayHor);
        LayHor.getChildren().addAll(Rn);
        LayVert.getChildren().add(Ingresar);
        Ingresar.getChildren().addAll(InForm,InPar1,InPar2, IngIndice);
        LayVert.getChildren().addAll(res,Indice);
        LayVert.getChildren().add(calcular);
        LayHor.getChildren().add(Cambiar);
        //--
        LayVert.setPadding(new Insets(18,18,18,18));
        LayVert.setSpacing(20);
        LayHor.setSpacing(20);
        Ingresar.setSpacing(15);
        //--

        InPar1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(InPar1.getText().equals("0")){
                    Cambiar.setDisable(false);

                }
            }
        });
        String vector=InForm.getText();
        String [] arregloC= vector.split(",");
        Double[] arregloI= new  Double[arregloC.length];
        for (int i = 0; i < arregloC.length; i++) {
            arregloI[i] = Double.parseDouble(arregloC[i]);
        }
        Double[] Matriz = TDF.CalcSerie1(arregloI);

        Cambiar.setOnAction(event ->  InPar1.setText("-"+InPar2.getText()));

        calcular.setOnAction((event -> {

            res.setText(Arrays.toString(Matriz));
            final ProgressIndicator progreso = new ProgressIndicator();
            LayVert.getChildren().add(progreso);




//            ArrayList<double[]> Ind= Fourier.Indices(Double.parseDouble(InPar1.getText()), Double.parseDouble(InPar2.getText()),Integer.parseInt(IngIndice.getText()), InForm.getText());

//            if((Ind.get(0)[1]==0) && (Ind.get(1)[1]==0) ){
//                res.setText("Serie Senos // Funcion impar " );
//            }else if((Ind.get(0)[2]==0) && (Ind.get(1)[2]==0)){
//                res.setText("Serie Cosenos // Funcion par ");
//            }else{
//                res.setText(" Funcion no par no impar ");
//            }
//            String NuevoIndice = "";
//            for(int i=0;i<Integer.parseInt(IngIndice.getText());i++) {
//                NuevoIndice += Ind.get(i)[0] + " , " + Ind.get(i)[1] + " , " + Ind.get(i)[2] + "\n";
//            }
//            Indice.setText(NuevoIndice);
            new Thread(() -> {
                if(grupo1.getSelectedToggle() == Rn){

//                    double resultado = Fourier.integrar(
//                            Double.parseDouble(InPar1.getText()),
//                            Double.parseDouble(InPar2.getText()),
//                            x -> Fourier.CalcSerie(Double.parseDouble(InPar1.getText()), Double.parseDouble(InPar2.getText()),x,5, InForm.getText())
//                    );

                    Platform.runLater(() -> {
                        // res.setText(String.valueOf(resultado));
                        new CartesianPlot(InForm.getText());
                        LayVert.getChildren().remove(progreso);
                    });
                }//else if(grupo1.getSelectedToggle() == Rc){
                //}else if(grupo1.getSelectedToggle() == Rs){ }
            }).start();
        }));
        //--
        Scene escenapoderosamentemamalona = new Scene(LayVert,500,260);
        primaryStage.setScene(escenapoderosamentemamalona);
        primaryStage.show();

    }
}