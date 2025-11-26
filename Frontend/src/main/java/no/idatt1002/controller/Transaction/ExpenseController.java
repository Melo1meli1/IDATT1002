package no.idatt1002.controller.Transaction;

import no.idatt1002.Model.Expense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Controller that handeles expences in Transaction.fxml
 */
public class ExpenseController {

  public Button editButton;
  @FXML
  private HBox box;

  @FXML
  private Label sum;

  @FXML
  private Label category;

  @FXML
  private Label date;

  @FXML
  private Label name;

  public Expense expense;

  /**
   * Method that resieves information about what expence that is to be edited.
   * @param expense expence
   */
  public void setData(Expense expense) {
    name.setText(expense.getName());
    category.setText(expense.getCategory());
    sum.setText(expense.getSum() + " Kr");
    date.setText(expense.getDate());
    this.expense = expense;

  }

  /**
   * Edits chosen expence when set on action
   * @param event An ActionEvent object that represents the user's action that
   *              triggered the method
   *              call. It is typically used to get information about the event,
   *              such as the source of the event
   *              and any additional data associated with it.
   */
  public void edit(ActionEvent event) {
    try {

      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EditTransaction"
          + ".fxml"));
      Parent root = fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      EditTransactionController controller = fxmlLoader.getController();
      controller.setExpenseData(expense);

      stage.showAndWait();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Changes the color of the addButton element when the mouse hovers over it.
   * 
   * @param event the event of the mouse entering the node.
   */
  @FXML
  public void buttonHover(MouseEvent event) {
    editButton.setStyle("-fx-background-color: #ca0728; -fx-background-radius: 10;"
        + "-fx-text-fill: WHITE; -fx-font-size: 1.5em;-fx-font-weight: 500; -fx-font-family: Verdana");
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
    editButton.setStyle("-fx-background-color: #dc0428; -fx-background-radius: 10;"
        + "-fx-text-fill: WHITE; -fx-font-size: 1.5em;-fx-font-weight: 500; -fx-font-family: Verdana");
  }
}
