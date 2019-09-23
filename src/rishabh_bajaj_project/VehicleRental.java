package rishabh_bajaj_project;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class to start application
 *
 * @author rishabhbajaj
 */
public class VehicleRental extends Application {

    // declare nodes
    BorderPane root;  // root pane for the scene
    static Scene scene1, scene2, scene3; // three different scenes
    static Stage primaryStage; //main stage for the application
    static Button vehicleChosen; //vehicleChosen button to get the source of vehicle selected for changes 
    static ArrayList<Car> car = new ArrayList<>();
    static ArrayList<Bike> bike = new ArrayList<>();
    static ArrayList<Truck> truck = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        VehicleRental.primaryStage = primaryStage;
        loginPage(); // loginPage method to display login pane in scene1
        root.setStyle("-fx-background-color: DAE6F3;");
        primaryStage.sizeToScene(); //setting it size to scene
        primaryStage.setTitle("Vehicle Rental System");
        primaryStage.show();//show stage
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    // call the login page to display
    public static void loginPage() {
        LoginPage login = new LoginPage(primaryStage);
        scene1 = new Scene(login);
        primaryStage.setScene(scene1); // set scene1 login page
        primaryStage.sizeToScene();
    }

    /**
     * Vehicle Chooser if password correct
     *
     * @param passwordValidated login password correct
     */
    public static void vehicleChooser(boolean passwordValidated) {
        if (passwordValidated == true) {
            VehicleChooser chooser = new VehicleChooser(primaryStage);
            scene2 = new Scene(chooser);
            primaryStage.setScene(scene2); // scene2 vehicle chooser 
        }
    }

    /**
     * Vehicle actions
     *
     * @param vehicleChosen vehicle selected source
     */
    public static void vehicleOperations(Button vehicleChosen) {
        VehicleRental.vehicleChosen = vehicleChosen;
        VehicleOperations operations = new VehicleOperations(primaryStage);
        scene3 = new Scene(operations);
        primaryStage.setScene(scene3); // scene3 to add/view vehicle
        primaryStage.sizeToScene(); // stage to scene size 
    }

    //call different stages for vehicle entering
    public static void vehicleEntry() {
        switch (vehicleChosen.getText()) { // check vehicle selected
            case "CAR":
                CarEntry addCar = new CarEntry(car);
                break;
            case "BIKE":
                BikeEntry addBike = new BikeEntry(bike);
                break;
            case "TRUCK":
                TruckEntry addTruck = new TruckEntry(truck);
                break;
        }
    }

    /**
     * Update view stage
     */
    public static void updateandview() {
        UpdateAndView updateAndView = new UpdateAndView(vehicleChosen);
        primaryStage.sizeToScene();
    }
}
