package rishabh_bajaj_project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * New stage to give information about the App and configure app Name
 *
 * @author rishabhbajaj
 */
public class VehicleInfo extends Stage {

    TextField appName;
    ToggleGroup grpSize;
    Text appText = new Text();
    Label helpLabel;
    GridPane pane;

    public VehicleInfo() {
        pane = new GridPane();
        pane.setVgap(20);

        //stage heading and font
        helpLabel = new Label("App Description and Help");
        helpLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));

        //change appame textField
        appName = new TextField();
        appName.setPromptText("Enter new app name");

        //help text to define app's purpose
        appText.setText("A simple “Vehicle Rental System” that allows the clients to list cars, trucks and bikes for\n"
                + "renting. The client can add a new vehicle to the renting list by adding all the specifications\n"
                + "for the vehicle, remove a vehicle from listing or view all the vehicles and their specifications\n"
                + "that has been listed for renting.");
        appText.setFont(Font.font(20));

        //customize radio buttons to change appName font size
        grpSize = new ToggleGroup();
        RadioButton rad1 = new RadioButton("Font Size: 30");
        rad1.setToggleGroup(grpSize);
        rad1.setSelected(true);
        VBox.setMargin(rad1, new Insets(10, 0, 0, 0));

        RadioButton rad2 = new RadioButton("Font Size: 35");
        rad2.setToggleGroup(grpSize);

        RadioButton rad3 = new RadioButton("Font Size: 40");
        rad3.setToggleGroup(grpSize);
        
        // add font selector column
        pane.addColumn(0, rad1, rad2, rad3);

        Button btnClose = new Button("OK");
        // simply close the stage when user hits OK
        btnClose.setOnAction(event -> this.close());

        VBox root = new VBox(15, helpLabel, appText, appName, pane, btnClose);
        root.setPadding(new Insets(10));
        setScene(new Scene(root));
        // stage title
        setTitle("App Information");
    }

    /**
     * This is the main entry point for the Stage
     *
     * @param title The current title of the main Stage
     * @return
     */
    public AppInfo getInfo(String title) {

        appName.setText(title);

        // this will prevent the user from clicking on the main stage
        // before this one is done
        this.initModality(Modality.APPLICATION_MODAL);
        showAndWait();
        // this will cause the code to wait before continuing until this
        // stage is closed showAndWait(); // a synchronous call
        // this code will run AFTER the user clicks OK
        RadioButton selected = (RadioButton) grpSize.getSelectedToggle();

        Font font = Font.font("Comic Sans Ms", Double.parseDouble(selected.getText().substring(11)));

        return new AppInfo(appName.getText(), font);
    }
}
