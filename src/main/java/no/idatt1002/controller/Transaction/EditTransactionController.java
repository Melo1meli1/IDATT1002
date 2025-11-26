package no.idatt1002.controller.Transaction;

import no.idatt1002.Model.Expense;
import no.idatt1002.Model.Income;
import no.idatt1002.service.ExpenseService;
import no.idatt1002.service.IncomeService;

import java.time.LocalDate;
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
 * Controller that edits transactions in Transaction.fxml
 */
public class EditTransactionController {

  public Button editButton;
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

  private int id;

  public EditTransactionController() {
    iservice = new IncomeService();
    eservice = new ExpenseService();
  }

  /**
   * Method that resieves information about what income that is to be edited.
   * @param income income
   */
  public void setIncomeData(Income income) {
    id = income.getId();
    name.setText(income.getName());
    sum.setText(String.valueOf(income.getSum()));
    category.setValue(income.getCategory());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    date.setValue(LocalDate.parse(income.getDate(), formatter));
    transactionType.setValue("Inntekt");
  }
  /**
   * Method that resieves information about what expence that is to be edited.
   * @param expense income
   */
  public void setExpenseData(Expense expense) {
    id = expense.getId();
    name.setText(expense.getName());
    sum.setText(String.valueOf(expense.getSum()));
    category.setValue(expense.getCategory());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    date.setValue(LocalDate.parse(expense.getDate(), formatter));
    transactionType.setValue("Utgift");

  }

  /**
   * Changes already existing transaction
   * @param event An ActionEvent object that represents the user's action that
   *              triggered the method
   *              call. It is typically used to get information about the event,
   *              such as the source of the event
   *              and any additional data associated with it.
   */
  public void changeTransaction(ActionEvent event) {
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
        income.setId(id);
        System.out.println("------------------" + id);
        income.setName(name.getText());
        income.setSum(Integer.parseInt(sum.getText()));
        income.setCategory(category.getValue());
        income.setDate(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        income.setRecurring(recurring.isSelected());

        iservice.updateIncome(income);

      } else if (transactionType.getValue().equals("Utgift")) {
        Expense expense = new Expense();
        expense.setId(id);
        expense.setName(name.getText());
        expense.setSum(Integer.parseInt(sum.getText()));
        expense.setCategory(category.getValue());
        expense.setDate(date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        expense.setRecurring(recurring.isSelected());

        eservice.updateExpence(expense);
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
    editButton.setStyle(
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
    editButton.setStyle(
        "-fx-background-color: #dc0428; -fx-background-radius: 10; -fx-text-fill: WHITE; -fx-font-size: 1.5em;   -fx-font-weight: 500; -fx-font-family: Verdana");
  }

  /**
   * Closes stage when set on action
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
