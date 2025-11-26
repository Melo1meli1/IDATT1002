package no.idatt1002.controller.Budget;

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
import java.util.List;

/**
 * Controller class for BudgetIncome.fxml
 * Used to display a single budget element in the budget view
 */
public class BudgetIncomeController {
    @FXML
    private TextField category;
    @FXML
    private TextField sum;
    @FXML
    private Label error;
    @FXML
    private ComboBox<String> priorityChoice = new ComboBox<>();
    private AppState appState;

    public BudgetIncomeController() {
        appState = AppState.getInstance();
    }

    /**
     * Adds income to the budget.
     * 
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void addIncome(ActionEvent event) throws IOException {
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
            // Hvis kategorien allerede eksisterer, legg inn feilhåndtering
            BudgetService budgetService = new BudgetService();
            List<Budget> budgetlist = appState.getCache().getCachedData("budget");
            int budgetId = budgetlist.size();

            String budgetCategory = category.getText();
            int budgetSum = Integer.parseInt(sum.getText());
            int userID = appState.getCache().getUser("user").getId();
            boolean budgetIe = false;
            int budgetPriority = 0;

            Budget budget = new Budget(budgetId, budgetCategory, budgetSum, userID, budgetIe, budgetPriority);
            // Budget budget = new Budget(budgetId, budgetCategory, budgetSum, userID,
            // budgetIe, 0);

            budgetService.addBudget(budget);

            List<Budget> cachedBudgetList = appState.getCache().getCachedData("budget");
            cachedBudgetList.add(budget);
            appState.getCache().setCachedData("budget", cachedBudgetList);
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
