package no.idatt1002.controller.Budget;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import no.idatt1002.Model.Budget;
import no.idatt1002.service.BudgetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for BudgetEdit.fxml
 */
public class BudgetEditController implements Initializable {
    @FXML
    TextField category;
    @FXML
    TextField sum;
    @FXML
    Label title;
    @FXML
    private ComboBox<String> priorityChoice = new ComboBox<>();
    @FXML
    private Label error;
    @FXML
    private Label priorityLabel;

    private Budget budget;

    private BudgetService bservice;

    /**
     * Constructor for BudgetEditController
     * Initializes the AppState and BudgetService
     */
    public BudgetEditController() {
        bservice = new BudgetService();
    }

    /**
     * This function initializes a dropdown menu with four choices in a JavaFX
     * application.
     * 
     * @param url            The URL of the FXML file that contains the UI
     *                       components for the JavaFX application.
     * @param resourceBundle The `resourceBundle` parameter in the `initialize`
     *                       method is a bundle of
     *                       resources that can be used to localize an application.
     *                       It contains a set of key-value pairs
     *                       where the keys are strings that identify the resources
     *                       and the values are the localized strings.
     *                       In this case, the `resourceBundle`
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> choices = FXCollections.observableArrayList("Nødvendig", "Ok", "Unødvendig", "Sparing");
        priorityChoice.setItems(choices);
    }

    /**
     * The function sets the data of a Budget object and updates the UI accordingly.
     * 
     * @param budget The budget Object
     */
    public void setData(Budget budget) {
        category.setText(budget.getCategory());
        sum.setText(Integer.toString(budget.getSum()));

        if (budget.getIe()) {
            title.setText("Rediger utgift");
        } else {
            title.setText("Rediger inntekt");
        }
        if (budget.getIe()) {
            priorityChoice.setVisible(true);
            priorityLabel.setVisible(true);
        }

        this.budget = budget;
    }

    /**
     * The function edits a budget element and checks for input validation before
     * updating the
     * database.
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void editElement(ActionEvent event) {
        try {
            if (category.getText().isBlank()) {
                throw new IllegalArgumentException("Kategori må fylles ut");
            }
            if (sum.getText().isBlank()) {
                throw new IllegalArgumentException("Sum må fylles ut");
            }
            if (sum.getText().contains("/^[a-z0-9._]+$/i")) {
                throw new IllegalArgumentException("Sum kan bare inneholde tall");
            }
            if (priorityChoice.getSelectionModel().getSelectedItem() == null && budget.getIe()) {
                throw new IllegalArgumentException("Pritoritering må fylles ut");
            }

            int priority = priorityChoice.getSelectionModel().getSelectedIndex();
            Budget newBudget = new Budget(budget.getId(), category.getText(), Integer.parseInt(sum.getText()),
                    budget.getUser_id(), budget.getIe(), priority);

            int i = 0;
            if (budget.getIe()) {
                i = priorityChoice.getSelectionModel().getSelectedIndex();
            }

            updateBudget(newBudget);
            closeStage(event);
        } catch (IllegalArgumentException i) {
            error.setText(i.getMessage());
            error.setVisible(true);
        } catch (Exception e) {
            error.setText(e.getMessage());
            error.setVisible(true);
            e.printStackTrace();
        }

    }

    /**
     * This function updates a budget using a budget service.
     * 
     * @param budget The budget Object
     */

    private void updateBudget(Budget budget) {
        bservice.updateBudget(budget);

    }

    /**
     * This function closes the current stage/window in a Java application.
     * 
     * @param event The event parameter is an object of the ActionEvent class, which
     *              closes the window when the user edits a expense or closes the
     *              window
     */
    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
