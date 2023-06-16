import java.io.FileWriter;
import java.io.IOException;

public class z_3 {
    public static void main(String[] args) {
        double startX = -4.0;
        double endX = 4.0;
        double stepX = 0.2;
        int xSize = (int) ((endX - startX) / stepX) + 1;

        double startY = -4.0;
        double endY = 4.0;
        double stepY = 0.25;
        int ySize = (int) ((endY - startY) / stepY) + 1;

        double[][] dataArray = new double[xSize][ySize];

        // Заполнение массива данными
        for (int i = 0; i < xSize; i++) {
            double x = startX + i * stepX;
            for (int j = 0; j < ySize; j++) {
                double y = startY + j * stepY;
                double f = Math.pow(6 * Math.pow(x, 3) - Math.exp(x), 2) - 2 * Math.pow(y, 2);
                dataArray[i][j] = f;
            }
        }

        // Запись данных в файл
        try (FileWriter writer = new FileWriter("data2.txt")) {
            // Запись шапки
            writer.write("Данные по координатам x, y и z\n");
            writer.write("f = (6*x^3 - e^x)^2 - 2*y^2\n");
            writer.write("x=[" + startX + ";" + endX + "] с шагом " + stepX + "\n");
            writer.write("y=[" + startY + ";" + endY + "] с шагом " + stepY + "\n");

            // Запись данных
            for (int i = 0; i < xSize; i++) {
                for (int j = 0; j < ySize; j++) {
                    double x = startX + i * stepX;
                    double y = startY + j * stepY;
                    double z = dataArray[i][j];
                    writer.write("x=" + x + ", y=" + y + ", z=" + z + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Данные успешно записаны в файл.");
    }
}
