package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.view.ViewCreator;

public class Router {

  private static final DbController DB_CONTROLLER = new DbController();

  public static void main(String[] args) {

    Menu currentMenu = Menu.MENU_PRINCIPAL;

    String selectedOption;

    do {
      selectedOption = getOptionFromUser(currentMenu);

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
              ViewCreator.waitEnter();
            }
            case "2" -> addNewClient();
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
              ViewCreator.waitEnter();
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
   * Pide al usuario que elija una opción del menu y devuelve la opción seleccionada en mayúsculas
   *
   * @param menu         menu donde esta el usuario
   * @return opción seleccionada en mayusculas
   */
  private static String getOptionFromUser(Menu menu) {
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
   */
  private static void addNewClient() {
    String consoleInput = "";
    boolean flag = true;
    Client newClient = new Client();
    while (flag) {
      consoleInput = ViewCreator.pedirEntradaTexto("Introduzca el nombre del nuevo cliente:");
      flag = consoleInput.length() < 4;
      if (flag) {
        ViewCreator.mostrarError(
            "Debe introducir un nombre de usuario de al menos cuatro caracteres");
      } else {
        newClient.setFullName(consoleInput);
      }
    }

    newClient.setDiscount(
        ViewCreator.pedirConfirmacion("¿El cliente " + consoleInput + " tiene descuento? s/n"));

    if (Router.DB_CONTROLLER.addNewClient(newClient)) {
      ViewCreator.mostrarExito("El cliente " + newClient.getFullName()
          + " se ha añadido con éxito");
    } else {
      ViewCreator.mostrarError("Se ha producido un error al intentar añadir al cliente");
    }
    ViewCreator.waitEnter();
  }
}
