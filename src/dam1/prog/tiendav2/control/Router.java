package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.view.ViewCreator;
import java.util.Scanner;

public class Router {

  private static final DbController DB_CONTROLLER = new DbController();

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

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
            case "0" -> selectedOption =
                ViewCreator.pedirConfirmacion("¿Desea salir? s/n:") ? "0" : "";
            default -> {
            }
          }
        }
        case MENU_CLIENTES -> {
          switch (selectedOption) {
            case "1" -> {
              ViewCreator.pintarTabla(DB_CONTROLLER.getClients());
              ViewCreator.waitEnter(input);
            }
            case "2" -> addNewClient(input);
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption =
                ViewCreator.pedirConfirmacion("¿Desea salir? s/n:") ? "0" : "";
            default -> {
            }
          }
        }
        case MENU_INVENTARIO -> {
          switch (selectedOption) {
            case "1" -> {
              ViewCreator.pintarTabla(DB_CONTROLLER.getStock());
              ViewCreator.waitEnter(input);
            }
            case "9" -> currentMenu = Menu.MENU_PRINCIPAL;
            case "0" -> selectedOption =
                ViewCreator.pedirConfirmacion("¿Desea salir? s/n:") ? "0" : "";
            default -> {
            }
          }
        }
      }
    } while (!selectedOption.equalsIgnoreCase("0"));
  }

  //Métodos de presentación

  //Métodos de la entrada de datos por consola

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
      ViewCreator.pintarMenu(menu);
      userInput = ViewCreator.pedirEntradaTexto("Por favor elija una opción del menú:");
      for (MenuItem opcion : menu.getOptionList()) {
        if (opcion.getOptionNumber().equalsIgnoreCase(userInput)) {
          return userInput.toUpperCase();
        }
      }
      ViewCreator.mostrarError("La opción seleccionada no es válida");
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
            dam1.prog.tiendav2.Utils.COLOR_RED
                + "Debe introducir un nombre de usuario de al menos cuatro caracteres"
                + dam1.prog.tiendav2.Utils.COLOR_WHITE);
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
            dam1.prog.tiendav2.Utils.COLOR_RED + "Debe introducir s/n"
                + dam1.prog.tiendav2.Utils.COLOR_WHITE);
      } else {
        newClient.setDiscount(consoleInput.equalsIgnoreCase("s"));
      }
    }

    if (Router.DB_CONTROLLER.addNewClient(newClient)) {
      System.out.println(
          dam1.prog.tiendav2.Utils.COLOR_GREEN + "El cliente " + newClient.getFullName()
              + " se ha añadido con éxito"
              + dam1.prog.tiendav2.Utils.COLOR_WHITE);
    } else {
      System.out.println(
          dam1.prog.tiendav2.Utils.COLOR_RED
              + "Se ha producido un error al intentar añadir al cliente"
              + dam1.prog.tiendav2.Utils.COLOR_WHITE);
    }
    ViewCreator.waitEnter(input);
  }
}
