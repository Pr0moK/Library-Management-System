package com.example.test.Controller;

import com.example.test.NewSceneController;
import com.example.test.model.ChosenIssuedBook;
import com.example.test.model.ChoosenUser;
import com.example.test.model.LendedBooks;
import com.example.test.service.BookService;
import com.example.test.service.DatabaseService;
import com.example.test.service.DataLoaderService;
import com.example.test.service.IssuedBookFeeService;
import com.example.test.utils.OnBookReturnListener;
import com.example.test.utils.OnStatisticListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class BookReturnController implements OnBookReturnListener{

    @FXML
    private TextField Fee;

    @FXML
    private TextField Id;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookTitle;

    @FXML
    private TableView<LendedBooks> UsersIssuedBooks;

    @FXML
    private TableColumn<LendedBooks, String> colAuthor;

    @FXML
    private TableColumn<LendedBooks, Integer> colID;

    @FXML
    private TableColumn<LendedBooks, String> colDateOfLend;

    @FXML
    private TableColumn<LendedBooks, String> colDateOfReturn;

    @FXML
    private TableColumn<LendedBooks, Double> colFee;

    @FXML
    private TableColumn<LendedBooks, String> colLogin;

    @FXML
    private TableColumn<LendedBooks, String> colTitle;

    @FXML
    private TextField userLogin;

    @FXML
    private Label ReturnMessage;

    @FXML
    private TextField userName;

    @FXML
    private TextField userSurname;

    private OnStatisticListener StatisticListener;

    public void setOnStatisticListner(OnStatisticListener listener) {
        this.StatisticListener = listener;
    }

    @Override
    public void onBookReturned() {
        try {
            LoadLendedBooks();
            ReturnMessage.setText("Pomyślnie oddano książkę.");
            if(StatisticListener != null){
                StatisticListener.onStatisticReturn();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws IOException {

        colID.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        colDateOfLend.setCellValueFactory(new PropertyValueFactory<>("LendDate"));
        colDateOfReturn.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("UserLogin"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("Fee"));
        IssuedBookFeeService fee = new IssuedBookFeeService();
        fee.CheckFee();
        LoadLendedBooks();

    }
    private void LoadLendedBooks() throws IOException {
        DataLoaderService loader = new DataLoaderService();
        try {
            ObservableList<LendedBooks> Lendedinfo = loader.LoadLendedBooks();
            UsersIssuedBooks.setItems(Lendedinfo);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych wypożyczeń: " + e.getMessage());
        }

    }

    @FXML
    void ReturnBookButton(ActionEvent event) throws IOException {
        BookService book = new BookService();
        if(!Id.getText().isEmpty()) {
            if (book.ReturnBook(Integer.valueOf(Id.getText()))) {
                NewSceneController scene = new NewSceneController("AcceptBookReturnView.fxml", 713, 132, this);
            } else {
                LoadLendedBooks();
                ReturnMessage.setText("Pomyślnie zwrócno książkę.");
                if(StatisticListener != null){
                    StatisticListener.onStatisticReturn();
                }
            }
        }
    }

    @FXML
    public void getDataRow(MouseEvent event) throws IOException {
        int index = UsersIssuedBooks.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }
        ChoosenUser user = ChoosenUser.getInstance();
        ChosenIssuedBook book = ChosenIssuedBook.getInstance();

        DatabaseService database = new DatabaseService();
        database.GetUser(colLogin.getCellData(index));

        userLogin.setText(String.valueOf(colLogin.getCellData(index)));
        userName.setText(String.valueOf(user.getFirstName()));
        userSurname.setText(String.valueOf(user.getLastName()));
        bookTitle.setText(String.valueOf(colTitle.getCellData(index)));
        bookAuthor.setText(String.valueOf(colAuthor.getCellData(index)));
        Fee.setText(String.valueOf(colFee.getCellData(index)));
        Id.setText(String.valueOf(colID.getCellData(index)));


        user.setLogin(userLogin.getText());
        book.setUserFee(Double.valueOf(Fee.getText()));
        book.setIssuedBookId(Integer.valueOf(colID.getCellData(index)));
    }

}
