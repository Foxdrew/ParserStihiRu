package org.foxdrew.utils;

import java.util.Scanner;

public class UserInput {
    public static String getAuthor() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Write author name: ");
        String name = scanner.nextLine();
        scanner.close();
        return name;
    }
}
