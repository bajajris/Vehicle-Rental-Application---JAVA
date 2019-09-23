package rishabh_bajaj_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Enter bike details into an array list to view and update
 *
 * @author Rishabh
 */
public class BikeEntry extends Stage {

    Button add, addFile, back, saveFile; // root pane nodes
    CheckBox append = new CheckBox("Append"); // checckbox to append data in an existing file
    Button vehicleChosen = new Button("BIKE"); // setting the vehicle chosen to BIKE
    TextField yearText, makeText, modelText, rentText;  // input textfields for data fields
    ComboBox<String> body = new ComboBox<>(); // body combobox
    ComboBox<String> engine = new ComboBox<>(); // engine combobox
    public static ArrayList<Bike> bike = new ArrayList<>(); // static bike arraylist 
    File file;
    GridPane root = new GridPane(); // main root pane
    Label heading; // main heading

    /**
     * Setting nodes and their actions
     * @param bike
     */
    public BikeEntry(ArrayList bike) {
        BikeEntry.bike = bike;
        // root positionig and aligments
        root.setVgap(25);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 50, 20, 50));

        // make other stage un accessible
        this.initModality(Modality.APPLICATION_MODAL);

        /*
            Initializing and positioning nodes in the root grid pane
            Setting Labels font
         */
        heading = new Label("ADD VEHICLE");
        heading.setFont(Font.font(40));
        root.add(heading, 1, 0, 7, 1);

        Label make = new Label("MAKE :");
        makeText = new TextField();
        root.add(make, 0, 1);
        root.add(makeText, 1, 1, 2, 1);

        Label model = new Label("MODEL :");
        modelText = new TextField();
        root.add(model, 0, 2);
        root.add(modelText, 1, 2, 2, 1);

        Label year = new Label("YEAR :");
        yearText = new TextField();

        /* 
            Text formatter to convert any string entered into a number
            If characters entered removes makes the field empty.
            Only accepts a valid number
         */
        yearText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        root.add(year, 0, 3);
        root.add(yearText, 1, 3, 2, 1);

        //setting the max Characters to 3 
        // textfield will not accept more than 4 numbers 
        yearText.setOnKeyTyped(event -> {
            int maxCharacters = 3;
            if (yearText.getText().length() > maxCharacters) {
                event.consume(); // stops further entering of number into the field
            }
        });

        Label rent = new Label("RENT :");
        rentText = new TextField();
        rentText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        root.add(rent, 0, 4);
        root.add(rentText, 1, 4, 3, 1);

        rentText.setOnKeyTyped(event -> {
            int maxCharacters = 2;
            if (rentText.getText().length() > maxCharacters) {
                event.consume();
            }
        });

        // making a combo box of body and engine
        Label bodyLabel = new Label("BODY TYPE :");
        root.add(bodyLabel, 0, 5);
        String[] bodyType = {"Cruiser", "Dirt Bike", "Motor Cross", "Choppers", "Enduro Bikes", "Sport", "Scooters"};

        // observable list of string bodyType to add to the combo box body
        ObservableList listBody = FXCollections.observableArrayList(bodyType);
        body.setItems(listBody); // setting list to combo box
        root.add(body, 1, 5, 2, 1);
        body.setPromptText("Select Bike Body Type");

        Label engineLabel = new Label("ENGINE:");
        root.add(engineLabel, 0, 6);
        String[] engineType = {"125cc", "250cc", "350cc", "500cc"};

        // observable list of string engineType to add to the combo box engine
        ObservableList engineList = FXCollections.observableArrayList(engineType);
        engine.setItems(engineList); // setting list to combo box
        root.add(engine, 1, 6, 2, 1);
        engine.setPromptText("Select Bike's Engine Type");

        // adding button nodes to root pane
        add = new Button("ADD VEHICLE");
        add.setOnAction(event -> {
            choice(event);
        });
        root.add(add, 0, 7, 6, 1);

        addFile = new Button("ADD FROM FILE");
        addFile.setOnAction(event -> {
            choice(event);
        });
        root.add(addFile, 0, 8, 7, 1);

        root.add(append, 1, 9);

        saveFile = new Button("SAVE IN FILE");
        saveFile.setOnAction(event -> {
            choice(event);
        });
        root.add(saveFile, 2, 9);

        back = new Button("OK");
        back.setOnAction(event -> {
            choice(event);
        });

        root.add(back, 2, 10);
        add.setMaxWidth(Double.MAX_VALUE);
        addFile.setMaxWidth(Double.MAX_VALUE);

        //calling the setGraphics() to add styles to the nodes
        setGraphics();

        //initializing and setting the scene and stage
        Scene scene = new Scene(root);
        setScene(scene);
        super.sizeToScene();
        setTitle("Add Bike Info"); //set stage title
        show(); //show stage
    }

    /**
     * Add data to bike arrayList
     *
     * @param makeText
     * @param yearText
     * @param rentText
     * @param modelText
     * @param engine
     * @param body
     */
    private void enterData(TextField makeText, TextField yearText, TextField rentText, TextField modelText, ComboBox engine, ComboBox body) {
        try {
            bike.add(new Bike(body.getSelectionModel().getSelectedItem().toString(), engine.getSelectionModel().getSelectedItem().toString(), makeText.getText(), modelText.getText(), yearText.getText(), rentText.getText()));
            clearData();
        } catch (IllegalArgumentException ex) {
            // alert user if exception is encountered
            String alertTitle = "Error";
            String alertHeader = "Wrong input";
            String alertContent = ex.getMessage();
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
        }
    }

    /**
     * Choice of the button pressed
     *
     * @param event get source of the button pressed
     */
    private void choice(ActionEvent event) {
        Button action = (Button) event.getSource();
        switch (action.getText()) {
            case "ADD VEHICLE":
                if (validate()) { // validate to check if ay field is empty
                    enterData(makeText, yearText, rentText, modelText, engine, body);//enter data into array list of four wheel
                }
                break;
            case "ADD FROM FILE":
                enterData();  // call the overloaded function to add data to list from file
                break;
            case "SAVE IN FILE":
                if (bike.size() > 0) {
                    writeFile(); // write data in file if arrayList not empty
                }
                break;
            case "OK":
                this.close();  //close the stage
                break;
        }
    }

    /**
     * Overloaded function to enter data from a file
     */
    private void enterData() {

        //open file chooser to get file
        FileChooser chooser = new FileChooser();

        File fileSet = new File("./src/BikeFiles");
        chooser.setInitialDirectory(fileSet);

        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"), new FileChooser.ExtensionFilter("CSV files", "*.csv") // add extension filters for file chooser
        );
        File file1 = chooser.showOpenDialog(this);
        if (file1 != null) {
            try {
                //read file
                Scanner in = new Scanner(file1);
                // read line by line
                while (in.hasNextLine()) {
                    String linein = in.nextLine();
                    // add by calling the overloaded constructor of Bike class
                    bike.add(new Bike(linein));
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * Write to a file.
     */
    private void writeFile() {
        //get the file from the file chooser
        FileChooser chooser = new FileChooser();

        File fileSet = new File("./src/BikeFiles");
        chooser.setInitialDirectory(fileSet);

        // add extension filters
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text Files", "*.txt"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        // append into a file if selected
        if (append.isSelected()) {
            file = chooser.showOpenDialog(this);
        } else {
            file = chooser.showSaveDialog(this);
        }
        if (file == null) {
            System.out.println("Cancelled"); //User clicks Cancel
        } else {
            // print line by line by czlling the toString method
            try (FileWriter fileWriter = new FileWriter(file, true); PrintWriter writer = new PrintWriter(fileWriter)) {
                for (int i = 0; i < bike.size(); i++) {
                    writer.println(bike.get(i).toString());
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    /**
     * Clears the entered data after each adding to file.
     */
    private void clearData() {
        makeText.clear();
        yearText.clear();
        rentText.clear();
        modelText.clear();
        engine.getSelectionModel().clearSelection();
        body.getSelectionModel().clearSelection();
    }

    /**
     * validates that the fields are not empty
     *
     * @return
     */
    private boolean validate() {
        if (!yearText.getText().isEmpty() && !makeText.getText().isEmpty() && !modelText.getText().isEmpty() && !rentText.getText().isEmpty() && !body.getSelectionModel().isEmpty() && !engine.getSelectionModel().isEmpty()) {
            return true;
        } else {
            //display alert message if empty
            String alertTitle = "Error";
            String alertHeader = "Empty Fields";
            String alertContent = "One or more field is empty";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);

            return false;
        }
    }

    /**
     * set styling for the nodes.
     */
    private void setGraphics() {

        // change the font size
        saveFile.setFont(Font.font(25));

        addFile.setFont(Font.font(25));

        add.setFont(Font.font(25));

        back.setFont(Font.font(18));

        //add color to the nodes
        root.setStyle("-fx-background-color: #08B7B7");
        heading.setTextFill(Color.rgb(93, 89, 89));
        saveFile.setStyle("-fx-background-color: white");
        add.setStyle("-fx-background-color: white");
        addFile.setStyle("-fx-background-color: white");
        back.setStyle("-fx-background-color: white");

        body.setStyle("-fx-background-color: white");
        engine.setStyle("-fx-background-color: white");

        // add mouse enter and exit property to add styles
        add.setOnMouseEntered(e -> {
            add.setStyle("-fx-background-color: #0008FF;");
            add.setTextFill(Color.WHITE);
        });
        add.setOnMouseExited(e -> {
            add.setStyle("-fx-background-color: white");
            add.setTextFill(Color.BLACK);
        });
        addFile.setOnMouseEntered(e -> {
            addFile.setStyle("-fx-background-color: #0008FF;");
            addFile.setTextFill(Color.WHITE);
        });
        addFile.setOnMouseExited(e -> {
            addFile.setStyle("-fx-background-color: white");
            addFile.setTextFill(Color.BLACK);
        });
        saveFile.setOnMouseEntered(e -> {
            saveFile.setStyle("-fx-background-color: #0008FF;");
            saveFile.setTextFill(Color.WHITE);
        });
        saveFile.setOnMouseExited(e -> {
            saveFile.setStyle("-fx-background-color: white");
            saveFile.setTextFill(Color.BLACK);
        });

    }
}
