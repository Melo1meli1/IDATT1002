package no.idatt1002.controller.Budget;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import no.idatt1002.AppState;
import no.idatt1002.Model.Budget;
import no.idatt1002.service.BudgetService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for BudgetExpence.fxml
 * Used to display a single budget element in the budget view
 */
public class BudgetExpenseController implements Initializable {

    @FXML
    private TextField category;
    @FXML
    private TextField sum;
    @FXML
    private Label error;
    @FXML
    private ComboBox<String> priorityChoice = new ComboBox<>();

    // FXCollections.observableArrayList(priorityChoices)
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
        ObservableList<String> choices = FXCollections.observableArrayList("Nødvendig", "Ok", "Unødvendig", "Sparing");
        priorityChoice.setItems(choices);
    }

    private BudgetService bService;

    private AppState appState;

    /**
     * Method for determening appstate for cashing.
     */
    public BudgetExpenseController() {
        appState = AppState.getInstance();
        bService = new BudgetService();
    }

    /**
     * Method for adding an expence to a budget
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              * triggered the method
     *              * call. It is typically used to get information about the event,
     *              * such as the source of the event
     *              * and any additional data associated with it.
     */
    public void addExpense(ActionEvent event) throws IOException {

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
            if (priorityChoice.getSelectionModel().getSelectedItem() == null) {
                throw new IllegalArgumentException("Pritoritering må fylles ut");
            }

            List<Budget> budgetlist = appState.getCache().getCachedData("budget");
            int budgetId = budgetlist.size();

            String budgetCategory = category.getText();
            int budgetSum = Integer.parseInt(sum.getText());
            int userID = appState.getCache().getUser("user").getId();
            boolean budgetIe = true;
            int i = priorityChoice.getSelectionModel().getSelectedIndex();

            Budget budget = new Budget(budgetId, budgetCategory, budgetSum, userID, budgetIe, i);

            bService.addBudget(budget);

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
     * Methods closes stage when event is set on action
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
