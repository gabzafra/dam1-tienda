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
    String selectedOption;
    do {
      selectedOption = getOptionFromUser(input, currentMenu);
      switch (currentMenu) {
        case MENU_1 -> {
          switch (selectedOption) {
            case "1" -> currentMenu = Menu.MENU_2;
            default -> {
            }
          }
        }
        case MENU_2 -> {
          switch (selectedOption) {
            case "1" -> currentMenu = Menu.MENU_1;
            default -> {
            }
          }
        }
      }
    } while (!selectedOption.equalsIgnoreCase("0"));
  }

  /**
   * Pinta por consola el menu dado como argumento
   *
   * @param menu que se quiere pintar por consola
   */
  private static void pintarMenu(Menu menu) {
    System.out.println(menu.getTitle());
    System.out.println("---------------------------------------");
    for (MenuItem option : menu.getOptionList()
    ) {
      System.out.println(option.getOptionNumber() + ". " + option.getOptionLabel());
    }
    System.out.println("---------------------------------------");
  }

  /**
   * Pide al usuario que elija una opción del menu y devuelve la opción seleccionada en mayúsculas
   *
   * @param consoleInput entrada del usuario
   * @param menu         menu donde esta el usuario
   * @return opción seleccionada en mayusculas
   */
  private static String getOptionFromUser(Scanner consoleInput, Menu menu) {
    String userInput;
    do {
      pintarMenu(menu);
      System.out.println("Por favor elija una opción del menú:");
      userInput = consoleInput.nextLine().trim();
      for (MenuItem opcion : menu.getOptionList()) {
        if (opcion.getOptionNumber().equalsIgnoreCase(userInput)) {
          return userInput.toUpperCase();
        }
      }
      System.out.println(COLOR_RED + "La opción seleccionada no es válida" + COLOR_WHITE);
    } while (true);

  }
}
