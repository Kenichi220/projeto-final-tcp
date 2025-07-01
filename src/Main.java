import Classes.Musica.*;
import Classes.Interface.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private TextoMusicalParser parser;
    private Musica musica;

    //Iniciando a interface
    @Override
    public void start(Stage primaryStage) throws Exception {
        parser = new TextoMusicalParser();
        musica = new Musica();

        UIBuilder uiBuilder = new UIBuilder(parser, musica);
        Scene scene = uiBuilder.buildUI();

        //Titulo da Interface
        primaryStage.setTitle("Sintetizador de Musica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Inicializa a interface
    public static void main(String[] args) {
        launch(args);
    }
}
