package dam1.prog.tiendav2;

import java.util.Scanner;

public class ShopFront {
  //Colores
  private static final String COLOR_WHITE = "\u001B[0m";
  private static final String COLOR_RED = "\u001B[31m";
  private static final String COLOR_GREEN = "\u001B[32m";

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Menu currentMenu = Menu.MENU_1;
    int selectedOption;
    do {
      System.out.println(currentMenu.getTitle());
      for (MenuItem option: currentMenu.getOptionList()
      ) {
        System.out.println(option.getOptionNumber() + ". " + option.getOptionLabel());
      }
      System.out.println("Por favor elija una opción");
      selectedOption = input.nextInt();
    }while (selectedOption != 0);
  }
}
