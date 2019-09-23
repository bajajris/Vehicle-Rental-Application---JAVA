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
 * VehicleChooser - a type of grid pane to choose vehicles to modify
 *
 * @author rishabhbajaj
 */
public class VehicleChooser extends GridPane {

    // nodes to add
    Button carButton;
    Button truckButton;
    Button bikeButton;
    Button logout;
    Stage primaryStage;
    Label heading;
    StackPane pane1, pane2, pane3;

    //button graphics
    Image icon1 = new Image("car.png");
    ImageView view1 = new ImageView(icon1);
    Image icon2 = new Image("bike.png");
    ImageView view2 = new ImageView(icon2);
    Image icon3 = new Image("truck.png");
    ImageView view3 = new ImageView(icon3);
    Image icon4 = new Image("logout.png");
    ImageView view4 = new ImageView(icon4);

    /**
     * Initialize vehicle chooser grid pane
     * @param primaryStage 
     */
    public VehicleChooser(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // setting nodes to the pane
        heading = new Label("SELECT A VEHICLE");
        heading.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
        super.add(heading, 0, 0, 7, 1);

        // styling of labels using circles and stackpane
        Circle circle1 = new Circle(15);
        circle1.setStrokeWidth(40);
        circle1.setStroke(Color.WHITE);
        circle1.setFill(Color.WHITE);
        Circle circle2 = new Circle(15);
        circle2.setStrokeWidth(40);
        circle2.setStroke(Color.WHITE);
        circle2.setFill(Color.WHITE);
        Circle circle3 = new Circle(15);
        circle3.setStrokeWidth(40);
        circle3.setStroke(Color.WHITE);
        circle3.setFill(Color.WHITE);

        pane1 = new StackPane();
        pane2 = new StackPane();
        pane3 = new StackPane();

        // pane alignment
        setVgap(30);
        setHgap(8);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(30));
        Font font = new Font(30);

        // root pane nodes
        Label tag1 = new Label("1.");
        tag1.setFont(font);
        pane1.getChildren().addAll(circle1, tag1);
        super.add(pane1, 0, 1);

        Label tag2 = new Label("2.");
        tag2.setFont(font);
        pane2.getChildren().addAll(circle2, tag2);
        super.add(pane2, 0, 2);

        Label tag3 = new Label("3.");
        tag3.setFont(font);
        pane3.getChildren().addAll(circle3, tag3);
        super.add(pane3, 0, 3);

        // car selector button
        carButton = new Button("CAR");
        super.add(carButton, 1, 1, 2, 1);
        carButton.setOnAction(event -> options(event)); // options() to determine selected button

        truckButton = new Button("TRUCK");
        super.add(truckButton, 1, 2, 2, 1);
        truckButton.setOnAction(event -> options(event));

        bikeButton = new Button("BIKE");
        super.add(bikeButton, 1, 3, 2, 1);
        bikeButton.setOnAction(event -> options(event));

        // go back to login screen
        logout = new Button("Logout");
        super.add(logout, 2, 4, 1, 1);
        logout.setOnAction(event -> options(event));
        setMargin(logout, new Insets(10, 20, 10, 65));
        bikeButton.setPrefSize(300, 70);
        truckButton.setPrefSize(300, 70);
        carButton.setPrefSize(300, 70);
        logout.setPrefSize(180, 40);

        // set graphics
        setGraphics();

        primaryStage.setTitle("Select a Vehicle");
    }

    /**
     * choose a vehicle
     *
     * @param event
     */
    private void options(ActionEvent event) {
        Button vehicleChosen = (Button) event.getSource(); //check button source
        switch (vehicleChosen.getText()) {
            case "CAR":
                VehicleRental.vehicleOperations(vehicleChosen);
                break;
            case "TRUCK":
                VehicleRental.vehicleOperations(vehicleChosen);
                break;
            case "BIKE":
                VehicleRental.vehicleOperations(vehicleChosen);
                break;
            case "Logout":
                VehicleRental.loginPage();
                break;
        }
    }

    /**
     * Set styling to nodes.
     */
    private void setGraphics() {
        // icon size
        view1.setFitHeight(50);
        view1.setFitWidth(140);
        view2.setFitHeight(70);
        view2.setFitWidth(120);
        view3.setFitHeight(70);
        view3.setFitWidth(110);
        view4.setFitHeight(40);
        view4.setFitWidth(40);

        // button font and graphic
        carButton.setFont(Font.font(30));
        carButton.setGraphicTextGap(45);
        carButton.setGraphic(view1);

        bikeButton.setFont(Font.font(30));
        bikeButton.setGraphicTextGap(45);
        bikeButton.setGraphic(view2);

        truckButton.setFont(Font.font(30));
        truckButton.setGraphicTextGap(35);
        truckButton.setGraphic(view3);

        logout.setFont(Font.font(20));
        logout.setGraphicTextGap(20);
        logout.setGraphic(view4);

        // color nodes
        this.setStyle("-fx-background-color: #08B7B7");
        heading.setTextFill(Color.rgb(93, 89, 89));
        carButton.setStyle("-fx-background-color: white");
        bikeButton.setStyle("-fx-background-color: white");
        truckButton.setStyle("-fx-background-color: white");
        logout.setStyle("-fx-background-color: white");

    }
}
