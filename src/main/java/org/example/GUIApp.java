package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.UUID;

public class GUIApp extends Application {
    private Store store = new Store("GUI Store");
    private ListView<String> listView = new ListView<>();
    private TextArea detailsArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clothes Manager PRO (UUID)");

        // Панель вводу
        TextField brandIn = new TextField(); brandIn.setPromptText("Бренд");
        TextField typeIn = new TextField(); typeIn.setPromptText("Модель");
        TextField priceIn = new TextField(); priceIn.setPromptText("Ціна");
        ComboBox<Size> sizeIn = new ComboBox<>(FXCollections.observableArrayList(Size.values()));
        
        Button addBtn = new Button("Додати товар");
        addBtn.setOnAction(e -> {
            try {
                Pants p = new Pants(typeIn.getText(), brandIn.getText(), sizeIn.getValue(), 
                                    Double.parseDouble(priceIn.getText()), false);
                store.addNewClothes(p, 1);
                updateList();
            } catch (Exception ex) { showMsg("Помилка даних!"); }
        });

        VBox leftSide = new VBox(10, new Label("Новий товар:"), brandIn, typeIn, priceIn, sizeIn, addBtn);
        leftSide.setPadding(new Insets(15));

        // Панель списку та пошуку
        TextField searchIn = new TextField(); searchIn.setPromptText("Вставте UUID");
        Button searchBtn = new Button("Знайти за UUID");
        searchBtn.setOnAction(e -> {
            try {
                StoreItem res = store.findByUuid(UUID.fromString(searchIn.getText().trim()));
                detailsArea.setText(res != null ? res.getClothes().getFullDetails() : "Не знайдено!");
            } catch (Exception ex) { showMsg("Невірний формат UUID!"); }
        });

        VBox centerSide = new VBox(10, new Label("Список (Коротко):"), listView, searchIn, searchBtn);
        centerSide.setPadding(new Insets(15));

        detailsArea.setPromptText("Тут з'являться деталі об'єкта...");
        detailsArea.setEditable(false);

        BorderPane root = new BorderPane();
        root.setLeft(leftSide);
        root.setCenter(centerSide);
        root.setBottom(detailsArea);

        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    private void updateList() {
        listView.getItems().clear();
        for (StoreItem item : store.getInventory()) {
            Clothes c = item.getClothes();
            listView.getItems().add(c.getBrand() + " [" + c.getType() + "] | UUID: " + c.getUuid().toString().substring(0, 8));
        }
    }

    private void showMsg(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(text);
        a.show();
    }

    public static void main(String[] args) { launch(args); }
}