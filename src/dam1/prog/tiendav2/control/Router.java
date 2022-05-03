package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.Utils;
import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.models.ShoeModel;
import dam1.prog.tiendav2.view.ViewCreator;
import java.util.Arrays;

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
        case MENU_PEDIDOS -> {
          Client clienteActual = identificarCliente();
          //TODO Si el cliente tiene ID -1 pregunta si quiere crear un cliente nuevo y lo manda al menu de clientes
          //TODO Si el cliente tiene ID > 0 crea un nuevo pedido
        }
        case MENU_CLIENTES -> {
          switch (selectedOption) {
            case "1" -> {
              ViewCreator.pintarTabla(DB_CONTROLLER.getClients());
              ViewCreator.waitEnter();
            }
            case "2" -> addNewClient();
            case "3" -> deleteClient();
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
            case "2" -> addNewModel();
            case "3" -> deleteModel();
            case "4" -> modifyStock();
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

  private static Client identificarCliente() {
    //TODO muestra al usuario la tabla de clientes.
    //TODO Le pide un ID de cliente.
    //TODO Si el cliente esta en la tabla de clientes lo devuelve
    //TODO Si no devuelve un cliente con ID -1
    return new Client();
  }

  /**
   * Pide al usuario que elija una opción del menu y devuelve la opción seleccionada en mayúsculas
   *
   * @param menu menu donde esta el usuario
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
   * Pide al usuario los datos de un nuevo modelo de zapato e intenta añadirlo al sistema
   */
  private static void addNewModel() {
    ViewCreator.mostraMensaje("Por favor introduzca los datos del nuevo modelo de zapato:");
    ShoeModel zapato = new ShoeModel();
    zapato.setDescription(ViewCreator.pedirEntradaTexto("Descripción:"));
    zapato.setStyle(ViewCreator.pedirEntradaTexto("Estilo:"));
    String consoleInput = "";

    boolean isValidInput = false;
    while (!isValidInput) {
      consoleInput = ViewCreator.pedirEntradaTexto("Precio:");
      isValidInput = Utils.isDoubleString(consoleInput);
      if (isValidInput) {
        double price = Double.parseDouble(consoleInput);
        if (price > 0) {
          zapato.setPrice(Double.parseDouble(consoleInput));
        } else {
          ViewCreator.mostrarError("El número debe ser mayor que 0");
          isValidInput = false;
        }
      } else {
        ViewCreator.mostrarError(
            "Debe introducir un número mayor que 0");
      }
    }

    if (DB_CONTROLLER.addShoeModel(zapato)) {
      ViewCreator.mostrarExito("El nuevo modelo de zapato se ha añadido al inventario con éxito");
    } else {
      ViewCreator.mostrarError(
          "Se ha producido un error al intentar añadir el nuevo modelo de zapato.");
    }
    ViewCreator.waitEnter();
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

    if (DB_CONTROLLER.addNewClient(newClient)) {
      ViewCreator.mostrarExito("El cliente " + newClient.getFullName()
          + " se ha añadido con éxito");
    } else {
      ViewCreator.mostrarError("Se ha producido un error al intentar añadir al cliente");
    }
    ViewCreator.waitEnter();
  }

  /**
   * Muestra al usuario la lista de los modelos en stock y le pide el ID del que desea eliminar. A
   * continuación intenta eliminarlo de la base de datos.
   */
  private static void deleteModel() {
    String consoleInput = "";
    ViewCreator.pintarTabla(DB_CONTROLLER.getStock());
    consoleInput = ViewCreator.pedirEntradaTexto(
        "Introduzca el código del modelo que desea eliminar:");
    if (DB_CONTROLLER.removeShoeModel(consoleInput)) {
      ViewCreator.mostrarExito(
          "Los datos del modelo con el código " + consoleInput + " se han eliminado");
    } else {
      ViewCreator.mostrarError(
          "Se ha producido un error al intentar borrar los datos del modelo " + consoleInput);
    }
    ViewCreator.waitEnter();
  }

  /**
   * Muestra al usuario la lista de los clientes en el sistema y le pide el ID del que desea
   * eliminar. A continuación intenta eliminarlo de la base de datos.
   */
  private static void deleteClient() {
    String consoleInput = "";
    ViewCreator.pintarTabla(DB_CONTROLLER.getClients());
    consoleInput = ViewCreator.pedirEntradaTexto(
        "Introduzca el código del cliente que desea eliminar:");
    if (DB_CONTROLLER.removeClient(consoleInput)) {
      ViewCreator.mostrarExito(
          "Los datos del cliente con el código " + consoleInput + " se han eliminado");
    } else {
      ViewCreator.mostrarError(
          "Se ha producido un error al intentar borrar los datos del cliente " + consoleInput);
    }
    ViewCreator.waitEnter();
  }

  /**
   * Muestra al usuario el inventario. Y le pide el ID del modelo del cual quiere modificar el
   * número de artículos en stock. A continuación le solicita el nuevo número de artículos y luego
   * intenta actualizar el inventario en la base de datos.
   */
  private static void modifyStock() {
    String entradaUsuario = "";
    boolean esValido = false;
    //Pintar inventario
    ShoeModel[] inventario = DB_CONTROLLER.getStock();
    ViewCreator.pintarTabla(inventario);
    //Pedir un ID
    int id = -1;
    while (!esValido) {
      entradaUsuario = ViewCreator.pedirEntradaTexto(
          "Introduzca el ID del modelo que quiere modificar:");
      if (Utils.isIntString(entradaUsuario)) {
        id = Integer.parseInt(entradaUsuario);
        esValido = true;
      } else {
        ViewCreator.mostrarError("Debe introducir un ID valido. Los ID son enteros mayores de 0.");
      }
    }
    //Obtener el objeto ShoeModel con ese id, o uno con id -1 si no está
    int finalID = id; //Cuidado hecha final de forma efectiva para la exp. Lambda
    ShoeModel modeloSeleccionado = Arrays.stream(inventario)
        .filter(modelo -> modelo.getID() == finalID)
        .findFirst().orElse(new ShoeModel());

    //Comprobar si el modelo existe
    if (modeloSeleccionado.getID() > 0) {
      ViewCreator.pintarTabla(new ShoeModel[]{modeloSeleccionado});
      esValido = false;
      ViewCreator.mostraMensaje(
          "El modelo seleccionado tiene " + modeloSeleccionado.getAvailableUnits() + " en stock.");
      //Pedir el nuevo número de unidades de ese modelo
      while (!esValido) {
        entradaUsuario = ViewCreator.pedirEntradaTexto(
            "Introduzca el nuevo número de unidades:");
        if (Utils.isIntString(entradaUsuario)) {
          modeloSeleccionado.setAvailableUnits(Integer.parseInt(entradaUsuario));
          esValido = true;
        } else {
          ViewCreator.mostrarError("El nuevo número de unidades debe ser 0 o un entero positivo.");
        }
      }
      //Usar el controlador para actualizar la bd
      modeloSeleccionado = DB_CONTROLLER.updateShoeModel(modeloSeleccionado);
      //Comprobar si actualizo con exito
      if (modeloSeleccionado.getID() > 0) {
        ViewCreator.mostrarExito("La cantidad de unidades en stock se ha modificado con éxito.");
      } else {
        ViewCreator.mostrarError("Ha ocurrido un error al intentar modificar la base de datos");
      }

      //Si el ID no coincide con ningun modelo de la bd
    } else {
      ViewCreator.mostrarError("No hay ningún modelo con ese ID");
    }
  }
}
