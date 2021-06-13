import Items.ItemGenerator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner userInput = new Scanner(System.in);

        System.out.println("Wellcome! what is your hero name?");

        ItemGenerator ig = new ItemGenerator();
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
        System.out.println(ig.generatePotion().getName());
    }
}
