package com.example.test.Controller;

import com.example.test.model.ChosenIssuedBook;
import com.example.test.model.ChoosenUser;
import com.example.test.service.BookService;
import com.example.test.service.IssuedBookFeeService;
import com.example.test.utils.OnStatisticListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.test.utils.OnBookReturnListener;



import java.io.IOException;

public class AcceptBookReturnViewController {

    @FXML
    private Button AcceptReturnButton;

    @FXML
    private Button DeclineReturnButton;

    @FXML
    private Label fee;

    @FXML
    private Label username;

    private OnBookReturnListener bookReturnListener;

    public void setOnBookReturnListener(OnBookReturnListener listener) {
        this.bookReturnListener = listener;
    }


    @FXML
    public void initialize() {
        username.setText(ChoosenUser.getInstance().getUserLogin());
        fee.setText(ChosenIssuedBook.getInstance().getUserFee() + "zł ");
    }

    @FXML
    public void DeclineReturnButton(ActionEvent event) {
        CloseWindowController scene = new CloseWindowController(event);
    }

    @FXML
    public void AcceptReturnButton(ActionEvent event) throws IOException {
        IssuedBookFeeService fee = new IssuedBookFeeService();
        if (fee.UpdateFee(ChosenIssuedBook.getInstance().getIssuedBookId())) {
            BookService bookService = new BookService();
            bookService.ReturnBook(ChosenIssuedBook.getInstance().getIssuedBookId());

            if (bookReturnListener != null) {
                bookReturnListener.onBookReturned();
                CloseWindowController scene1 = new CloseWindowController(event);
            } else {
                System.out.println("You have declined the book");
            }
        }
    }
}
