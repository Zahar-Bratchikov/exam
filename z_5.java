import java.io.FileWriter;
import java.io.IOException;

public class z_5 {
    public static void main(String[] args) {
        double x = 10.0; // Значение x для вычисления arctg(x)
        double[] tolerances = { 1e-2, 1e-3, 1e-4 }; // Массив погрешностей
        int maxIterations = 1000; // Максимальное количество итераций

        String filename = "results.txt"; // Имя файла для записи результатов

        try {
            FileWriter writer = new FileWriter(filename);

            // Записываем заголовок файла
            writer.write("Функция f(x) = arctg(x) для x = " + x + " равняется" + Math.atan(x) + "\n");
            writer.write("Результаты определения значений функции f(x) = arctg(x) с помощью ряда Маклорена\n");

            // Вычисление для каждой погрешности
            for (double tolerance : tolerances) {
                writer.write("\nПогрешность итерационной процедуры: " + tolerance + "\n");
                writer.write("Функция по ряду Маклорена\tПогрешность от аналитики\tчисло итераций\n");

                double sum = 0.0;
                double error = Double.MAX_VALUE;
                int iterations = 0;

                // Вычисление ряда Маклорена для arctg(x)
                for (int n = 0; n < maxIterations && error > tolerance; n++) {
                    double term = ((n % 2 == 0) ? 1 : -1) * Math.pow(x, 2 * n + 1) / (2 * n + 1);
                    sum += term;
                    error = Math.abs(Math.atan(x) - sum);
                    iterations++;

                    // Запись результатов в файл
                    writer.write(sum + "\t"+ "\t" + error + "\t" + "\t" + iterations + "\n");
                }
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
