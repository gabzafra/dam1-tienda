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
      selectedOption = getOptionFromUser(input, currentMenu);
    } while (selectedOption != 0);
  }

  private static void pintarMenu(Menu menu){
    System.out.println(menu.getTitle());
    System.out.println("---------------------------------------");
    for (MenuItem option : menu.getOptionList()
    ) {
      System.out.println(option.getOptionNumber() + ". " + option.getOptionLabel());
    }
    System.out.println("---------------------------------------");
  }

  private static int getOptionFromUser(Scanner consoleInput, Menu menu) {
    int userInput = -1;
    boolean isAnInt;
    while (true) {
      pintarMenu(menu);
      isAnInt = true;
      System.out.println("Por favor elija una opción del menú:");
      try {
        userInput = Integer.parseInt(consoleInput.nextLine());
      } catch (NumberFormatException e) {
        isAnInt = false;
        System.out.println(COLOR_RED + "Debe escribir un número!" + COLOR_WHITE);
      }
      if (isAnInt) {
        for (MenuItem opcion : menu.getOptionList()) {
          if (opcion.getOptionNumber() == userInput) {
            return userInput;
          }
        }
      }
    }
  }
}
