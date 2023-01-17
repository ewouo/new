package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int count = 0;
        String path;
        System.out.println("Введите путь: ");
        path = new Scanner(System.in).nextLine();
        File file = new File(path);
        while (true) {
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == true && isDirectory == true) {
                System.out.println("Путь корректен, но ведет к папке");
                break;
            } else if (fileExists == true && isDirectory == false) {
                System.out.println("Путь указан верно");
                count++;
                // System.out.println("Это файл номер " + count);
                break;
            } else {
                System.out.println("Путь некорректен");
                break;
            }

        }
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            int count1 = 0, max = 0, min = 1024;
            String[] str = new String[0];
            int kol = 0;
            while ((line = reader.readLine()) != null) {
                count1++;
                int length = line.length();
                str = line.split("\" ");
                String[] parts = str[2].split(";");
                if (parts.length >= 2) {
                    String fragment = parts[1];
                    // String str1 = fragment.trim();
                    String[] parts1 = fragment.split("/");

                    if ((parts1[0].strip().equals("YandexBot")) || (parts1[0].strip().equals("GoogleBot"))) kol++;

                }
                if (length >= max) max = length;
                if (length <= min) min = length;
                if (length >= 1024) throw new OperationAttemptException();

            }
            System.out.println("Всего строк в файле: " + count1);
            System.out.println(kol);
            System.out.println(kol+"/"+count1);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }

// /Users/emelkova/Downloads/1.csv
}

class OperationAttemptException extends Exception{
    public OperationAttemptException() {
        System.out.println("Длина строки не может превышать 1024 символа");
    }
}