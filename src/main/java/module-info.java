module com.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.tracker to javafx.fxml;
    opens com.tracker.controller to javafx.fxml;
    opens com.tracker.model to javafx.base;

    exports com.tracker;
    exports com.tracker.controller;
    exports com.tracker.model;
    exports com.tracker.service;
}
