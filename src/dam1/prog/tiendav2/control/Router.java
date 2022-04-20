package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.ShoeModel;
import dam1.prog.tiendav2.view.Menu;
import dam1.prog.tiendav2.view.MenuItem;
import java.util.Arrays;
import java.util.Scanner;

public class Router {

  //Colores
  private static final String COLOR_WHITE = "\u001B[0m";
  private static final String COLOR_RED = "\u001B[31m";
  private static final String COLOR_GREEN = "\u001B[32m";
  private static final DbController DB_CONTROLLER = new DbController();

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

//    Menu currentMenu = Menu.MENU_PRINCIPAL;
    Menu currentMenu = Menu.MENU_CLIENTES;
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
            case "1" -> pintarTabla(input, DB_CONTROLLER.getClients());
            case "2" -> addNewClient(input);
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption = confirmAction(input, "salir") ? "0" : "";
            default -> {
            }
          }
        }
        case MENU_INVENTARIO -> {
          switch (selectedOption) {
            case "1" -> pintarTabla(input, DB_CONTROLLER.getStock());
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption = confirmAction(input, "salir") ? "0" : "";
            default -> {
            }
          }
        }
      }
    } while (!selectedOption.equalsIgnoreCase("0"));
  }

  //Métodos de presentación

  /**
   * Dado un array de los modelos del inventario imprime una tabla formateada de sus valores
   *
   * @param input entrada usuario
   * @param rows  modelos a imprimir
   */
  private static void pintarTabla(Scanner input, ShoeModel[] rows) {
    String fiveColFormat = "| %-6.6s | %-10.10s | %-20.20s | %s%8.8s%s | %8.8s€ |\n";
    if (rows.length > 0) {
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      System.out.printf(fiveColFormat, "Código", "Estilo", "Modelo", "", "Unidades", "", "Precio ");
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      Arrays.stream(rows).forEach(model -> {
        String colored = model.getAvailableUnits() == 0 ? COLOR_RED : COLOR_GREEN;
        System.out.printf(fiveColFormat, model.getID(), model.getStyle(), model.getDescription(),
            colored,
            model.getAvailableUnits(), COLOR_WHITE, model.getPrice());
      });
      System.out.println("+--------+------------+----------------------+----------+-----------+");
    } else {
      System.out.println(COLOR_RED + "No hay productos en el inventario" + COLOR_WHITE);
    }
    waitEnter(input);
  }

  /**
   * Dado un array de los clientes imprime una tabla formateada de sus valores
   *
   * @param input entrada de usuario
   * @param rows  clientes a imprimir
   */
  private static void pintarTabla(Scanner input, Client[] rows) {
    String threeColFormat = "| %-6.6s | %-20.20s | %9.9s |\n";
    if (rows.length > 0) {
      System.out.println("+--------+----------------------+-----------+");
      System.out.printf(threeColFormat, "Código", "Nombre", "Descuento");
      System.out.println("+--------+----------------------+-----------+");
      Arrays.stream(rows)
          .forEach(client -> System.out.printf(threeColFormat, client.getID(), client.getFullName(),
              client.hasDiscount() ? "Si" : "No"));
      System.out.println("+--------+----------------------+-----------+");
    } else {
      System.out.println(COLOR_RED + "No hay clientes en el sistema" + COLOR_WHITE);
    }
    waitEnter(input);
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

  //Métodos de la entrada de datos por consola

  /**
   * Pide al usuario que pulse Enter para continuar la ejecución
   *
   * @param input entrada del usuario
   */
  private static void waitEnter(Scanner input) {
    System.out.println("Pulse ENTER para continuar");
    input.nextLine();
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

  /**
   * Pide al usuario los datos de un nuevo cliente e intenta añadirlo al sistema
   *
   * @param input entrada de usuario
   */
  private static void addNewClient(Scanner input) {
    String consoleInput = "";
    boolean flag = true;
    Client newClient = new Client();
    while (flag) {
      System.out.println("Introduzca el nombre del nuevo cliente:");
      consoleInput = input.nextLine().trim();
      flag = consoleInput.length() < 4;
      if (flag) {
        System.out.println(
            COLOR_RED + "Debe introducir un nombre de usuario de al menos cuatro caracteres"
                + COLOR_WHITE);
      } else {
        newClient.setFullName(consoleInput);
      }
    }

    while (!flag) {
      System.out.println("¿El cliente " + consoleInput + " tiene descuento? s/n");
      consoleInput = input.nextLine().trim();
      flag = consoleInput.equalsIgnoreCase("s") || consoleInput.equalsIgnoreCase("n");
      if (!flag) {
        System.out.println(
            COLOR_RED + "Debe introducir s/n"
                + COLOR_WHITE);
      } else {
        newClient.setDiscount(consoleInput.equalsIgnoreCase("s"));
      }
    }

    if (Router.DB_CONTROLLER.addNewClient(newClient)) {
      System.out.println(
          COLOR_GREEN + "El cliente " + newClient.getFullName() + " se ha añadido con éxito"
              + COLOR_WHITE);
    } else {
      System.out.println(
          COLOR_RED + "Se ha producido un error al intentar añadir al cliente" + COLOR_WHITE);
    }
    waitEnter(input);
  }
}
