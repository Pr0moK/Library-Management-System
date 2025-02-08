package com.example.test.Controller;

import com.example.test.NewSceneController;
import com.example.test.service.BookService;
import com.example.test.service.DatabaseService;
import com.example.test.utils.OnStatisticListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;


public class MenuController implements OnStatisticListener{

    @FXML
    private Label TotalBooks;
    @FXML
    private Label TotalIssuedBooks;
    @FXML
    private Label TotalUsers;


    @FXML
    public void initialize() throws IOException {
        LoadStatistics();
    }
    private void LoadStatistics() throws IOException {
        DatabaseService base = new DatabaseService();
        BookService bm = new BookService();
        TotalBooks.setText(String.valueOf(bm.CountBooks()));
        TotalIssuedBooks.setText(String.valueOf(bm.CountIssuedBooks()));
        TotalUsers.setText(String.valueOf(base.CountUsers()));
    }

    @FXML
    protected void BookListButtonClick() throws IOException {
        NewSceneController booklistscene = new NewSceneController("BooklistView.fxml",800,800,this);
    }
    @FXML
    protected void UserListButtonClick() throws IOException {
        NewSceneController userlistscene = new NewSceneController("UsersView.fxml",800,800);

    }
    @FXML
    protected void LendBookWindow() throws IOException {
        NewSceneController lendbookwindow = new NewSceneController("LendbookView.fxml",800,800,this);
    }
    @FXML
    protected void ReturnbookWindow() throws IOException{
        NewSceneController returnbookwindow = new NewSceneController("ReturnbookView.fxml",800,800,this);
    }

    @Override
    public void onStatisticReturn() throws IOException {
        LoadStatistics();
    }


}