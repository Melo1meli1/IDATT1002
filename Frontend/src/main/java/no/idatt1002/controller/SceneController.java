package no.idatt1002.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
 * Controller that controlls the different scenes
 */
public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Switches scene to transaction.fxml
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It could be a button click, menu item selection, or any
     *              other user interaction that is
     *              associated with an action.
     */
    public void switchToTransaction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/FXML/Transactions.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
    /**
     * Switches scene to overview.fxml
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It could be a button click, menu item selection, or any
     *              other user interaction that is
     *              associated with an action.
     */
    public void switchToOverview(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/FXML/Overview.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
