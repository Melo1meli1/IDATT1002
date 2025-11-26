package no.idatt1002.controller.Transaction;

import no.idatt1002.Model.Expense;
import no.idatt1002.Model.Income;
import no.idatt1002.service.ExpenseService;
import no.idatt1002.service.IncomeService;

import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller that adds income in Transaction.fxml
 */
public class AddIncomeController {

  /**
   * FXML variables
   */
  public Button addButton;
  public Label error;
  @FXML
  private ChoiceBox<String> transactionType;
  @FXML
  private TextField name;

  @FXML
  private TextField sum;

  @FXML
  private ChoiceBox<String> category;

  @FXML
  private DatePicker date;

  @FXML
  private CheckBox recurring;

  private IncomeService iservice;
  private ExpenseService eservice;

  public AddIncomeController() {
    iservice = new IncomeService();
    eservice = new ExpenseService();
  }

  /**
   * Adds income when set on action
   * 
   * @param event An ActionEvent object that represents the user's action that
   *              triggered the method
   *              call. It is typically used to get information about the event,
   *              such as the source of the event
   *              and any additional data associated with it.
   **/
  public void addIncome(ActionEvent event) {
    try {
      if (sum.getText().isBlank()) {
        throw new IllegalArgumentException("Beløp må fylles ut");
      }
      if (!sum.getText().matches("\\d+")) {
        throw new IllegalArgumentException("Beløp kan bare inneholde tall");
      }
      if (category.getValue().equals("Kategori")) {
        throw new IllegalArgumentException("Kategori må velges");
      }
      if (date.getEditor().getText().isBlank()) {
        throw new IllegalArgumentException("Dato må velges");
      }
      if (transactionType.getValue().equals("Transaksjonstype")) {
        throw new IllegalArgumentException("Transaksjonstype må velges");
      }

      if (transactionType.getValue().equals("Inntekt")) {

        Income income = new Income();
        income.setName(name.getText());
        income.setSum(Integer.parseInt(sum.getText()));
        income.setCategory(category.getValue());
        income.setDate(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        income.setRecurring(recurring.isSelected());

        iservice.addIncome(income);
        closeStage(event);

      } else if (transactionType.getValue().equals("Utgift")) {

        Expense expense = new Expense();

        expense.setName(name.getText());
        expense.setSum(Integer.parseInt(sum.getText()));
        expense.setCategory(category.getValue());
        expense.setDate(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        expense.setRecurring(recurring.isSelected());

        eservice.addExpense(expense);
        closeStage(event);

      }
    } catch (IllegalArgumentException i) {
      error.setText(i.getMessage());
      error.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
      closeStage(event);
    }
  }

  /**
   * Changes the color of the addButton element when the mouse hovers over it.
   * 
   * @param event the event of the mouse entering the node.
   */
  @FXML
  public void buttonHover(MouseEvent event) {
    addButton.setStyle(
        "-fx-background-color: #ca0728; -fx-background-radius: 10; -fx-text-fill: WHITE; -fx-font-size: 1.5em;   -fx-font-weight: 500; -fx-font-family: Verdana");
  }

  /**
   * Changes the color of the addButton element back to its default state when the
   * mouse exits
   * the button
   * 
   * @param event the event of the mouse exiting the node.
   */
  @FXML
  public void buttonExited(MouseEvent event) {
    addButton.setStyle(
        "-fx-background-color: #dc0428; -fx-background-radius: 10; -fx-text-fill: WHITE; -fx-font-size: 1.5em;   -fx-font-weight: 500; -fx-font-family: Verdana");
  }

  /**
   * Closes the stage when set on action
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
