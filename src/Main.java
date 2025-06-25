import Classes.Musica.*;
import Classes.Interface.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private Midi midi;
    private TextoMusicalParser parser;
    private Musica musica;


    //Isso aqui é a interface
    @Override
    public void start(Stage primaryStage) throws Exception {
        midi = new Midi();
        parser = new TextoMusicalParser();

        UIBuilder uiBuilder = new UIBuilder(parser, musica);
        Scene scene = uiBuilder.buildUI();

        //Pega key pressionada
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Interface Musical Modular");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //isso aqui ta bugado n sei como arrumar e to cansado
    private void handleKeyPress(KeyEvent event) {
        //midiController.handleKeyPress(event);
    }
    //inicializa a interface
    public static void main(String[] args) {
        launch(args);
    }
}
