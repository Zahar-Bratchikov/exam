import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class z_2 extends Application {

    private double startX;
    private double endX;
    private double step;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("График функции");

        // Создание полей ввода для диапазона значений x и шага
        TextField startTextField = new TextField();
        TextField endTextField = new TextField();
        TextField stepTextField = new TextField();

        Label startLabel = new Label("Начальное значение x:");
        Label endLabel = new Label("Конечное значение x:");
        Label stepLabel = new Label("Шаг:");

        // Создание кнопки для запуска построения графика и сохранения данных
        Button generateButton = new Button("Сгенерировать и сохранить");
        generateButton.setOnAction(e -> {
            try {
                startX = Double.parseDouble(startTextField.getText());
                endX = Double.parseDouble(endTextField.getText());
                step = Double.parseDouble(stepTextField.getText());

                if (startX >= endX) {
                    showAlert1("Некорректный ввод", "Начальное значение x должно быть меньше конечного значения x.");
                    return;
                }

                populateData();
            } catch (NumberFormatException ex) {
                showAlert1("Некорректный ввод", "Пожалуйста, введите числовые значения для диапазона и шага.");
            }
        });

        // Создание сетки для размещения элементов интерфейса
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);
        inputGrid.setPadding(new Insets(10));
        inputGrid.add(startLabel, 0, 0);
        inputGrid.add(startTextField, 1, 0);
        inputGrid.add(endLabel, 0, 1);
        inputGrid.add(endTextField, 1, 1);
        inputGrid.add(stepLabel, 0, 2);
        inputGrid.add(stepTextField, 1, 2);
        inputGrid.add(generateButton, 0, 3, 2, 1);

        // Создание контейнера для сетки и установка его в сцену
        VBox mainPane = new VBox(10);
        mainPane.getChildren().addAll(inputGrid);
        Scene scene = new Scene(mainPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populateData() {
        Stage chartStage = new Stage();
        chartStage.setTitle("График функции");

        // Создание осей с заданным масштабом
        final NumberAxis xAxis = new NumberAxis(startX, endX, step);
        final NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("График функции");

        // Создание кнопки для записи данных в файл
        Button saveButton = new Button("Сохранить в файл");
        saveButton.setOnAction(e -> saveDataToFile(lineChart));

        VBox mainPane = new VBox(10);
        mainPane.setPadding(new Insets(10));
        mainPane.getChildren().addAll(lineChart, saveButton);

        Scene scene = new Scene(mainPane, 600, 600);
        chartStage.setScene(scene);
        chartStage.show();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        for (double x = startX; x <= endX; x += step) {
            double y = calculateFunctionValue(x);
            series.getData().add(new XYChart.Data<>(x, y));
        }
        series.setName("z");
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
    }

    //определение функции
    private double calculateFunctionValue(double x) {
        double g;
        if (x < 0) {
            g = ((-2 * (Math.pow(x, 2))) - Math.exp(1)) / (Math.sqrt(1 + Math.pow(x, 2)));
        } else if (x >= 1) {
            g = 2 - Math.cos(Math.sin(3 * x));
        } else {
            g = Math.sqrt(Math.abs(1 + Math.cos(x)));
        }
        return g;
    }

    private void saveDataToFile(LineChart<Number, Number> lineChart) {
        double[][] data = new double[3][(int) ((endX - startX) / step) + 1];

        int index = 0;
        for (XYChart.Data<Number, Number> dataPoint : lineChart.getData().get(0).getData()) {
            double x = dataPoint.getXValue().doubleValue();
            double y = dataPoint.getYValue().doubleValue();

            data[0][index] = index + 1;
            data[1][index] = x;
            data[2][index] = y;

            index++;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data1.txt"))) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            writer.write("Номер\tX\tЗначение функции");
            writer.newLine();
            for (int i = 0; i < data[0].length; i++) {
                writer.write(decimalFormat.format(data[0][i]) + "\t" +
                        decimalFormat.format(data[1][i]) + "\t" +
                        decimalFormat.format(data[2][i]));
                writer.newLine();
            }
            showAlert();
        } catch (IOException e) {
            showAlert1("Ошибка сохранения", "Произошла ошибка при сохранении данных.");
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Сохранение данных");
        alert.setContentText("Данные сохранены в файл data1.txt");
        alert.showAndWait();
    }

    private void showAlert1(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
