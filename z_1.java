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

import static java.lang.Math.*;

public class z_1 extends Application {

    private LineChart<Number, Number> lineChart;
    private TextField xTextField;
    private Label yLabel;
    private Label zLabel;
    private Label gLabel;

    public z_1() {
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Графики функций");

        // Создание осей
        final NumberAxis xAxis = new NumberAxis(-5, 5, 1);
        final NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        lineChart = new LineChart<>(xAxis, yAxis);

        // Создание поля ввода x
        Label xInputLabel = new Label("Введите значение x:");
        xTextField = new TextField();
        Button calculateButton = new Button("Вычислить");
        Button plotButton = new Button("Построить график");
        calculateButton.setOnAction(e -> calculateAndDisplayValues());
        plotButton.setOnAction(e -> updateChart());

        // Создание меток для отображения значений функций
        yLabel = new Label("y = ");
        zLabel = new Label("z = ");
        gLabel = new Label("g = ");

        // Создание сетки для размещения элементов управления
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.add(xInputLabel, 0, 0);
        gridPane.add(xTextField, 1, 0);
        gridPane.add(calculateButton, 2, 0);
        gridPane.add(yLabel, 0, 1);
        gridPane.add(zLabel, 0, 2);
        gridPane.add(gLabel, 0, 3);
        gridPane.add(plotButton, 0, 4);

        // Создание главной панели
        VBox mainPane = new VBox(10);
        mainPane.setPadding(new Insets(10));
        mainPane.getChildren().addAll(lineChart, gridPane);

        Scene scene = new Scene(mainPane, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateAndDisplayValues() {
        try {
            double x = Double.parseDouble(xTextField.getText());

            // Вычисление значений функций
            double y = pow(tan(x + exp(x) + exp(1)), 3);
            double z = (pow(cos(x), 2) - sin(x)) / (pow(x, 5) + 5);
            double g = 0;
            if (x < 0) {
                g = pow(100, abs(x));
            } else if (x >= 0) {
                g = exp(10 * abs(x));
            }

            // Отображение значений функций с округлением до 2 знаков после запятой
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            yLabel.setText("y = " + decimalFormat.format(y));
            zLabel.setText("z = " + decimalFormat.format(z));
            gLabel.setText("g = " + decimalFormat.format(g));
        } catch (NumberFormatException e) {
            // Обработка ошибки некорректного ввода значения x
            showAlert();
            yLabel.setText("y = ");
            zLabel.setText("z = ");
            gLabel.setText("g = ");
        }
    }

    // Построение графиков
    private void updateChart() {
        XYChart.Series<Number, Number> seriesY = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesZ = new XYChart.Series<>();
        XYChart.Series<Number, Number> seriesG = new XYChart.Series<>();

        for (double x = -2.5; x <= 2.5; x += 0.01) {
            seriesY.getData().add(new XYChart.Data<>(x, pow(tan(x + exp(x) + exp(1)), 3)));
            seriesZ.getData().add(new XYChart.Data<>(x, (pow(cos(x), 2) - sin(x)) / (pow(x, 5) + 5)));
            double g;
            if (x <= 0) {
                g = pow(100, abs(x));
            } else {
                g = exp(10 * abs(x));
            }
            seriesG.getData().add(new XYChart.Data<>(x, g));
        }

        seriesY.setName("y");
        seriesZ.setName("z");
        seriesG.setName("g");

        lineChart.getData().clear();
        lineChart.getData().addAll(seriesY, seriesZ, seriesG);
        lineChart.setCreateSymbols(false);
        // Сохранение данных в файл
        saveToFile();
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            writer.write("x\ty\tz\tg");
            writer.newLine();
            for (double i = -2.5; i <= 2.5; i += 0.25) {
                double y = pow(tan(i + exp(i) + exp(1)), 3);
                double z = (pow(cos(i), 2) - sin(i)) / (pow(i, 5) + 5);
                double g;
                if (i <= 0) {
                    g = pow(100, abs(i));
                } else {
                    g = exp(10 * abs(i));
                }
                writer.write(decimalFormat.format(i) + "\t" + decimalFormat.format(y) + "\t" + decimalFormat.format(z) + "\t" + decimalFormat.format(g));
                writer.newLine();
            }
            showAlert1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Некорректное значение x");
        alert.setContentText("Пожалуйста, введите число в поле для значения x.");
        alert.showAndWait();
    }

    private void showAlert1() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Сохранение данных");
        alert.setContentText("Данные сохранены в файл data.txt");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
