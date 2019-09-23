package rishabh_bajaj_project;

import javafx.scene.text.Font;

/**
 * Gets appName and font to apply changes to the login page
 * @author rishabhbajaj
 */
class AppInfo {

    // declaring data fields
    private final String appName;
    private final Font font;

    /**
     * 
     * @param appName
     * @param font 
     */
    public AppInfo(String appName, Font font) {
        this.appName = appName;
        this.font = font;
    }

    public String getAppName() {
        return appName;
    }

    public Font getFont() {
        return font;
    }
}
