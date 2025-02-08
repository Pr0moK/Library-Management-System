package com.example.test.Controller;

import com.example.test.service.BookService;
import com.example.test.model.Book;
import com.example.test.service.DataLoaderService;
import com.example.test.utils.OnStatisticListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class BookListController {

    private OnStatisticListener StatisticListener;

    public void setOnStatisticListner(OnStatisticListener listener) {
        this.StatisticListener = listener;
    }

    @FXML
    private Label BookError;

    @FXML
    private TextField bookAmount;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookTitle;

    @FXML
    private TableView<Book> booksTable;

    @FXML
    private TableColumn<Book, Integer> colAmou;

    @FXML
    private TableColumn<Book, String> colAuth;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    public void initialize() throws IOException {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuth.setCellValueFactory(new PropertyValueFactory<>("author"));
        colAmou.setCellValueFactory(new PropertyValueFactory<>("amount"));
        LoadbookData();
    }

    public void LoadbookData() throws IOException {
        DataLoaderService loader = new DataLoaderService();
        try {
            ObservableList<Book> bookList = loader.loadBooksData();
            booksTable.setItems(bookList);
        } catch (IOException e) {
            System.err.println("Błąd podczas ładowania danych użytkowników: " + e.getMessage());
        }
    }

    @FXML
    protected void AddBook() throws IOException {
        if(bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()){
            BookError.setText("Uzupełnij wszystkie pola!");
        }else {
            BookError.setText("");
            try {
                BookService bookService = new BookService();

                bookService.AddBook(bookTitle.getText(),bookAuthor.getText(), Integer.parseInt(bookAmount.getText()));
                BookError.setText("Pomyślnie dodano książkę.");
                LoadbookData();
                if(StatisticListener != null){
                    StatisticListener.onStatisticReturn();
                }
            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Liczba musi być cyfrą!");
            }
        }
    }

    @FXML
    protected void EditBook() throws IOException {
        if (bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()) {
            BookError.setText("Uzupełnij wszystkie pola!");
        } else {
            BookError.setText("");
            try {

                BookService bookService = new BookService();

                if (bookService.EditBookAmount(bookTitle.getText(), bookAuthor.getText(), Integer.parseInt(bookAmount.getText()))) {
                    LoadbookData();
                    System.out.println("Pomyślnie edytowano liczbę książek.");
                    BookError.setText("Pomyślnie edytowano liczbę książek.");
                    if(StatisticListener != null){
                        StatisticListener.onStatisticReturn();
                    }
                } else {
                    System.out.println("Nie istnieje taka książka.");
                    BookError.setText("NIe znaleziono książki");
                }
            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Liczba nie jest cyfrą!");
            }

        }
    }

    @FXML
    protected void RemoveBook() throws IOException {
        if (bookAuthor.getText().isEmpty() || bookTitle.getText().isEmpty() || bookAmount.getText().isEmpty()) {
            BookError.setText("Uzupełnij wszystkie pola!");
        } else {
            BookError.setText("");
            try {


                BookService bookService = new BookService();

                if (bookService.RemoveBook(bookTitle.getText(), bookAuthor.getText())) {
                    BookError.setText("Pomyślnie usunięto książkę.");
                    LoadbookData();
                    if(StatisticListener != null){
                        StatisticListener.onStatisticReturn();
                    }
                } else {
                    BookError.setText("Nie znaleziono książki!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Amount nie jest cyfra.");
                BookError.setText("Liczba nie jest cyfrą!");
            }
        }
    }

    @FXML
    protected void GetdataRow() {
        int index = booksTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }
        bookTitle.setText(String.valueOf(colTitle.getCellData(index)));
        bookAuthor.setText(String.valueOf(colAuth.getCellData(index)));
        bookAmount.setText(String.valueOf(colAmou.getCellData(index)));
    }
}
