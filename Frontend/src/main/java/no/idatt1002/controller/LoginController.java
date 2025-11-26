package no.idatt1002.controller;

import no.idatt1002.service.LoginService;
import no.idatt1002.service.config.LoginRequest;
import no.idatt1002.service.config.RegisterRequest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * 
 * 
 * Controller that handles interface in login.fxml
 *
 */
public class LoginController {
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;
    @FXML
    private TextField regname;
    @FXML
    private TextField regage;
    @FXML
    private TextField regaddress;
    @FXML
    private TextField regtlf;
    @FXML
    private TextField regemail;
    @FXML
    private PasswordField regpassword;
    @FXML
    private Pane loginPane;
    @FXML
    private Pane registerPane;

    private LoginService service;

    public LoginController() {
        service = new LoginService();
    }

    /**
     * Interface pane that registeres a new user
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     * @throws IOException
     */
    public void registerScreen(ActionEvent event) throws IOException {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    /**
     * Interface pane that allows already existing user to login
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     * @throws IOException
     */
    public void LoginScreen(ActionEvent event) throws IOException {
        loginPane.setVisible(true);
        registerPane.setVisible(false);
    }

    /**
     * Method that registeres new users
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void registerUser(ActionEvent event) throws IOException {
        // LoginRequest user = new LoginRequest(email.getText(), password.getText());
        int intAage = Integer.parseInt(regage.getText());

        RegisterRequest user = new RegisterRequest(intAage, regname.getText(), regemail.getText(),
                regpassword.getText(), regtlf.getText(), regaddress.getText());

        if (service.register(user) == false) {
            error.visibleProperty().set(true);
            return;
        }
        SceneController con = new SceneController();
        con.switchToTransaction(event);
    }

    /**
     * Method that allows already existing user to log in to their account
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void login(ActionEvent event) throws IOException {
        LoginRequest user = new LoginRequest(email.getText(), password.getText());

        if (service.login(user) == false) {
            error.visibleProperty().set(true);
            return;
        }
        SceneController con = new SceneController();
        con.switchToOverview(event);
    }
}
