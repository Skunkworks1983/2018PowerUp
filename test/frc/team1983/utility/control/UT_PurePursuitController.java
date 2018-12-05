package frc.team1983.utility.control;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UT_PurePursuitController extends Application
{
    @Override
    public void start(Stage stage)
    {
        Pane canvas = new Pane();
        Scene scene = new Scene(canvas, 1080, 540, Color.BLACK);

        scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ESCAPE)
                stage.close();
        });

        stage.setScene(scene);
        stage.show();
    }
}