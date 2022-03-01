module com.example.jplquiz {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.example.jplquiz to
      javafx.fxml;

  exports com.example.jplquiz;
  exports com.example.jplquiz.controller;
  opens com.example.jplquiz.controller to javafx.fxml;
}
