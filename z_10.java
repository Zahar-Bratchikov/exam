import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class z_10 {
    public static void main(String[] args) {
        String[] familyData = {
                "Возраст: 16; Пол: Мужской; Численность семьи: 4; Средний балл: 4.5; Место учебы: СОШ №12; Место жительства: город Пермь, улица Профессора Дедюкина, дом 10, квартира 1; Количество кружков: 3",
                "Возраст: 17, Пол: Женский; Численность семьи: 2; Средний балл: 4.0; Место учебы: СОШ №12; Место жительства: город Пермь, улица Маршала Рыбалко, дом 23, квартира 30; Количество кружков: 1",
                // Дополнительные строки данных семей
        };

        String filename = "family_data.txt"; // Имя файла для записи результатов

        try {
            FileWriter writer = new FileWriter(filename);

            // Записываем исходные данные в файл
            writer.write("Исходная информация:\n");
            for (String data : familyData) {
                writer.write(data + "\n");
            }

            writer.write("\nИзмененная информация:\n");

            // Выполняем автозамену информации в цикле
            for (String data : familyData) {
                // Замена понятий с помощью регулярных выражений
                data = data.replaceAll("\\bквартира\\b", "кв.")
                        .replaceAll("\\bколичество\\b", "кол.")
                        .replaceAll("\\bулица\\b", "ул.")
                        .replaceAll("\\bгород\\b", "г.");

                // Запись измененной информации в файл
                writer.write(data + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}