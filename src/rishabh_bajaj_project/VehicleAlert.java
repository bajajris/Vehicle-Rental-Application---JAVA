package rishabh_bajaj_project;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * VehicleAlert - custom alert to display as per the type set
 *
 * @author rishabhbajaj
 */
public class VehicleAlert extends Alert { // an alert class to give different alerts

    // get pressed button
    Optional<ButtonType> result;

    /**
     *
     * @param type
     * @param title
     * @param content
     * @param header
     */
    public VehicleAlert(AlertType type, String title, String content, String header) {
        super(type);
        // set alert title, head, content
        setTitle(title);
        setHeaderText(header);
        setContentText(content);
        result = this.showAndWait();
    }

    /**
     * check the button pressed to confirm a task
     *
     * @return
     */
    public boolean getConfirmation() {
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                return true;
            }
        }
        return false;
    }
}
