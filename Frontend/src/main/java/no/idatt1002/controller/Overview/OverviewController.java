package no.idatt1002.controller.Overview;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import no.idatt1002.DoughnutChart;
import no.idatt1002.service.BudgetService;
import no.idatt1002.service.ExpenseService;
import no.idatt1002.service.IncomeService;
import no.idatt1002.service.config.MonthYearRequest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import no.idatt1002.Model.Budget;
import no.idatt1002.Model.Expense;
import no.idatt1002.Model.Priority;
import no.idatt1002.Model.PriorityCategory;
import no.idatt1002.Model.PrioritySum;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.swing.text.TableView;

/**
 * Controller class for overview.fxml
 */
public class OverviewController implements Initializable {
  @FXML
  private DoughnutChart kontoversikt;

  @FXML
  private DoughnutChart budsjett;

  @FXML
  private DoughnutChart forbruk1;

  @FXML
  private DoughnutChart forbruk2;

  @FXML
  private javafx.scene.control.TableView<Table> ForbrukTabell;

  @FXML
  private TableColumn<Table, String> Kategori;

  @FXML
  private TableColumn<Table, Double> Forbruk;

  @FXML
  private TableColumn<Table, Double> Budsjett;

  @FXML
  private TableColumn<Table, Double> Margin;

  @FXML
  private TableView busjettTabell;

  @FXML
  private LineChart<String, Number> månedsoversikt;

  @FXML
  private ChoiceBox<String> MainChoiceBoxYear;

  @FXML
  private ChoiceBox<String> MainChoiceBoxMonth;
  @FXML
  private ChoiceBox<String> FraChoiceBox;
  @FXML
  private ChoiceBox<String> TilChoiceBox;

  @FXML
  private Label kont1;
  @FXML
  private Label kont2;
  @FXML
  private Label kont3;
  @FXML
  private Label kont4;

  private IncomeService iService;
  private BudgetService bService;
  private ExpenseService eservice;
  private MonthYearRequest monthYearRequest;

  // ! Months
  private ObservableList<String> months = FXCollections.observableArrayList("Velg måned", "Januar", "Februar", "Mars",
      "April", "Mai", "Juni", "Juli",
      "August", "Septermber", "Oktober", "November", "Desember");

  private ObservableList<String> years = FXCollections.observableArrayList("Velg år", "2018", "2019", "2020", "2021",
      "2022", "2023"); // en løsning kan være at for hver gang det er et nytt år, legges dette til i

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
    iService = new IncomeService();
    bService = new BudgetService();
    eservice = new ExpenseService();
    int month = LocalDateTime.now().getMonthValue();
    int year = LocalDateTime.now().getYear();
    monthYearRequest = new MonthYearRequest(month, year);

    MainChoiceBoxYear.setItems(years);
    MainChoiceBoxMonth.setItems(months);

    MainChoiceBoxYear.setValue(String.valueOf(year));
    MainChoiceBoxMonth.setValue(months.get(month));

    MainChoiceBoxYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      updateData();
    });

    MainChoiceBoxMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      updateData();
    });

    try {
      updateData();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates data on all diagrams and tables.
   */
  private void updateData() {

    int selectedYear = Integer.parseInt(MainChoiceBoxYear.getValue().toString());
    int selectedMonth = months.indexOf(MainChoiceBoxMonth.getValue().toString());

    if (selectedYear > 0 && selectedMonth > 0) {
      monthYearRequest.setMonth(selectedMonth);
      monthYearRequest.setYear(selectedYear);

      initKontoversiktData();
      initBudgetData();
      initExpenceDataOne();
      initExpenceDataTwo();

      initExpenceTableOne();
      initLineChart();
    }
  }

  // ! KONTOVERSIKT
  private ObservableList<PieChart.Data> kontoversiktData = FXCollections.observableArrayList(
      new PieChart.Data("Brukskonto", 144),
      new PieChart.Data("Sparekonto", 6700),
      new PieChart.Data("BSU", 40000));

  /**
   * Initialises "kontooversikt" donutChart.
   */
  private void initKontoversiktData() {
    kontoversikt.setData(kontoversiktData);
    kontoversikt.setSize(260);
    kontoversikt.setMinSize(260, 260);
    kontoversikt.setMaxSize(260, 260);
    kontoversikt.setPrefSize(260, 260);
    kontoversikt.setVisible(true);
    kontoversikt.showTotal(true);
  }

  // ! BUDGET
  /**
   * Initialises "budget" donutChart.
   */
  private void initBudgetData() {
    budsjett.setData(getBudgetData());
    budsjett.setSize(260);
    budsjett.setMinSize(260, 260);
    budsjett.setMaxSize(260, 260);
    budsjett.setPrefSize(260, 260);
    budsjett.setVisible(true);
    budsjett.showTotal(true);
  }

  /**
   * Method that imports data regarding budget from database
   * 
   * @return budget data
   */
  private ObservableList<PieChart.Data> getBudgetData() {
    List<Priority> budget = bService.getPriority();

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    for (Priority p : budget) {
      pieChartData.add(new PieChart.Data(p.getPriority(), p.getSum()));
    }
    return pieChartData;
  }

  // ! INCOME
  private void initExpenceDataOne() {
    // en metode som sletter all displayed informasjon, og legger inn det
    // oppdaterte.
    forbruk1.setData(GetIncomeDataOne());
    forbruk1.setSize(260);
    forbruk1.setMinSize(260, 260);
    forbruk1.setMaxSize(260, 260);
    forbruk1.setPrefSize(260, 260);
    forbruk1.setVisible(true);
    forbruk1.showTotal(true);
  }

  private ObservableList<PieChart.Data> GetIncomeDataOne() {
    List<Expense> expenses = eservice.getExpenceMonthYearFromCache(monthYearRequest);

    expenses.sort((expense1, expense2) -> Double.compare(expense2.getSum(), expense1.getSum()));
    ObservableList<PieChart.Data> IncomeData = FXCollections.observableArrayList();

    // Get the top 5 elements with the highest sum
    int elementsToAdd = Math.min(5, expenses.size());

    for (int i = 0; i < elementsToAdd; i++) {
      IncomeData.add(new PieChart.Data(expenses.get(i).getCategory(), expenses.get(i).getSum()));
    }

    if (expenses.size() >= 1) {
      kont1.setText(expenses.get(0).getCategory());
    }
    if (expenses.size() >= 2) {
      kont2.setText(expenses.get(1).getCategory());
    }
    if (expenses.size() >= 3) {
      kont3.setText(expenses.get(2).getCategory());
    }
    if (expenses.size() >= 4) {
      kont4.setText(expenses.get(3).getCategory());
    }

    return IncomeData;
  }

  // ! EXPENCE
  /**
   * Initialises "expence" donutChart.
   */
  private void initExpenceTableOne() {
    Kategori.setCellValueFactory(new PropertyValueFactory<Table, String>("kategori"));
    Forbruk.setCellValueFactory(new PropertyValueFactory<Table, Double>("forbruk"));
    Budsjett.setCellValueFactory(new PropertyValueFactory<Table, Double>("budsjett"));
    Margin.setCellValueFactory(new PropertyValueFactory<Table, Double>("margin"));

    ForbrukTabell.setItems(IncomeTableDataOne());
  }

  /**
   * Method that imports data regarding expence from database
   * 
   * @return expence data
   */
  private ObservableList<Table> IncomeTableDataOne() {

    List<Budget> budgetList = bService.getBudgetDataFromCache();
    List<Expense> expenseList = eservice.expenseList();

    ObservableList<Table> as = FXCollections.observableArrayList();

    for (int i = 0; i < budgetList.size(); i++) {
      // int lastUpdatedRow = -1;
      Budget budget = budgetList.get(i);
      double sum = 0;
      for (Expense expense : expenseList) {
        if (budget.getCategory().equals(expense.getCategory())) {
          sum += expense.getSum();
        }
      }
      Double dSum = (double) budget.getSum();

      double margin = budget.getSum() - sum;

      Text text = new Text(margin + "");
      if (margin < 0) {
        text.setId("negative-margin");

      } else {
        text.setId("positive-margin");
      }
      Table table = new Table(budget.getCategory(), sum, dSum, text);
      as.add(table);
    }
    return as;
  }

  /**
   * Initialises "expence" donutChart.
   */
  private void initExpenceDataTwo() {
    forbruk2.setData(ExpenceDataTwo());
    forbruk2.setSize(260);
    forbruk2.setMinSize(260, 260);
    forbruk2.setMaxSize(260, 260);
    forbruk2.setPrefSize(260, 260);
    forbruk2.setVisible(true);
    forbruk2.showTotal(true);
  }

  /**
   * Method that imports data regarding expence from database
   * 
   * @return expence data
   */
  private ObservableList<PieChart.Data> ExpenceDataTwo() {
    List<Expense> expenseList = eservice.getExpenceMonthYearFromCache(monthYearRequest);
    List<PriorityCategory> priorityList = bService.getPriorityCategoryFromCache();

    ObservableList<PieChart.Data> forbruk2Data = FXCollections.observableArrayList();

    PrioritySum p1 = new PrioritySum("Necessary", 0);
    PrioritySum p2 = new PrioritySum("Ok", 0);
    PrioritySum p3 = new PrioritySum("Unnecessary", 0);
    PrioritySum p4 = new PrioritySum("Saving", 0);

    for (PriorityCategory p : priorityList) {
      for (Expense e : expenseList) {
        if (p.getCategory().equals(e.getCategory())) {
          if (p.getPriority() == "Necessary") {
            p1.setSum(p1.getSum() + e.getSum());
          }
          if (p.getPriority() == "Ok") {
            p2.setSum(p2.getSum() + e.getSum());
          }
          if (p.getPriority() == "Unnecessary") {
            p3.setSum(p3.getSum() + e.getSum());
          }
          if (p.getPriority() == "Saving") {
            p4.setSum(p4.getSum() + e.getSum());
          }
        }
      }
    }

    forbruk2Data.add(new PieChart.Data(p1.getPriority(), p1.getSum()));
    forbruk2Data.add(new PieChart.Data(p2.getPriority(), p2.getSum()));
    forbruk2Data.add(new PieChart.Data(p3.getPriority(), p3.getSum()));
    forbruk2Data.add(new PieChart.Data(p4.getPriority(), p4.getSum()));

    return forbruk2Data;

  }

  // ! Måned LineChart
  /**
   *
   * Initialises "måned" lineChart.
   */
  private void initLineChart() {
    månedsoversikt.getData().clear(); // Clear existing data from the linechart

    List<Expense> expenses = eservice.getExpenceMonthYearFromCache(monthYearRequest);

    int month = monthYearRequest.getMonth();
    int year = monthYearRequest.getYear();

    XYChart.Series<String, Number> monthSeries = new XYChart.Series<String, Number>();
    monthSeries.setName(String.format("%02d", month) + "/" + year);

    Map<Integer, Double> dailyExpenses = new HashMap<>();

    for (Expense expense : expenses) {
      int day = expense.getDay();
      double amount = expense.getSum();
      if (dailyExpenses.containsKey(day)) {
        dailyExpenses.put(day, dailyExpenses.get(day) + amount);
      } else {
        dailyExpenses.put(day, amount);
      }
    }

    int daysInMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

    for (int day = 1; day <= daysInMonth; day++) {
      if (dailyExpenses.containsKey(day)) {
        String dayStr = String.format("%02d", day);
        monthSeries.getData().add(new XYChart.Data<String, Number>(dayStr, dailyExpenses.get(day)));
      }
    }

    månedsoversikt.setStyle("-fx-background-color: transparent;");

    månedsoversikt.getData().add(monthSeries);
    månedsoversikt.setLegendVisible(false);
    månedsoversikt.setHorizontalGridLinesVisible(false);

  }

}