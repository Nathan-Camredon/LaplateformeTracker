module com.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires io.github.cdimascio.dotenv.java;

    opens com.tracker to javafx.fxml;
    opens com.tracker.controller to javafx.fxml;
    opens com.tracker.model to javafx.base, javafx.fxml;

    exports com.tracker;
    exports com.tracker.model;
    exports com.tracker.config;
    exports com.tracker.service;
}
