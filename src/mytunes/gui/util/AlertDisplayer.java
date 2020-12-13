package mytunes.gui.util;

import javafx.scene.control.Alert;
import javafx.scene.layout.TilePane;

/**
 * class contains methods used to display alerts
 * @author kuba
 */
public class AlertDisplayer {

    /**
     * Alert suggests that the program is missing some data
     * data needs to be inserted by the user
     */
    public void displayInformationAlert(String title, String information, String header)
    {
        TilePane tilePane = new TilePane();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(information);
        alert.show();
    }

}
