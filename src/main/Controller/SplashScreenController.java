package main.Controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.Main;

import java.io.IOException;
import java.util.concurrent.*;

public class SplashScreenController {
    @FXML
    private ImageView logoIV;
    @FXML
    private Label loadingLbl;
    @FXML
    private ProgressBar loadingPB;
    @FXML
    private AnchorPane rootAnchorPane;

    private final double loadingTime = 5000;
    private boolean exit;
    private ExecutorService executorService;
    private Image logo1;
    private Image logo2;

    public void initialize(){
        logo1 = new Image(SplashScreenController.class.getResourceAsStream("/main/View/logo.png"));
        logo2 = new Image(SplashScreenController.class.getResourceAsStream("/main/View/logo2.png"));
        exit = false;


        executorService = Executors.newCachedThreadPool();

        Thread imageThread = new Thread(() -> {
            int imgNum = 0;
            while (!exit){
                int finalImgNum = imgNum;
                Platform.runLater(() -> {
                    if (finalImgNum == 0){
                        logoIV.setImage(logo1);
                    } else {
                        logoIV.setImage(logo2);
                    }
                });
                imgNum = 1 - imgNum;
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread labelThread = new Thread(() -> {
            while (!exit){
                Platform.runLater(() -> {
                    if (loadingLbl.getText().length() > "Loading . .".length()){
                        loadingLbl.setText("Loading");
                    } else {
                        loadingLbl.setText(loadingLbl.getText() + " .");
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), rootAnchorPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> {
            handleExit();
            showMainStage();
        });

        Thread progressBarThread = new Thread(() -> {
            while (!exit){
                Platform.runLater(() -> {
                    loadingPB.setProgress(loadingPB.getProgress() + 17/loadingTime);

                });
                if (loadingPB.getProgress() >= 1){
                    fadeTransition.play();
                }
                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.submit(imageThread);
        executorService.submit(labelThread);
        executorService.submit(progressBarThread);
    }

    public void showMainStage(){
        Parent root = null;
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(Main.class.getResource("View/simulationScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Boids");
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
//        stage.setFullScreen(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.getIcons().add(logo1);

        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        stage.show();
    }

    public void handleExit(){
        exit = true;
        ((Stage) rootAnchorPane.getScene().getWindow()).close();
        executorService.shutdown();
    }
}
