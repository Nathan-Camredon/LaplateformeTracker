module com.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires io.github.cdimascio.dotenv.java;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.tracker to javafx.fxml;
    exports com.tracker;
}
