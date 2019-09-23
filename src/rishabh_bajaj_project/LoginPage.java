package rishabh_bajaj_project;

import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Login Page - Border pane to display login information
 *
 * @author rishabhbajaj
 */
public class LoginPage extends BorderPane {

    private final String LOGIN = "Rishabh";
    private final String PASSWORD = "project";
    // declare data fields/nodes
    TextField login;
    PasswordField password; // to get password
    Label loginLabel, passwordLabel, title;
    boolean passwordValidated = false; // check if password is correct or not
    Stage primaryStage;
    //images on page
    Image help = new Image("help.png");
    ImageView view = new ImageView(help);
    Image carImage = new Image("performance.png");
    ImageView viewCar = new ImageView(carImage);
    GridPane pane = new GridPane();// main root pane

    /**
     * Initialize login page that is a border pane
     *
     * @param primaryStage
     */
    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // pane alignments
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setAlignment(Pos.CENTER);
        setPadding(new Insets(30)); // set pane inner padding

        loginLabel = new Label("Login:");
        pane.add(loginLabel, 0, 1);

        passwordLabel = new Label("Password:");
        pane.add(passwordLabel, 0, 2);

        login = new TextField();
        pane.add(login, 1, 1, 2, 1);

        password = new PasswordField();
        pane.add(password, 1, 2, 2, 1);

        // button set on action
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> validate());

        Button clear = new Button("Clear");
        clear.setOnAction(event -> clear());//clear text fields

        HBox buttonBox = new HBox(15, submitButton, clear);

        pane.add(buttonBox, 1, 3);

        title = new Label("Change App Name");
        title.setFont(Font.font("Comic Sans Ms", 25));
        setCenter(pane);
        setTop(title);
        setAlignment(title, Pos.CENTER);
        title.setAlignment(Pos.CENTER);

        // set mouse clicked event to image
        view.setOnMouseClicked(event -> {
            Font font = new Font(50);
            VehicleInfo appDescription = new VehicleInfo();

            //calling the getInfo method of VehicleInfo to get app ame
            AppInfo message = appDescription.getInfo(title.getText());

            // Because the stage is loaded modally and with showAndWait
            title.setText(message.getAppName());
            title.setFont(message.getFont());

            primaryStage.sizeToScene();
        });

        view.setFitHeight(30);
        view.setFitWidth(30);
        setBottom(view);

        viewCar.setFitHeight(200);
        viewCar.setFitWidth(200);
        setRight(viewCar);
        setAlignment(viewCar, Pos.CENTER);

      
        primaryStage.setTitle("Login Information");

        // call the set Graphics()
        setGraphics();
    }

    /**
     * clear all fields.
     */
    private void clear() {
        login.clear();
        password.clear();
    }

    //validate username password
    private void validate() {
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            //display alert message if empty

            String alertTitle = "Error";
            String alertHeader = "Please fix the Error";
            String alertContent = "Both Fields are required";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);

            passwordValidated = false;
        } else if (login.getText().equals(LOGIN) && password.getText().equals(PASSWORD)) //read password from the file and compare for login information
        {
            passwordValidated = true; // next scene to call true
            VehicleRental.vehicleChooser(passwordValidated);
        } else {
            String alertTitle = "Error";
            String alertHeader = "Wrong Login/Password";
            String alertContent = "The username/password entered is wrong";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
            passwordValidated = false; // wrong password
        }
    }

    /**
     * set styling to nodes
     */
    private void setGraphics() {
        // set background image and color
        setStyle("-fx-background-image: url('background_image.png');");
        loginLabel.setTextFill(Color.WHITE);
        passwordLabel.setTextFill(Color.WHITE);
        loginLabel.setFont(Font.font(25));
        passwordLabel.setFont(Font.font(25));
        title.setTextFill(Color.DEEPSKYBLUE);
    }
}
