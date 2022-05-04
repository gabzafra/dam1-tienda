package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.Utils;
import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.models.Order;
import dam1.prog.tiendav2.models.Shoe;
import dam1.prog.tiendav2.models.ShoeModel;
import dam1.prog.tiendav2.view.ViewCreator;
import java.util.Arrays;

public class Router {

  private static final DbController DB_CONTROLLER = new DbController();

  public static void main(String[] args) {

    Menu currentMenu = Menu.MENU_PRINCIPAL;
    Order currentOrder = new Order();
    Client currentClient;

    String selectedOption;

    do {
      //Controlar si es un pedido nuevo
      if (currentMenu == Menu.MENU_PEDIDOS && currentOrder.getID() < 0) {
        currentClient = identificarCliente();
        if (currentClient.getID() < 0) {
          //Caso de cliente nuevo
          currentMenu = Menu.MENU_CLIENTES;
        } else {
          //Crear nuevo pedido
          currentOrder = DB_CONTROLLER.createOrder(currentClient.getID());
          if (currentOrder.getID() < 0) {
            ViewCreator.mostrarError("Ha ocurrido un error al crear el pedido en la base de datos");
            currentMenu = Menu.MENU_PRINCIPAL;
          } else {
            ViewCreator.mostrarExito(
                "Se ha creado un nuevo pedido para el cliente " + currentClient.getFullName());
          }
        }
      }

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
          switch (selectedOption) {
            case "1" -> addProduct(currentOrder);
            case "2" -> removeProduct();
            case "3" -> realizarPago();
            case "4" -> cancelOrder();
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

  private static void cancelOrder() {
    /*TODO confirma que se quiere cancelar el pedido
     *  - si se confirma actualiza el estado del pedido a CANCELADO, se reinicia el pedido actual
     *  y volvemos al menu principal*/
  }

  private static void realizarPago() {
    /*TODO muestra la factura del pedido actual
     *  confirma si se desea completar el pago
     *  - si confirma se actualiza el estado del pedido a PAGADO, se reinicia el pedido actual
     *  y volvemos al menu PRINCIPAL.
     *  - si no se confirma volvemos al menu de PEDIDOS*/
  }

  private static void removeProduct() {
    /*TODO muestra los zapatos que hay en el pedido actual
     *  pide al usuario el código del modelo de zapato que quiere eliminar
     *  elimina uno de los zapatos de ese modelo del pedido actual*/
  }

  private static void addProduct(Order currentOrder) {
    ShoeModel[] stockDisponible = Arrays.stream(DB_CONTROLLER.getStock())
        .filter(modelo -> modelo.getAvailableUnits() > 0).toArray(ShoeModel[]::new);
    ViewCreator.pintarTabla(stockDisponible);
    boolean esValido = false;
    while (!esValido) {
      String entradaUsuario = ViewCreator.pedirEntradaTexto(
          "Introduzca el código del modelo de zapato que quiere añadir al pedido:");
      if (Utils.isIntString(entradaUsuario)) {
        int id = Integer.parseInt(entradaUsuario);
        ShoeModel modeloElegido = Arrays.stream(stockDisponible)
            .filter(modelo -> modelo.getID() == id).findFirst().orElse(new ShoeModel());
        if (modeloElegido.getID() > 0) {
          Shoe zapato = buscarZapato(currentOrder, modeloElegido);
          if (zapato.getModelId() < 0) {
            zapato.setModelId(modeloElegido.getID());
            currentOrder.getProductList().add(zapato);
            ViewCreator.mostrarExito(
                "Se ha añadido una unidad del zapato modelo " + modeloElegido.getDescription()
                    + " al pedido.");
            esValido = true;
          } else {
            if (modeloElegido.getAvailableUnits() > zapato.getDesiredUnits()) {
              zapato.setDesiredUnits(zapato.getDesiredUnits() + 1);
              ViewCreator.mostrarExito(
                  "Se ha añadido una unidad del zapato modelo " + modeloElegido.getDescription()
                      + " al pedido.");
              esValido = true;
            } else {
              ViewCreator.mostrarError("No se ha podido añadir otra unidad del zapato modelo "
                  + modeloElegido.getDescription() + " al pedido, no hay stock suficiente");
            }
          }
        } else {
          ViewCreator.mostrarError("Debe introducir un código de un modelo que tenga stock.");
        }
      } else {
        ViewCreator.mostrarError(
            "Debe introducir un código valido. Los códigos son enteros mayores de 0.");
      }
    }
    ViewCreator.pintarTabla(currentOrder, stockDisponible);
    ViewCreator.waitEnter();
  }

  /**
   * Busca en la lista de productos de un pedido un modelo de zapato. Si lo encuentra devuelve ese
   * zapato de la lista, si no devuelve un zapato nuevo con modelID = -1
   *
   * @param order         pedido en el que buscar el modelo de zapato
   * @param modeloBuscado
   * @return si se encuentra el modelo se devuelve el Shoe con ese id si no un Shoe con ID -1
   */
  private static Shoe buscarZapato(Order order, ShoeModel modeloBuscado) {
    return order.getProductList().stream()
        .filter(model -> model.getModelId() == modeloBuscado.getID()).findFirst()
        .orElse(new Shoe());
  }

  /**
   * Muestra al usuario los clientes que hay en el sistema. Le pide que proporcione el código id de
   * uno de ellos o si desea crear uno nuevo. Si desea crear uno nuevo devuelve un Client con ID =
   * -1. Si no devuelve el Client con ese ID.
   *
   * @return un Client con ID -1 si se desea crear uno nuevo o el Client con ese ID de la bd
   */
  private static Client identificarCliente() {
    ViewCreator.pintarTabla(DB_CONTROLLER.getClients());
    Client cliente = new Client();

    String entradaUsuario;
    while (true) {
      entradaUsuario = ViewCreator.pedirEntradaTexto(
          "Por favor introduzca el código del cliente o NUEVO si desea dar de alta a un cliente nuevo");
      if (entradaUsuario.equalsIgnoreCase("NUEVO")) {
        return cliente;
      }
      if (Utils.isIntString(entradaUsuario)) {
        int id = Integer.parseInt(entradaUsuario);
        cliente = Arrays.stream(DB_CONTROLLER.getClients()).filter(row -> row.getID() == id)
            .findFirst().orElse(new Client());
        if (cliente.getID() > 0) {
          return cliente;
        } else {
          ViewCreator.mostrarError("El código introducido no coincide con el de ningún cliente.");
        }
      } else {
        ViewCreator.mostrarError(
            "Debe introducir un código valido o NUEVO. Los códigos son enteros mayores de 0.");
      }
    }
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
    String consoleInput;

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
    String consoleInput;
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
    String consoleInput;
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
    String entradaUsuario;
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
