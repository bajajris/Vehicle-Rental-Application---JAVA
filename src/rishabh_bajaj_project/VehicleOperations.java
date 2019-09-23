package rishabh_bajaj_project;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Select what to do with the vehicles
 *
 * @author rishabhbajaj
 */
public class VehicleOperations extends GridPane {

    // nodes added to roo pane
    Button addVehicle;
    Button removeVehicle;
    Button viewVehicle;
    Button back;
    Stage primaryStage;
    Button vehicleChosen; // source of vehicle chosen
    Label heading;
    // stack pane to pile up labels and circles
    StackPane pane1, pane2;
    // button graphics
    Image icon1 = new Image("add.png");
    ImageView view1 = new ImageView(icon1);
    Image icon2 = new Image("edit.png");
    ImageView view2 = new ImageView(icon2);
    Image icon3 = new Image("back.png");
    ImageView view3 = new ImageView(icon3);

    /**
     * Initialize vehicle action stage
     *
     * @param primaryStage
     */
    public VehicleOperations(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // pae positioning and alignments
        setVgap(30);
        setHgap(8);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));

        // initializig nodes and add to pane
        heading = new Label("CHOOSE ACTION");
        heading.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
        super.add(heading, 0, 0, 7, 1);

        // pile up circle to labels - styling
        Circle circle1 = new Circle(15);
        circle1.setStrokeWidth(40);
        circle1.setStroke(Color.WHITE);
        circle1.setFill(Color.WHITE);
        Circle circle2 = new Circle(15);
        circle2.setStrokeWidth(40);
        circle2.setStroke(Color.WHITE);
        circle2.setFill(Color.WHITE);

        Font font = new Font(30);

        pane1 = new StackPane();
        pane2 = new StackPane();

        Label tag1 = new Label("1.");
        tag1.setFont(font);
        pane1.getChildren().addAll(circle1, tag1);
        super.add(pane1, 0, 1);

        Label tag2 = new Label("2.");
        tag2.setFont(font);
        pane2.getChildren().addAll(circle2, tag2);
        super.add(pane2, 0, 2);

        // buttons to choose action
        addVehicle = new Button("ADD VEHICLE");
        super.add(addVehicle, 1, 1, 2, 1);
        addVehicle.setOnAction(event -> options(event)); // lambda expression to set button action

        viewVehicle = new Button("VIEW/UPDATE");
        super.add(viewVehicle, 1, 2, 2, 1);
        viewVehicle.setOnAction(event -> options(event));

        back = new Button("BACK");
        super.add(back, 2, 3, 1, 1);
        setMargin(back, new Insets(10, 20, 10, 65));
        back.setOnAction(event -> options(event));

        // button size
        addVehicle.setPrefSize(340, 70);
        viewVehicle.setPrefSize(340, 70);
        back.setMaxSize(180, 40);

        // call setGraphics() to set node styling
        setGraphics();

        primaryStage.setTitle("Select the action");
    }

    /**
     *
     * @param event get source of the button pressed
     */
    private void options(ActionEvent event) {
        Button action = (Button) event.getSource(); // getSource of the button pressed
        switch (action.getText()) {
            case "ADD VEHICLE":
                VehicleRental.vehicleEntry();
                break;
            case "VIEW/UPDATE":
                VehicleRental.updateandview();
                break;
            case "BACK":
                VehicleRental.vehicleChooser(true); // true for password validated
                break;
        }
    }

    /**
     * Set graphics to the node.
     */
    private void setGraphics() {

        //set image view size
        view1.setFitHeight(55);
        view1.setFitWidth(60);
        view2.setFitHeight(55);
        view2.setFitWidth(60);
        view3.setFitHeight(40);
        view3.setFitWidth(40);

        // add graphics to the node
        addVehicle.setFont(Font.font(30));
        addVehicle.setGraphicTextGap(35);
        addVehicle.setGraphic(view1);

        viewVehicle.setFont(Font.font(30));
        viewVehicle.setGraphicTextGap(35);
        viewVehicle.setGraphic(view2);

        back.setFont(Font.font(20));
        back.setGraphicTextGap(20);
        back.setGraphic(view3);

        // add colors to the nodes
        this.setStyle("-fx-background-color: #08B7B7");
        heading.setTextFill(Color.rgb(93, 89, 89));
        addVehicle.setStyle("-fx-background-color: white");
        viewVehicle.setStyle("-fx-background-color: white");
        back.setStyle("-fx-background-color: white");

    }
}
