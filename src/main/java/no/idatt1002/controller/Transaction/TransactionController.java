package no.idatt1002.controller.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import no.idatt1002.Model.Income;
import no.idatt1002.Model.Expense;
import no.idatt1002.service.ExpenseService;
import no.idatt1002.service.IncomeService;
import javafx.stage.Stage;

/**
 * Controller that has the responsibility of handling all dynamic components in the Transaction.fxml
 */
public class TransactionController implements Initializable {

    private IncomeService iService;
    private ExpenseService eService;

    @FXML
    private VBox expenseLayout;
    @FXML
    private VBox incomeLayout;
    @FXML
    private Button addButton;
    /**
     * Initializes the controller by setting up the BudgetService and creating the
     * charts and lists.
     *
     * @param location  location representing the location of the FXML file
     * @param resources resources representing the resources used by the FXML
     *                  file
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iService = new IncomeService();
        eService = new ExpenseService();
        try {
            initIncomeList();
            initExpenseList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * adds transaction
     * @param event An ActionEvent object that represents the user's action that
     *              triggered the method
     *              call. It is typically used to get information about the event,
     *              such as the source of the event
     *              and any additional data associated with it.
     */
    public void addTransaction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddIncome"
                    + ".fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            initExpenseList();
            initIncomeList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes income
     */
    private void initIncomeList() throws IOException {
        incomeLayout.getChildren().clear();
        List<Income> incomeList = iService.incomeList();

        for (Income income : incomeList) {
            FXMLLoader incomeLoader = new FXMLLoader();
            incomeLoader.setLocation(getClass().getResource("/fxml/Income.fxml"));
            HBox IncomeBox = incomeLoader.load();
            IncomeController incomeController = incomeLoader.getController();

            incomeController.setData(income);
            incomeLayout.getChildren().add(IncomeBox);
        }
    }

    /**
     * Initializes expences
     */
    private void initExpenseList() throws IOException {
        expenseLayout.getChildren().clear();
        List<Expense> expenseList = eService.expenseList();

        for (Expense expense : expenseList) {
            FXMLLoader expenseLoader = new FXMLLoader();
            expenseLoader.setLocation(getClass().getResource("/fxml/Expense.fxml"));
            HBox ExpenseBox = expenseLoader.load();
            ExpenseController expenseController = expenseLoader.getController();

            expenseController.setData(expense);
            expenseLayout.getChildren().add(ExpenseBox);
        }
    }

    /**
     * Changes the color of the addButton element when the mouse hovers over it.
     * @param event the event of the mouse entering the node.
     */
    @FXML
    public void buttonHover(MouseEvent event) {
        addButton.setStyle("-fx-background-color: #ca0728; -fx-background-radius: 10;            "
                + "-fx-text-fill: #ffffff; -fx-font-size: 2em; -fx-font-weight: 500;-fx-font-family: Verdana");
    }

    /**
     * Changes the color of the addButton element back to its default state when the
     * mouse exits
     * the button
     * @param event the event of the mouse exiting the node.
     */
    @FXML
    public void buttonExited(MouseEvent event) {
        addButton.setStyle("-fx-background-color: #dc0428; -fx-background-radius: 10;            "
                + "-fx-text-fill: #ffffff; -fx-font-size: 2em; -fx-font-weight: 500;-fx-font-family: Verdana");
    }
}
