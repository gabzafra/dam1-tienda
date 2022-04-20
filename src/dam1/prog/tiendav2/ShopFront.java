package dam1.prog.tiendav2;

import java.util.Scanner;

public class ShopFront {

  //Colores
  private static final String COLOR_WHITE = "\u001B[0m";
  private static final String COLOR_RED = "\u001B[31m";
  private static final String COLOR_GREEN = "\u001B[32m";

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    DbController db = new DbController();
    Menu currentMenu = Menu.MENU_PRINCIPAL;
    String selectedOption;

    do {
      selectedOption = getOptionFromUser(input, currentMenu);
      switch (currentMenu) {
        case MENU_PRINCIPAL -> {
          switch (selectedOption) {
            case "1" -> currentMenu = Menu.MENU_PEDIDOS;
            case "2" -> currentMenu = Menu.MENU_CLIENTES;
            case "3" -> currentMenu = Menu.MENU_INVENTARIO;
            case "0" -> selectedOption = confirmAction(input, "salir") ? "0" : "";
            default -> {
            }
          }
        }
        case MENU_CLIENTES -> {
          switch (selectedOption) {
            case "1" -> pintarInventario(input);
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption = confirmAction(input, "salir") ? "0" : "";
            default -> {
            }
          }
        }
        case MENU_INVENTARIO -> {
          switch (selectedOption) {
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption = confirmAction(input, "salir") ? "0" : "";
            default -> {
            }
          }
        }
      }
    } while (!selectedOption.equalsIgnoreCase("0"));
  }

  private static void pintarInventario(Scanner input) {

  }

  /**
   * Pregunta al usuario si desea continuar con una acción
   *
   * @param input entrada del usuario
   * @return true si confirma false si no
   */
  private static boolean confirmAction(Scanner input, String action) {
    System.out.println("¿Seguro que desea " + action + "? s/n");
    return input.nextLine().trim().equalsIgnoreCase("s");
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
