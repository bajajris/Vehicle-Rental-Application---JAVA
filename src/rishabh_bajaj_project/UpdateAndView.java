package rishabh_bajaj_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A new Stage to update and view existing record in table view and allow
 * modification in them.
 *
 * @author rishabhbajaj
 */
public final class UpdateAndView extends Stage {

    private int selectedIndex = 1;  //keep track of selected index in the table
    Button vehicleChosen = new Button(); //source of vehicle chosen to determine the next step
    boolean fileSaved; // keep track if the file is already saved
    Button saveButton, delButton, back, clearButton; // buttons on the pane
    CheckBox append = new CheckBox("Append"); // checkbox to determine to append or save a new file
    File file;
    TableColumn make, model, year, rent, transmission, engine, body, seats, capacity; // all table columns
    VBox root;// root pane
    private TableView<Vehicle> vehicleTable = new TableView<>();  // vehicle table to display a table - using vehicle iterface as type
    private ObservableList<Vehicle> data;  // observable list of vehicle
    Label label; // heading of the stage

    /**
     * Constructor to initialize the new Stage
     *
     * @param vehicleChosen vehicle chosen to determine the correct action as
     * per the vehicle
     */
    public UpdateAndView(Button vehicleChosen) {
        root = new VBox();
        setTitle("Update/View Records");

        /* this will prevent the user from clicking on the main stage
        before this one is done */
        this.initModality(Modality.APPLICATION_MODAL);

        //clearing the table
        vehicleTable.getSelectionModel().clearSelection();

        //pane alignments and padding
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        this.vehicleChosen = vehicleChosen;

        initializeColumns(); // caliig the initializeColumns() to get the columns and their type and value from vehicle classes

        // using the vehicle source to determine the type of vehicle set
        switch (vehicleChosen.getText()) {
            case "CAR":
                listCars(); // set specific car Columns in the table
                break;
            case "BIKE":
                listBikes(); // set specific bike Columns in the table
                break;
            case "TRUCK": 
                listTrucks(); // set specific truck Columns in the table
                break;
        }
        /*
        sets the layout of the nodes in the pane
        calls the setGraphics() to add styles to the panes
         */
        setLayout();
    }

    /*
     removeSelectedVehicle() to delete the element from the table and also
     remove the element from the arrayList of that vehicle
     Called when delete button is pressed
     */
    public void removeSelectedVehicle() {
        fileSaved = false; // change the fileSaved to false
        if (!vehicleTable.getSelectionModel().isEmpty()) {
            data.remove(selectedIndex); // remove selected element from the table

            //switch-case to determine the vehicle and remove the selected element from the arraylist of that vehicle
            switch (vehicleChosen.getText()) {
                case "CAR":
                    CarEntry.car.remove(selectedIndex);
                    break;
                case "BIKE":
                    BikeEntry.bike.remove(selectedIndex);
                    break;
                case "TRUCK":
                    TruckEntry.truck.remove(selectedIndex);
                    break;
            }
            //clear selection
            vehicleTable.getSelectionModel().clearSelection();
        } else if (data.isEmpty()) {
            String alertTitle = "Error";
            String alertHeader = "Table is Empty";
            String alertContent = "Add a record to to update it!!";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
        } else {
            String alertTitle = "Error";
            String alertHeader = "Select and Delete";
            String alertContent = "Select a record to delete from the table!!";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
        }
    }

    /**
     * Initialize columns - set their width and value.
     */
    private void initializeColumns() {
        make = new TableColumn("Make");
        make.setMinWidth(100); // set min width of the columns
        // get the value of the column from the make datafield of the particular Vehicle class
        make.setCellValueFactory(new PropertyValueFactory<>("make"));

        year = new TableColumn("Year");
        year.setMinWidth(100);
        // get the value of the column from the year datafield of the particular Vehicle class
        year.setCellValueFactory(new PropertyValueFactory<>("year"));

        capacity = new TableColumn("Load Capacity");
        capacity.setMinWidth(130);
        // get the value of the column from the loadingCapacity datafield of the particular Vehicle class
        capacity.setCellValueFactory(new PropertyValueFactory<>("loadingCapacity"));

        seats = new TableColumn("Seats");
        seats.setMinWidth(100);
        seats.setCellValueFactory(new PropertyValueFactory<>("noOfSeats"));

        body = new TableColumn("Body");
        body.setMinWidth(100);
        // get the value of the column from the body datafield of the particular Vehicle class
        body.setCellValueFactory(new PropertyValueFactory<>("body"));

        transmission = new TableColumn("Transmission");
        transmission.setMinWidth(130);
        // get the value of the column from the transmission datafield of the particular Vehicle class
        transmission.setCellValueFactory(new PropertyValueFactory<>("transmission"));

        rent = new TableColumn("Rent($/day)");
        rent.setMinWidth(120);
        // get the value of the column from the baseRent datafield of the particular Vehicle class
        rent.setCellValueFactory(new PropertyValueFactory<>("baseRent"));

        model = new TableColumn("Model");
        model.setMinWidth(100);
        // get the value of the column from the model datafield of the particular Vehicle class
        model.setCellValueFactory(new PropertyValueFactory<>("model"));

        engine = new TableColumn("Engine");
        engine.setMinWidth(100);
        // get the value of the column from the engine datafield of the particular Vehicle class
        engine.setCellValueFactory(new PropertyValueFactory<>("engine"));

    }

    /**
     * List car - layout its columns and  and enable edit on columns
     * list data.
     */
    public void listCars() {
        vehicleTable.getColumns().clear();
        data = FXCollections.observableArrayList(CarEntry.car);
        vehicleTable.getColumns().addAll(make, year, seats, body, transmission, rent); // add columns to the table

        /*
            Double click to start edit - enter to confirm changes
            Setting up editing in columns and also applying that changed element to the specifit element of arrayList
         */
        make.setCellFactory(TextFieldTableCell.forTableColumn());
        make.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                ((FourWheel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setMake(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        year.setCellFactory(TextFieldTableCell.forTableColumn());
        year.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                try {
                    ((FourWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setYear(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }
            }
        }
        );

        rent.setCellFactory(TextFieldTableCell.forTableColumn());
        rent.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                try {
                    ((FourWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setBaseRent(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }

            }
        }
        );

        transmission.setCellFactory(TextFieldTableCell.forTableColumn());
        transmission.setOnEditCommit(
                new EventHandler<CellEditEvent<Car, String>>() {
            @Override
            public void handle(CellEditEvent<Car, String> t) {
                ((Car) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setTransmission(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        seats.setCellFactory(TextFieldTableCell.forTableColumn());
        seats.setOnEditCommit(
                new EventHandler<CellEditEvent<Car, String>>() {
            @Override
            public void handle(CellEditEvent<Car, String> t) {
                ((Car) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setNoOfSeats(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        body.setCellFactory(TextFieldTableCell.forTableColumn());
        body.setOnEditCommit(
                new EventHandler<CellEditEvent<Car, String>>() {
            @Override
            public void handle(CellEditEvent<Car, String> t) {
                ((Car) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setBody(t.getNewValue()); //changes the data field value in array
            }
        }
        );
    }

    /**
     * List Bike - layout its columns and  and enable edit on columns
     * list data.
     */
    public void listBikes() {
        vehicleTable.getColumns().clear();
        data = FXCollections.observableArrayList(BikeEntry.bike);
        vehicleTable.getColumns().addAll(make, year, model, body, engine, rent); // add bike columns to the table

        /*
            Double click to start edit - enter to confirm changes
            Setting up editing in columns and also applying that changed element to the specifit element of arrayList
         */
        model.setCellFactory(TextFieldTableCell.forTableColumn());
        model.setOnEditCommit(
                new EventHandler<CellEditEvent<Bike, String>>() {
            @Override
            public void handle(CellEditEvent<Bike, String> t) {
                ((Bike) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setModel(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        body.setCellFactory(TextFieldTableCell.forTableColumn());
        body.setOnEditCommit(
                new EventHandler<CellEditEvent<Bike, String>>() {
            @Override
            public void handle(CellEditEvent<Bike, String> t) {
                ((Bike) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setBody(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        engine.setCellFactory(TextFieldTableCell.forTableColumn());
        engine.setOnEditCommit(
                new EventHandler<CellEditEvent<Bike, String>>() {
            @Override
            public void handle(CellEditEvent<Bike, String> t) {
                ((Bike) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEngine(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        make.setCellFactory(TextFieldTableCell.forTableColumn());
        make.setOnEditCommit(
                new EventHandler<CellEditEvent<TwoWheel, String>>() {
            @Override
            public void handle(CellEditEvent<TwoWheel, String> t) {
                ((TwoWheel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setMake(t.getNewValue()); //changes the data field value in array
            }
        }
        );

        year.setCellFactory(TextFieldTableCell.forTableColumn());
        year.setOnEditCommit(
                new EventHandler<CellEditEvent<TwoWheel, String>>() {
            @Override
            public void handle(CellEditEvent<TwoWheel, String> t) {
                try {
                    ((TwoWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setYear(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }
            }
        }
        );

        rent.setCellFactory(TextFieldTableCell.forTableColumn());
        rent.setOnEditCommit(
                new EventHandler<CellEditEvent<TwoWheel, String>>() {
            @Override
            public void handle(CellEditEvent<TwoWheel, String> t) {
                try {
                    ((TwoWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setBaseRent(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }
            }
        }
        );
    }

    /**
     * List Truck - layout its columns and enable edit on columns
     * list data.
     */
    public void listTrucks() {
        vehicleTable.getColumns().clear();
        data = FXCollections.observableArrayList(TruckEntry.truck);
        vehicleTable.getColumns().addAll(make, year, capacity, rent); //add columns to the vehicleTale

        /*
            Double click to start edit - enter to confirm changes
            Setting up editing in columns and also applying that changed element to the specifit element of arrayList
         */
        make.setCellFactory(TextFieldTableCell.forTableColumn());
        make.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                ((FourWheel) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setMake(t.getNewValue());
            }
        }
        );

        year.setCellFactory(TextFieldTableCell.forTableColumn());
        year.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                try {
                    ((FourWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setYear(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }
            }
        }
        );

        rent.setCellFactory(TextFieldTableCell.forTableColumn());
        rent.setOnEditCommit(
                new EventHandler<CellEditEvent<FourWheel, String>>() {
            @Override
            public void handle(CellEditEvent<FourWheel, String> t) {
                try {
                    ((FourWheel) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setBaseRent(t.getNewValue()); //changes the data field value in array
                } catch (IllegalArgumentException ex) {
                    String alertTitle = "Error";
                    String alertHeader = "Invalid entry";
                    String alertContent = ex.getMessage();
                    VehicleAlert alert = new VehicleAlert(Alert.AlertType.ERROR, alertTitle, alertContent, alertHeader);
                }
            }
        }
        );

        capacity.setCellFactory(TextFieldTableCell.forTableColumn());
        capacity.setOnEditCommit(
                new EventHandler<CellEditEvent<Truck, String>>() {
            @Override
            public void handle(CellEditEvent<Truck, String> t) {
                ((Truck) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLoadingCapacity(t.getNewValue()); //changes the data field value in array
            }
        }
        );
    }

    /**
     * writeFile() to write the data to a file
     *
     * @param vehicle accepts arrayList of a vehicle to add data of arraylist to
     * a file line by line
     */
    private void writeFile(ArrayList vehicle) {
        if (!data.isEmpty()) {
            fileSaved = true; // change the fileSaved to true
            FileChooser chooser = new FileChooser(); // open file chooser

            File fileSet = new File("./src");
            chooser.setInitialDirectory(fileSet);

            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("CSV files", "*.csv")); // add extention filters to chooser window

            //check the append checkbox to appent data to an existing file
            if (append.isSelected()) {
                file = chooser.showOpenDialog(this);
            } else {
                file = chooser.showSaveDialog(this);
            }
            if (file == null) {
                System.out.println("Cancelled");//User clicks Cancel
            } else {
                //try with resources the file writer ad printwriter to close them as soon s writing is finished
                //appends data to a file
                try (FileWriter fileWriter = new FileWriter(file, true); PrintWriter writer = new PrintWriter(fileWriter);) {
                    for (int i = 0; i < vehicle.size(); i++) {
                        writer.println(vehicle.get(i).toString()); // print every record from the table
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.toString());
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        } else {
            String alertTitle = "Warning";
            String alertHeader = "Empty Tables";
            String alertContent = "Add data to table from add vehicle button";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.WARNING, alertTitle, alertContent, alertHeader);
        }
    }

    /**
     * Setting the layout of the root pane and adding nodes to accomplish task
     * an adding observable list to table view. Sets the graphics of the pane by
     * calling the setGraphics()
     */
    private void setLayout() {

        HBox hbox1 = new HBox(10);
        HBox hbox2 = new HBox(15);

        //main heading of the root
        label = new Label("View/Update Vehicles");
        label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));

        //make table view editable
        vehicleTable.setEditable(true);

        //set observable list to the table
        vehicleTable.setItems(data);

        //get selected index when any element of the table view is clicked
        vehicleTable.setOnMouseClicked(event -> {
            if (!data.isEmpty()) {
                try {
                    String selectedItem = vehicleTable.getSelectionModel().getSelectedItem().toString();
                    selectedIndex = vehicleTable.getSelectionModel().getSelectedIndex();
                } catch (Exception ex) {
                }
            }
        });

        //delete button to delete record
        delButton = new Button("Delete");
        delButton.setOnAction(event -> {
            removeSelectedVehicle();
        });

        clearButton = new Button("Clear View");
        clearButton.setOnAction(e -> {
            //ask for confirmation using confirn alert
            String alertTitle = "Confirmation";
            String alertHeader = "Delete all records";
            String alertContent = "Are you sure you want to clear all data";
            VehicleAlert alert = new VehicleAlert(Alert.AlertType.CONFIRMATION, alertTitle, alertContent, alertHeader);
            if (alert.getConfirmation() == true) {
                data.clear(); // clear list
                //clear arrayList
                switch (vehicleChosen.getText()) {
                    case "CAR":
                        CarEntry.car.clear();
                        break;
                    case "BIKE":
                        BikeEntry.bike.clear();
                        break;
                    case "TRUCK":
                        TruckEntry.truck.clear();
                        break;
                }
            }
        });
        //add button to hbox
        hbox2.getChildren().addAll(delButton, clearButton);
        hbox2.setAlignment(Pos.CENTER);

        //closes the stage
        back = new Button("OK");
        back.setOnAction(event -> this.close());

        //invokes the writeFile() to save data
        saveButton = new Button("Save Changes");
        saveButton.setOnAction(event -> {
            switch (vehicleChosen.getText()) {
                case "CAR":
                    writeFile(CarEntry.car);
                    break;
                case "BIKE":
                    writeFile(BikeEntry.bike);
                    break;
                case "TRUCK":
                    writeFile(TruckEntry.truck);
                    break;
            }
        });

        // changing textbox text as per the selection
        append.setOnAction(event -> {
            if (saveButton.getText().equals("Save Changes")) {
                saveButton.setText("Add to Existing File");
            } else {
                saveButton.setText("Save Changes");
            }
        });

        hbox1.getChildren().addAll(append, saveButton);
        hbox1.setAlignment(Pos.CENTER);

        //customize the buttons size
        saveButton.setPrefSize(350, 60);
        delButton.setPrefSize(225, 60);
        clearButton.setPrefSize(225, 60);

        // resize all columns accordint to the specified length - giveas an extra column 
        vehicleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // add nodes to the root pane
        root.getChildren().addAll(label, vehicleTable, hbox2, hbox1, back);
        root.setSpacing(25); // setSpacing between elements
        root.setPadding(new Insets(20));

        //change the stage size as per the scene
        super.sizeToScene();
        setGraphics();// set graphics of the pane

        Scene scene = new Scene(root); // add root to the scene
        setScene(scene); // add the scene to the stage
        show(); // show the stage
    }

    /**
     * Add colors and actions to the buttons
     */
    private void setGraphics() {

        //customize text font size
        saveButton.setFont(Font.font(25));

        delButton.setFont(Font.font(25));

        clearButton.setFont(Font.font(25));

        back.setFont(Font.font(18));

        //set root background color
        root.setStyle("-fx-background-color: #08B7B7");

        //set nodes colors
        label.setTextFill(Color.rgb(93, 89, 89));
        saveButton.setStyle("-fx-background-color: white");
        clearButton.setStyle("-fx-background-color: white");
        delButton.setStyle("-fx-background-color: white");
        back.setStyle("-fx-background-color: white");

        // add a mouse enter and exit property to buttons to add styles
        delButton.setOnMouseEntered(e -> {
            delButton.setStyle("-fx-background-color: #0008FF;");
            delButton.setTextFill(Color.WHITE);
        });
        delButton.setOnMouseExited(e -> {
            delButton.setStyle("-fx-background-color: white");
            delButton.setTextFill(Color.BLACK);
        });

        saveButton.setOnMouseEntered(e -> {
            saveButton.setStyle("-fx-background-color: #0008FF;");
            saveButton.setTextFill(Color.WHITE);
        });
        saveButton.setOnMouseExited(e -> {
            saveButton.setStyle("-fx-background-color: white");
            saveButton.setTextFill(Color.BLACK);
        });

        clearButton.setOnMouseEntered(e -> {
            clearButton.setStyle("-fx-background-color: #0008FF;");
            clearButton.setTextFill(Color.WHITE);
        });
        clearButton.setOnMouseExited(e -> {
            clearButton.setStyle("-fx-background-color: white");
            clearButton.setTextFill(Color.BLACK);
        });
    }
}
