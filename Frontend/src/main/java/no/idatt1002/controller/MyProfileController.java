package no.idatt1002.controller;

import java.io.IOException;

import no.idatt1002.AppState;
import no.idatt1002.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for myProfile.fxml
 */
public class MyProfileController extends NavBarController {
  @FXML
  private Label name;

  @FXML
  private Label email;

  @FXML
  private ChoiceBox<String> language;

  private AppState appState;

  @FXML
  private Button logoutButton;

  ObservableList<String> languageList = FXCollections.observableArrayList("Norsk/Norwegian");

  /**
   * Displays name and Email of the user that is logged in.
   */
  @FXML
  public void setData() {
    appState = AppState.getInstance();
    User user = appState.getCache().getUser("user");

    name.setText(user.getName());
    email.setText(user.getEmail());

    language.setValue("Norsk/Norwegian");
    language.setItems(languageList);
  }

  /**
   * Method that logs the user out of their account
   * @param event An ActionEvent object that represents the user's action that
   *              triggered the method
   *              call. It is typically used to get information about the event,
   *              such as the source of the event
   *              and any additional data associated with it.
   */
  @FXML
  public void logout(ActionEvent event) throws IOException {
    appState.getCache().clearCache();

    // Load the login scene and apply the CSS
    Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
    Scene loginScene = new Scene(root);
    loginScene.getStylesheets().add(getClass().getResource("/styles/default.css").toExternalForm());

    // Switch to the login scene in the main window
    mainWindow.setScene(loginScene);

    // Close the MyProfile window
    Stage myProfileWindow = (Stage) logoutButton.getScene().getWindow();
    myProfileWindow.close();
  }
}
