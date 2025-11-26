package no.idatt1002.controller.Budget;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import no.idatt1002.Model.Budget;
import no.idatt1002.DoughnutChart;
import no.idatt1002.service.BudgetService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Controller class for Budget.fxml
 */
public class BudgetController implements Initializable {

    private ObservableList<Budget> incomeList;
    private ObservableList<Budget> expenseList;
    @FXML
    private DoughnutChart incomeChart = new DoughnutChart();

    @FXML
    private DoughnutChart expenseChart = new DoughnutChart();

    @FXML
    private GridPane incomeGridPane;

    @FXML
    private GridPane expenseGridPane;
    private final String[] diagramColors = { "#9bc53d", "#ffc600", "#c3423f", "#a913e5", "#25ccc6", "#1567d3",
            "#fd6406", "#f109ed", "#a8bfc3", "#094eff", "#609098" };

    private ObservableList<PieChart.Data> incomeChartData = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();

    private BudgetService bservice;

    /**
     * Initializes the controller by setting up the BudgetService and creating the
     * charts and lists.
     * 
     * @param location  URL representing the location of the FXML file
     * @param resources ResourceBundle representing the resources used by the FXML
     *                  file
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader budgetIncomeLoader = new FXMLLoader();
        budgetIncomeLoader.setLocation(getClass().getResource("/fxml/Budget.fxml"));
        bservice = new BudgetService();

        try {
            updateLists();
            initIncomeChart();
            initIncomeList();
            initExpenseList();
            initExpenseChart();

            bservice.getBudgetDataFromCache()
                    .addListener((ListChangeListener<? super Budget>) new ListChangeListener<Budget>() {

                        @Override
                        public void onChanged(Change<? extends Budget> change) {
                            updateLists();
                            while (change.next()) {
                                for (Budget b : change.getAddedSubList()) {
                                    if (b.getIe()) {
                                        initExpenseList();
                                        updateExpenseChart();
                                    } else {
                                        initIncomeList();
                                        updateIncomeChart();
                                    }
                                }
                            }

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the income and expense lists from the BudgetService cache.
     */
    private void updateLists() {
        incomeList = bservice.getBudgetDataFromCache().stream().filter(i -> !i.getIe())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        expenseList = bservice.getBudgetDataFromCache().stream().filter(Budget::getIe)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * Initializes the income list in the UI
     */
    private void initIncomeList() {
        try {
            for (int i = 0; i < incomeList.size(); i++) {
                FXMLLoader incomeLoader = new FXMLLoader();
                incomeLoader.setLocation(getClass().getResource("/fxml/BudgetElement.fxml"));
                Button income = incomeLoader.load();
                BudgetElementController incomeController = incomeLoader.getController();
                incomeController.setColor(diagramColors[i % diagramColors.length]);
                incomeController.setData(incomeList.get(i));
                int rowPosition = i / 2;
                int columnPosition = i % 2;
                incomeGridPane.add(income, columnPosition, rowPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the expense list in the UI
     */
    private void initExpenseList() {
        try {
            for (int i = 0; i < expenseList.size(); i++) {
                FXMLLoader expenseLoader = new FXMLLoader();
                expenseLoader.setLocation(getClass().getResource("/fxml/BudgetElement.fxml"));
                Button expense = expenseLoader.load();
                BudgetElementController expenseController = expenseLoader.getController();
                expenseController.setColor(diagramColors[i % diagramColors.length]); // Use modulo operator

                expenseController.setData(expenseList.get(i));
                int rowPosition = i / 2;
                int columnPosition = i % 2;
                expenseGridPane.add(expense, columnPosition, rowPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the income chart data
     */
    private void initIncomeChart() {
        updateIncomeChart();
        incomeChart.showTotal(true);
        incomeChart.setData(incomeChartData);
        incomeChart.setSize(320);
    }

    /**
     * Initializes the expence chart data
     */
    private void initExpenseChart() {
        updateExpenseChart();
        expenseChart.showTotal(true);
        expenseChart.setData(expenseChartData);
        expenseChart.setSize(320);
    }

    /**
     * The method loads and displays a new window for adding budget income using an
     * FXML file.
     */
    @FXML
    private void addBudgetIncome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AddBudgetIncome.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method loads and displays a new window for adding a budget expense in a
     * JavaFX
     * application.
     * 
     * @param event The event parameter is an object of the ActionEvent class, which
     *              represents an
     *              event that occurred as a result of user interaction with a
     *              graphical user interface (GUI)
     *              component, such as a button click. In this case, the
     *              addBudgetExpense() method is triggered when
     *              the user clicks a button, and
     */
    @FXML
    private void addBudgetExpense(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AddBudgetExpense.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates the data in a pie chart representing income
     */
    private void updateIncomeChart() {
        incomeChartData.clear();
        for (Budget b : incomeList) {
            incomeChartData.add(new PieChart.Data(b.getCategory(), b.getSum()));
        }
    }

    /**
     * This method updates the data in a pie chart representing expenses
     */
    private void updateExpenseChart() {
        expenseChartData.clear();
        for (Budget b : expenseList) {
            expenseChartData.add(new PieChart.Data(b.getCategory(), b.getSum()));
        }
    }

}
