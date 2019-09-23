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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Enter car vehicle details
 * @author rishabhbajaj
 */
public class CarEntry extends Stage {

    Button add, addFile, back, saveFile; // root pane nodes
    CheckBox append = new CheckBox("Append");// checckbox to append data in an existing file
    Button vehicleChosen = new Button("CAR"); // setting the vehicle chosen to Car
    TextField yearText, makeText, rentText; // texfields to enter
    public static ArrayList<Car> car = new ArrayList<>(); // static arrayList of truck
    ComboBox<String> body = new ComboBox<>(); // body combobox
    ComboBox<String> transmission = new ComboBox<>(); // transmission combobox
    ToggleGroup numSeats = new ToggleGroup(); // ToggleGroup to set the radio button
    File file;
    RadioButton seat1, seat2, seat3, seat4; // radio buttons for the seats
    Label heading;
    GridPane root = new GridPane(); // main root pane

    /**
     * Initialize car stage 
     * @param car 
     */
    public CarEntry(ArrayList car) {
        // make other stage un accessible
        this.initModality(Modality.APPLICATION_MODAL);
        CarEntry.car = car;

        // root positionig and aligments
        root.setVgap(25);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

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

        Label year = new Label("YEAR :");
        yearText = new TextField();
        root.add(year, 0, 2);
        root.add(yearText, 1, 2, 2, 1);
        /* 
            Text formatter to convert any string entered into a number
            If characters entered removes makes the field empty.
            Only accepts a valid number
         */
        yearText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        //setting the max Characters to 3 
        // textfield will not accept more than 4 numbers 
        yearText.setOnKeyTyped(event -> {
            int maxCharacters = 3;
            if (yearText.getText().length() > maxCharacters) {
                event.consume();//consumes extra character
            }
        });

        Label rent = new Label("RENT :");
        rentText = new TextField();
        root.add(rent, 0, 3);
        root.add(rentText, 1, 3, 2, 1);
        rentText.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));

        rentText.setOnKeyTyped(event -> {
            int maxCharacters = 2;
            if (rentText.getText().length() > maxCharacters) {
                event.consume();
            }
        });

        // setting radio buttons and adding to toggle group
        seat1 = new RadioButton("2");
        seat2 = new RadioButton("4");
        seat3 = new RadioButton("5");
        seat3.setSelected(true);
        seat4 = new RadioButton("7");
        seat1.setToggleGroup(numSeats);
        seat2.setToggleGroup(numSeats);
        seat3.setToggleGroup(numSeats);
        seat4.setToggleGroup(numSeats);

        Label seatLabel = new Label("NO. OF SEATS :");
        root.add(seatLabel, 0, 4);
        HBox radBox = new HBox(10, seat1, seat2, seat3, seat4);
        root.add(radBox, 1, 4, 2, 1);

        // combo box of transmission and body type
        Label bodyLabel = new Label("BODY TYPE :");
        root.add(bodyLabel, 0, 5);
        String[] bodyType = {"Caravan", "Sedan", "SUV", "Minivan", "Convertible", "Coupe", "Crossover"};

        ObservableList listBody = FXCollections.observableArrayList(bodyType);
        body.setItems(listBody);//setting list to combo box
        root.add(body, 1, 5, 2, 1);
        body.setPromptText("Select Car Body Type");

        Label transmissionLabel = new Label("TRANSMISSION :");
        root.add(transmissionLabel, 0, 6);
        String[] transmissionType = {"Automatic", "Manual"};

        ObservableList listTransmission = FXCollections.observableArrayList(transmissionType);
        transmission.setItems(listTransmission);
        root.add(transmission, 1, 6, 2, 1);
        transmission.setPromptText("Select Transmission");

        add = new Button("ADD VEHICLE");
        add.setOnAction(event -> {
            choice(event);
        });
        root.add(add, 0, 7, 7, 1);

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
        setTitle("Add Car Info");
        show();
    }

    /**
     * Choice of the button pressed
     *
     * @param event get source of the button pressed
     */
    private void choice(ActionEvent event) {
        Button action = (Button) event.getSource(); // get button source
        switch (action.getText()) {
            case "ADD VEHICLE":
                if (validate()) { // validate to check if ay field is empty
                    enterData(makeText, yearText, rentText, body, transmission);//enter data into array list of four wheel
                }
                break;
            case "ADD FROM FILE":
                enterData(); // call the overloaded function to add data to list from file
                break;
            case "SAVE IN FILE":
                if (car.size() > 0) {
                    writeFile(); // saves into a file
                }
                break;
            case "OK":
                this.close(); //close the stage
        }
    }

    /**
     * Add data to Car arrayList
     *
     * @param makeText
     * @param yearText
     * @param rentText
     * @param body
     * @param transmission
     */
    private void enterData(TextField makeText, TextField yearText, TextField rentText, ComboBox body, ComboBox transmission) {
        RadioButton selected = (RadioButton) numSeats.getSelectedToggle();
        try {
            car.add(new Car(body.getSelectionModel().getSelectedItem().toString(), selected.getText(), transmission.getSelectionModel().getSelectedItem().toString(), makeText.getText(), yearText.getText(), rentText.getText()));
            clear();
        } catch (IllegalArgumentException ex) {
            String alertTitle = "Error";
            String alertHeader = "Wrong input";
            String alertContent = ex.getMessage();
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
        }
    }

    /**
     * Overloaded function to enter data from a file.
     */
    private void enterData() {
        //open file chooser to get file
        FileChooser chooser = new FileChooser();

        File fileSet = new File("./src/CarFiles");
        chooser.setInitialDirectory(fileSet);

        // add extension filters
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text Files", "*.txt"), new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file1 = chooser.showOpenDialog(this);
        if (file1 != null) {
            try {
                //read file
                Scanner in = new Scanner(file1);
                while (in.hasNextLine()) {
                    String linein = in.nextLine();
                    // add by calling the overloaded constructor of Bike class
                    car.add(new Car(linein));
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

        File fileSet = new File("./src/CarFiles");
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
            System.out.println("Cancelled");//User clicks Cancel
        } else {
            // print line by line by calling the toString method
            try (FileWriter fileWriter = new FileWriter(file, true); PrintWriter writer = new PrintWriter(fileWriter)) {
                for (int i = 0; i < car.size(); i++) {
                    writer.println(car.get(i).toString());
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
    private void clear() {
        makeText.clear();
        yearText.clear();
        rentText.clear();
        body.getSelectionModel().clearSelection();
        transmission.getSelectionModel().clearSelection();
    }

    /**
     * validates that the fields are not empty
     *
     * @return
     */
    private boolean validate() {
        if (!yearText.getText().isEmpty() && !makeText.getText().isEmpty() && !rentText.getText().isEmpty() && !body.getSelectionModel().isEmpty() && !transmission.getSelectionModel().isEmpty()) {
            return true;
        } else {
            //display alert message if empty
            String alertTitle = "Error";
            String alertHeader = "Empty Fields";
            String alertContent = "One or more fiels ids empty";
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
        transmission.setStyle("-fx-background-color: white");

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
