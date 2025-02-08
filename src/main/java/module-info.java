module com.example.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires mysql.connector.j;
    requires org.apache.commons.lang3;

    opens com.example.test to javafx.fxml;
    exports com.example.test;
    exports com.example.test.utils;
    opens com.example.test.utils to javafx.fxml;
    exports com.example.test.Controller;
    opens com.example.test.Controller to javafx.fxml;
    exports com.example.test.repository;
    opens com.example.test.repository to javafx.fxml;
    exports com.example.test.service;
    opens com.example.test.service to javafx.fxml;
    exports com.example.test.model;
    opens com.example.test.model to javafx.fxml;
}