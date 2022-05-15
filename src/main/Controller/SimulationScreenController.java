package main.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Model.FileIO;
import main.Model.Stats.BirdStats;
import main.Model.Boids;
import main.Model.MapGrid;
import main.Model.Organisms.Bird;
import main.Model.Stats.MapStats;
import main.Model.Vector;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationScreenController {

    // FXML variables
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private Canvas mapAreaCanvas;
    @FXML
    private Button playBtn;
    @FXML
    private TextField numBirdsTF;
    @FXML
    private Slider numBirdsSlider;
    @FXML
    private TextField alignmentTF;
    @FXML
    private Slider alignmentSlider;
    @FXML
    private TextField coherenceTF;
    @FXML
    private Slider coherenceSlider;
    @FXML
    private TextField separationTF;
    @FXML
    private Slider separationSlider;
    @FXML
    private TextField visionRadiusTF;
    @FXML
    private Slider visionRadiusSlider;
    @FXML
    private TextField visionAngleTF;
    @FXML
    private Slider visionAngleSlider;
    @FXML
    private TextField maxSpeedTF;
    @FXML
    private Slider maxSpeedSlider;
    @FXML
    private TextField maxAccelerationTF;
    @FXML
    private Slider maxAccelerationSlider;
    @FXML
    private TextField boundaryMarginTF;
    @FXML
    private Slider boundaryMarginSlider;
    @FXML
    private TextField gridSquareSizeTF;
    @FXML
    private Slider gridSquareSizeSlider;
    @FXML
    private TextField spawnChanceTF;
    @FXML
    private Slider spawnChanceSlider;
    @FXML
    private TextField thresholdTF;
    @FXML
    private Slider thresholdSlider;
    @FXML
    private TextField seedTF;
    @FXML
    private Tooltip alignmentTT;
    @FXML
    private Tooltip coherenceTT;
    @FXML
    private Tooltip separationTT;
    @FXML
    private Tooltip visionRadiusTT;
    @FXML
    private Tooltip visionAngleTT;
    @FXML
    private Tooltip maxSpeedTT;
    @FXML
    private Tooltip maxAccelerationTT;
    @FXML
    private Tooltip boundaryMarginTT;
    @FXML
    private Tooltip gridSquareSizeTT;
    @FXML
    private Tooltip spawnChanceTT;
    @FXML
    private Tooltip thresholdTT;
    @FXML
    private Tooltip seedTT;
    @FXML
    private Tooltip iterationsTT;
    @FXML
    private Tooltip wallColorTT;
    @FXML
    private Tooltip backgroundColorTT;
    @FXML
    private Tooltip birdColorTT;
    @FXML
    private Tooltip birdSizeTT;
    @FXML
    private Tooltip birdAngleTT;
    @FXML
    private Tooltip themeTT;
    @FXML
    private Label iterationsPassedLbl;
    @FXML
    private TextField iterationsTF;
    @FXML
    private ColorPicker wallColorCP;
    @FXML
    private ColorPicker backgroundColorCP;
    @FXML
    private ColorPicker birdColorCP;
    @FXML
    private Slider birdSizeSlider;
    @FXML
    private Slider birdAngleSlider;
    @FXML
    private TextField birdSizeTF;
    @FXML
    private TextField birdAngleTF;
    @FXML
    private ChoiceBox<String> themeCB;
    @FXML
    private CheckBox saveBirdSettingsCB;
    @FXML
    private CheckBox saveMapSettingsCB;
    @FXML
    private CheckBox saveAppearanceCB;
    @FXML
    private RadioButton drawWallsRB;
    @FXML
    private RadioButton eraseWallsRB;
    @FXML
    private CheckBox floodFillCB;

    // Controls Arrays
    private Tooltip[] tooltips;
    private TextField[] birdTextFields;
    private Slider[] birdSliders;
    private TextField[] mapTextFields;
    private Slider[] mapSliders;
    private ToggleGroup tg;

    // Controller variables
    private GraphicsContext graphicsContext;
    private MapGrid mapGrid;
    private Boids boids;
    private BirdStats birdStats;
    private MapStats mapStats;
    private boolean nextStep;
    private boolean play;


    // EDITABLE VARIABLES
    private double birdSize;
    private double birdAngle;
    private Paint wallColor;
    private Paint backgroundColor;
    private Paint birdColor;

    public void initialize(){

        tooltips = new Tooltip[]{
                alignmentTT, coherenceTT, separationTT,
                visionRadiusTT, visionAngleTT,
                maxSpeedTT, maxAccelerationTT,
                boundaryMarginTT,
                gridSquareSizeTT,
                spawnChanceTT,
                thresholdTT,
                seedTT,
                iterationsTT,
                wallColorTT,
                backgroundColorTT,
                birdColorTT,
                birdSizeTT,
                birdAngleTT,
                themeTT
        };

        birdTextFields = new TextField[]{
                alignmentTF, coherenceTF, separationTF,
                visionRadiusTF, visionAngleTF,
                maxSpeedTF, maxAccelerationTF,
                boundaryMarginTF,
                birdSizeTF, birdAngleTF
        };

        birdSliders = new Slider[]{
                alignmentSlider, coherenceSlider, separationSlider,
                visionRadiusSlider, visionAngleSlider,
                maxSpeedSlider, maxAccelerationSlider,
                boundaryMarginSlider,
                birdSizeSlider, birdAngleSlider
        };

        mapTextFields = new TextField[]{
                gridSquareSizeTF,
                spawnChanceTF,
                thresholdTF,
        };

        mapSliders = new Slider[]{
                gridSquareSizeSlider,
                spawnChanceSlider,
                thresholdSlider,
        };


        Random random = new Random();
        birdStats = new BirdStats(maxSpeedSlider.getValue(),
                maxAccelerationSlider.getValue() / 100.0 * maxSpeedSlider.getValue(),
                (int) visionRadiusSlider.getValue(),
                (int) visionAngleSlider.getValue(),
                alignmentSlider.getValue() / 100,
                coherenceSlider.getValue() / 100,
                separationSlider.getValue() / 100,
                boundaryMarginSlider.getValue());
        mapStats = new MapStats(gridSquareSizeSlider.getValue(),
                spawnChanceSlider.getValue() / 100, (int) thresholdSlider.getValue(), -1);
        birdSize = (int) birdSizeSlider.getValue();
        birdAngle = (int) birdAngleSlider.getValue();

        playBtn.setText("▶");
        play = false;
        nextStep = false;
        mapGrid = new MapGrid(new Vector(mapAreaCanvas.getHeight(), mapAreaCanvas.getWidth()), mapStats);
        boids = new Boids((int) numBirdsSlider.getValue(), mapGrid, random, birdStats);
        handleSeed();

        graphicsContext = mapAreaCanvas.getGraphicsContext2D();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> Platform.runLater(() -> {
            if (play || nextStep){
                boids.nextFrame();
                nextStep = false;
            }
            numBirdsSlider.setValue(boids.getBirds().size());
            graphicsContext.setFill(backgroundColor);
            graphicsContext.fillRect(0, 0, mapAreaCanvas.getWidth(), mapAreaCanvas.getHeight());
            graphicsContext.setFill(wallColor);
            drawMapGrid();
            graphicsContext.setFill(birdColor);
            drawBirds(birdSize, birdAngle);
        }), 0, 16666, TimeUnit.MICROSECONDS);

        initializeControls();

        File defaultSettings = new File(System.getProperty("user.dir") + "/" + "default.csv");
        if (defaultSettings.exists()){
            processData(FileIO.readFile(defaultSettings));
        }
    }

    public void initializeControls(){

        for (Tooltip tt: tooltips){
            tt.setShowDelay(Duration.seconds(0));
            tt.setFont(Font.font(12));
        }

        numBirdsTF.setText("" + (int) numBirdsSlider.getValue());
        numBirdsSlider.valueProperty().addListener((observableValue, number, t1) -> {
            numBirdsTF.setText("" + t1.intValue());
            if (t1.intValue() - number.intValue() < 0){
                for (int i= t1.intValue(); i<number.intValue(); i++){
                    if (t1.intValue() != boids.getBirds().size()){
                        boids.removeBird();
                    }
                }
            } else{
                for (int i= number.intValue(); i<t1.intValue(); i++){
                    boids.addBird();
                }
            }
        });

        // BIRD STATS
        for (int node=0; node<birdTextFields.length; node++){

            birdTextFields[node].setText("" + (int) birdSliders[node].getValue());
            int finalNode = node;
            birdSliders[node].valueProperty().addListener((observableValue, number, t1) -> {
                int value = t1.intValue();
                birdTextFields[finalNode].setText("" + value);
                switch (finalNode) {
                    case 0 -> // alignment
                            birdStats.setAlignmentFactor(value / 100.0);
                    case 1 -> // coherence
                            birdStats.setCoherenceFactor(value / 100.0);
                    case 2 -> // separation
                            birdStats.setSeparationFactor(value / 100.0);
                    case 3 -> // vision radius
                            birdStats.setVisionRadius(value);
                    case 4 -> // vision angle
                            birdStats.setVisionAngle(value);
                    case 5 -> // max speed
                            birdStats.setMaxSpeed(value);
                    case 6 -> // max acceleration
                            birdStats.setMaxAcceleration(value / 100.0 * maxSpeedSlider.getValue());
                    case 7 -> // boundary margin
                            birdStats.setBoundaryMargin(value);
                    case 8 -> // bird sprite size
                            birdSize = value;
                    case 9 -> // bird sprite angle
                            birdAngle = value;
                }
            });
        }

        // MAP STATS
        for (int node=0; node< mapTextFields.length; node++){
            mapTextFields[node].setText("" + (int) mapSliders[node].getValue());
            int finalNode = node;
            mapSliders[node].valueProperty().addListener((observableValue, number, t1) -> {
                int value = t1.intValue();
                mapTextFields[finalNode].setText("" + value);
                switch (finalNode){
                    case 0 -> { // grid square size
                        if (value < 2){
                            gridSquareSizeSlider.setValue(2);
                        } else{
                            mapStats.setGridSquareSize(value);
                        }
                    }
                    case 1 -> // wall spawn chance
                            mapStats.setSpawnChance(value / 100.0);
                    case 2 -> // neighbor threshold
                            mapStats.setThreshold(value);
                }
                iterationsPassedLbl.setText("Smoothened: 0");
                mapGrid.update();
            });
        }

        seedTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*")) {
                seedTF.setText(oldValue);
            }
        });
        iterationsTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*") || (newValue.matches("\\d+") && Long.parseLong(newValue) > 500)) {
                iterationsTF.setText(oldValue);
            }
        });

        themeCB.getItems().add("Crimson-Red");
        themeCB.getItems().add("Night-Blue");
        themeCB.getItems().add("Nature-Green");
        themeCB.getItems().add("Yin-Yang");
        themeCB.valueProperty().addListener((observableValue, s, t1) -> {
            rootAnchorPane.getStylesheets().remove(1);
            rootAnchorPane.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/main/View/" + t1 + ".css")).toExternalForm());
            switch (t1) {
                case "Crimson-Red" -> {
                    wallColorCP.setValue(Color.rgb(20, 20, 30));
                    backgroundColorCP.setValue(Color.rgb(30, 30, 40));
                    birdColorCP.setValue(Color.rgb(255, 40, 80));
                }
                case "Night-Blue" -> {
                    wallColorCP.setValue(Color.rgb(25, 20, 20));
                    backgroundColorCP.setValue(Color.rgb(35, 30, 30));
                    birdColorCP.setValue(Color.rgb(0, 150, 255));
                }
                case "Nature-Green" -> {
                    wallColorCP.setValue(Color.rgb(175, 220, 220));
                    backgroundColorCP.setValue(Color.rgb(240, 250, 250));
                    birdColorCP.setValue(Color.rgb(0, 100, 100));
                }
                case "Yin-Yang" -> {
                    wallColorCP.setValue(Color.rgb(0, 0, 0));
                    backgroundColorCP.setValue(Color.rgb(50, 50, 50));
                    birdColorCP.setValue(Color.rgb(255, 255, 255));
                }
            }
        });
        themeCB.setValue("Crimson-Red");

        tg = new ToggleGroup();
        drawWallsRB.setToggleGroup(tg);
        eraseWallsRB.setToggleGroup(tg);

        wallColor = wallColorCP.getValue();
        wallColorCP.valueProperty().addListener((observableValue, color, t1) -> wallColor = t1);
        backgroundColor = backgroundColorCP.getValue();
        backgroundColorCP.valueProperty().addListener((observableValue, color, t1) -> backgroundColor = t1);
        birdColor = birdColorCP.getValue();
        birdColorCP.valueProperty().addListener((observableValue, color, t1) -> birdColor = t1);

    }

    public void handleMouseDrag(MouseEvent mouse){
        try{
            if (floodFillCB.isSelected()){
                mapGrid.floodFillInit(mouse.getY(), mouse.getX(), drawWallsRB.isSelected());
            } else {
                mapGrid.setBool(new Vector(mouse.getY(), mouse.getX()), drawWallsRB.isSelected());
            }
        } catch (ArrayIndexOutOfBoundsException ignored){
        }
    }

    public void handleMinimize(){
        ((Stage) rootAnchorPane.getScene().getWindow()).setIconified(true);
    }

    public void handleExit(){
        Stage stage = (Stage) rootAnchorPane.getScene().getWindow();
        stage.getOnCloseRequest().handle(null);
        stage.close();
        Platform.exit();
    }

    public void handleHelp(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinWidth(1000);
        alert.getDialogPane().setMinHeight(600);
        alert.setTitle("BOIDS");
        alert.setHeaderText("Made by Huang Yuebin");
        String help = """
                Welcome to Boids!
                This is a program designed to simulate the behaviour of birds and bird flocking. Each bird follows 4 simple rules, alignment, coherence, separation, and collision-avoidance. Alignment is following the direction of adjacent birds, coherence is moving closer to the center of a flock, separation is equally distancing itself from adjacent birds, and collision-avoidance is avoiding walls as they will die if they crash into them.
                Users can hover their cursor over the labels of each function to get a better understanding of what the functions do.
                To get started, you can click the play button under the BOIDS SIMULATION TitledPane and the birds will start flying. However, birds flying in an empty space is quite boring. You can change how the birds behave in the BIRD SETTINGS TitledPane.
                To make things even more interesting, you can change the map design itself under MAP SETTINGS! Start by setting a wall-spawn chance of around 60%. You should get something that looks like a QR code. Next, you can smoothen out the map by clicking the Smoothen button. I am using Cellular Automata for this process. Keep smoothening the map until you find it to be satisfactory. You can change the seed for the random function by clearing the text-field and hitting the Enter key, which changes how the walls are randomly spawned.
                There are map editor tools designed for you to draw on the map itself. You can draw/erase using the draw/erase radio buttons and drag your mouse across the areas you want to draw/erase on the map. A paint bucket / fill function is provided for you to easily fill areas of the map with walls or empty space. This is done through the Flood Fill algorithm implemented using Breadth-First-Search and a Queue data structure for more efficient filling.
                Users can also change the theme of the UI through the APPEARANCE TitledPane. There are 4 pre-set themes available for use. The color of the walls, background and birds in the main screen can be changed to be different from the theme.
                If users wish to save the beautiful map, the bird settings, or their handpicked colors for use next time, they can save it under BOIDS SIMULATION. Users can select which settings they wish to include in the saved file, such as Appearance, Map Settings and Bird Settings. Users can then load the saved file under BOIDS SIMULATION as well.
                I hope you have enjoyed this program as much as I did, and that it had helped you appreciate the beauty of bird movement, how complex behavior can arise from each bird following simple rules.
                """;
        alert.setContentText(help);
        alert.show();
    }

    public void handleLoad(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(rootAnchorPane.getScene().getWindow());
        if (file != null){
            ArrayList<String[]> data = FileIO.readFile(file);
            if (data != null){
                try{
                    processData(data);
                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Error reading file");
                    alert.setContentText("File chosen might be corrupted or have invalid data.\n" +
                            "Please choose a different file instead or try again.");
                    alert.show();
                }
            }
        }
    }

    public void handleSave(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(rootAnchorPane.getScene().getWindow());
        if (file != null){
            FileIO.writeFile(file, getSaveData());
        }
    }

    public void processData(ArrayList<String[]> data){
        numBirdsSlider.setValue(Double.parseDouble(data.get(0)[0]));
        String[] tokens;
        if (!(tokens = data.get(1))[0].equals("null")){
            themeCB.setValue(tokens[0]);
            wallColorCP.setValue(Color.web(tokens[1]));
            backgroundColorCP.setValue(Color.web(tokens[2]));
            birdColorCP.setValue(Color.web(tokens[3]));
            birdSizeSlider.setValue(Double.parseDouble(tokens[4]));
            birdAngleSlider.setValue(Double.parseDouble(tokens[5]));
        }
        if (!(tokens = data.get(2))[0].equals("null")){
            for (int i=0; i<tokens.length; i++){
                birdSliders[i].setValue(Double.parseDouble(tokens[i]));
            }
        }
        if (!(tokens = data.get(3))[0].equals("null")){

            seedTF.setText(tokens[0]);
            mapStats.setRSeed(Long.parseLong(tokens[0]));
            for (int i=0; i<mapSliders.length; i++){
                mapSliders[i].setValue(Double.parseDouble(tokens[i+1]));
            }
            iterationsPassedLbl.setText("Smoothened: " + tokens[4]);
            mapGrid.setIterationsPassed(Integer.parseInt(tokens[4]));
            iterationsTF.setText(tokens[5]);

            int lengthX = Integer.parseInt(data.get(4)[0]);
            int lengthY = Integer.parseInt(data.get(4)[1]);
            boolean[][] map = new boolean[lengthX][lengthY];
            for (int x=0; x<lengthX; x++){
                tokens = data.get(5+x);
                for (int y=0; y<lengthY; y++){
                    map[x][y] = tokens[y].equals("1");
                }
            }
            mapGrid.setTerrain2DArray(map);
        }
        mapGrid.reload();
    }

    public String getSaveData(){
        // FORMAT:
        // null for not saved
        // number of birds
        // theme, wall color, bg color, bird color, bird size, bird angle
        // bird settings
        // map seed, grid square size, wall spawn chance, threshold, smoothened, smoothen
        // mapGrid X, mapGrid Y
        // mapGrid (2d)
        StringBuilder data = new StringBuilder();
        data.append(numBirdsSlider.getValue()).append(",\n");

        if (saveAppearanceCB.isSelected()){
            data.append(themeCB.getValue()).append(",").append(wallColorCP.getValue()).append(",").append(backgroundColorCP.getValue()).append(",")
                    .append(birdColorCP.getValue()).append(",").append(birdSizeSlider.getValue()).append(",").append(birdAngleSlider.getValue()).append(",\n");
        } else {
            data.append("null,\n");
        }

        if (saveBirdSettingsCB.isSelected()){
            for (int i=0; i<birdSliders.length-2; i++){
                data.append(birdSliders[i].getValue()).append(",");
            }
            data.append("\n");
        } else {
            data.append("null,\n");
        }

        if (saveMapSettingsCB.isSelected()){
            data.append(mapStats.getRSeed()).append(",");
            for (Slider slider: mapSliders){
                data.append(slider.getValue()).append(",");
            }
            data.append(mapGrid.getIterationsPassed()).append(",").append(iterationsTF.getText()).append(",\n");

            boolean[][] map = mapGrid.getTerrain2DArray();
            data.append(map.length).append(",").append(map[0].length).append(",");
            data.append("\n");

            for (boolean[] line: map){
                for (boolean bool: line){
                    data.append(bool ? 1 : 0).append(",");
                }
                data.append("\n");
            }
            data.append("\n");

        } else {
            data.append("null,\n");
        }

        return data.toString();
    }

    public void handleIncrementSmoothen(){
        if (Integer.parseInt(iterationsTF.getText()) >= 500){
            iterationsTF.setText("500");
        }
        iterationsTF.setText("" + (Integer.parseInt(iterationsTF.getText()) + 1));
    }

    public void handleDecrementSmoothen(){
        if (iterationsTF.getText().equals("0")){
            return;
        }
        iterationsTF.setText("" + (Integer.parseInt(iterationsTF.getText()) - 1));
    }

    public void handleNextIteration(){
        if (iterationsTF.getText().equals("")){
            iterationsTF.setText("1");
        }
        for (int i=0; i<Integer.parseInt(iterationsTF.getText()); i++){
            mapGrid.nextIteration();
        }
        iterationsPassedLbl.setText("Smoothened: " + mapGrid.getIterationsPassed());
    }

    public void handleSeed(){
        if (seedTF.getText().equals("")){
            long seed = new Random().nextLong();
            mapStats.setRSeed(seed);
            seedTF.setText("" + seed);
        } else{
            mapStats.setRSeed(Long.parseLong(seedTF.getText()));
        }
        iterationsPassedLbl.setText("Smoothened: 0");
        mapGrid.update();
    }

    public void handlePlay(){
        if (!play){
            playBtn.setText("⏸");
            play = true;

        } else {
            playBtn.setText("▶");
            play = false;
        }
    }

    public void handleNextStep(){
        nextStep = true;
    }

    public void handleReload(){
        mapGrid.update();
    }

    public void drawMapGrid(){
        for(Vector v: mapGrid.getWalls()){
            graphicsContext.fillRect(v.getY(), v.getX(), mapStats.getGridSquareSize(), mapStats.getGridSquareSize());
        }
    }

    public void drawBirds(double radius, double theta){
        for (Bird b: boids.getBirds()){
            double[][] triangle = b.getTriangle(radius, theta);
            graphicsContext.fillPolygon(triangle[1], triangle[0], triangle[0].length);
        }
    }
}
