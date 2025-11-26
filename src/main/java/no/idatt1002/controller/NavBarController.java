package no.idatt1002.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Controller for navbar.fxml
 */
public class NavBarController implements Initializable {
    @FXML
    private ToggleButton overview;
    @FXML
    private ToggleButton transaction;
    @FXML
    private ToggleButton budget;
    @FXML
    private ToggleButton assistant;
    @FXML
    private ToggleGroup group;

    public Stage mainWindow;

    /**
     * Sets the display to the currently piced scene.
     * 
     * @param mainWindow window of choice (eg. Transaction)
     */
    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Initializes the controller by setting up the BudgetService and creating the
     * charts and lists.
     * 
     * @param url            URL representing the location of the FXML file
     * @param resourceBundle ResourceBundle representing the resources used by the
     *                       FXML
     *                       file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
         * overview.setToggleGroup(group);
         * transaction.setToggleGroup(group);
         * budget.setToggleGroup(group);
         * assistant.setToggleGroup(group);
         * 
         * overview.setSelected(overviewSelected);
         * transaction.setSelected(transactionSelected);
         * budget.setSelected(budgetSelected);
         * assistant.setSelected(assistantSelected);
         */
    }

    /**
     * Changes the scene to the transaction page
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void changeSceneToTransaction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Transactions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Changes the scene to the budget page
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void changeSceneToBudget(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Budget.fxml"));
        budget.setSelected(true);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        budget.setSelected(true);

    }

    /**
     * Changes the scene to the overview page
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void changeSceneToOverview(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Overview.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

    }

    /**
     * Changes the scene to the assistent page
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void changeSceneToAssistent(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Assistent.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Makes the myProfile wondow to display
     * 
     * @param event
     */
    public void addMyProfile(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MyProfile.fxml"));
            Parent root = fxmlLoader.load();
            MyProfileController myProfileController = fxmlLoader.getController();
            myProfileController.setData();
            myProfileController.setMainWindow((Stage) ((Node) event.getSource()).getScene().getWindow()); // set the
                                                                                                          // mainWindow
                                                                                                          // reference
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the window to login.fxml
     */
    public void switchToLoginScene() throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        stage = (Stage) root.getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
