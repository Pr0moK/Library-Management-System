package com.example.test.service;

import com.example.test.repository.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class IssuedBookFeeService extends DatabaseConnection {


    public IssuedBookFeeService() throws IOException {
        super();
    }


    public boolean UpdateFee(Integer id){
        String QUERY = "UPDATE IssuedBooks SET fee = ? WHERE Id = ?";

        try(Connection connection = getConnection(); PreparedStatement prep = connection.prepareStatement(QUERY)){
            prep.setInt(1,0);
            prep.setInt(2,id);

            int RowsAffected = prep.executeUpdate();
            if(RowsAffected > 0){
                System.out.println("Fee Updated Successfully");
                return true;
            } else {
                System.out.println("Fee Updated Failed");
                return false;
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }


    }

    public void CheckFee() {
        String QUERY = "SELECT fee, datereturn, Id FROM IssuedBooks WHERE CURRENT_DATE() > datereturn";

        try (Connection connection = getConnection();
             PreparedStatement prep = connection.prepareStatement(QUERY)) {

            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                String ReturnDate = rs.getString(2);
                int bookId = rs.getInt(3);
                String TodayDate = LocalDate.now().toString();
                final long days = ChronoUnit.DAYS.between(LocalDate.parse(ReturnDate), LocalDate.parse(TodayDate));
                double fee = rs.getDouble(1);

                System.out.println("Days overdue: " + days);
                System.out.println("Current fee: " + fee);

                if (fee == days * 0.5) {
                    System.out.println("Fee matches value");
                } else {
                    String UPDATEFEE = "UPDATE IssuedBooks SET fee = ? WHERE datereturn = ? AND Id = ?";
                    try (PreparedStatement pr2 = connection.prepareStatement(UPDATEFEE)) {
                        fee = days * 0.5;
                        System.out.println("New fee: " + fee);
                        pr2.setDouble(1, fee);
                        pr2.setString(2, ReturnDate);
                        pr2.setInt(3, bookId);
                        int RowsUpdated = pr2.executeUpdate();
                        if (RowsUpdated > 0) {
                            System.out.println("Fee updated");
                        } else {
                            System.out.println("Failed to update fee");
                        }
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
