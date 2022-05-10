package dam1.prog.tiendav2.view;

import dam1.prog.tiendav2.Utils;
import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.models.Order;
import dam1.prog.tiendav2.models.Shoe;
import dam1.prog.tiendav2.models.ShoeModel;
import java.util.Arrays;
import java.util.Scanner;

public class ViewCreator {

  private static final Scanner INPUT = new Scanner(System.in);

  //SALIDAS

  /**
   * Pinta por pantalla un resumen de un pedido del usuario.
   *
   * @param pedido pedido actual
   * @param inventario inventario de la tienda
   */
  public static void pintarTabla(Order pedido, ShoeModel[] inventario) {
    ShoeModel[] rows = new ShoeModel[]{};
    for (Shoe zapato : pedido.getProductList()) {
      rows = Arrays.copyOf(rows, rows.length + 1);
      rows[rows.length - 1] = Arrays.stream(inventario)
          .filter(model -> model.getID() == zapato.getModelId())
          .findFirst()
          .map(model -> new ShoeModel(model.getID(), model.getDescription(), model.getStyle(),
              zapato.getDesiredUnits(),
              model.getPrice()))
          .get();
    }
    System.out.println("+--------+------------+----------------------+----------+-----------+");
    System.out.println("********************* RESUMEN DEL PEDIDO ****************************");
    pintarTabla(rows);
  }

  /**
   * Dado un array de los modelos del inventario imprime una tabla formateada de sus valores
   *
   * @param rows modelos a imprimir
   */
  public static void pintarTabla(ShoeModel[] rows) {
    String fiveColFormat = "| %-6.6s | %-10.10s | %-20.20s | %s%8.8s%s | %8.8s€ |\n";
    if (rows.length > 0) {
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      System.out.printf(fiveColFormat, "Código", "Estilo", "Modelo", "", "Unidades", "", "Precio ");
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      Arrays.stream(rows)
          .forEach(model -> {
            String colored = model.getAvailableUnits() == 0 ? Utils.COLOR_RED : Utils.COLOR_GREEN;
            System.out.printf(fiveColFormat, model.getID(), model.getStyle(), model.getDescription(),
            colored, model.getAvailableUnits(), Utils.COLOR_WHITE, model.getPrice());
            });
      System.out.println("+--------+------------+----------------------+----------+-----------+");
    } else {
      System.out.println(Utils.COLOR_RED + "No hay productos." + Utils.COLOR_WHITE);
    }
  }

  /**
   * Dado un array de los clientes imprime una tabla formateada de sus valores
   *
   * @param rows clientes a imprimir
   */
  public static void pintarTabla(Client[] rows) {
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
      System.out.println(Utils.COLOR_RED + "No hay clientes en el sistema" + Utils.COLOR_WHITE);
    }
  }

  /**
   * Pinta por consola el menu dado como argumento
   *
   * @param menu que se quiere pintar por consola
   */
  public static void pintarMenu(Menu menu) {
    System.out.println(menu.getTitle());
    System.out.println("---------------------------------------");
    for (MenuItem option : menu.getOptionList()
    ) {
      System.out.println(option.getOptionNumber() + ". " + option.getOptionLabel());
    }
    System.out.println("---------------------------------------");
  }

  /**
   * Imprime una factura de un pedido.
   *
   * @param order Pedido del cliente
   * @param inventario inventario de la tienda
   * @param iva impuestos
   * @param descuento si lo tiene el cliente
   */
  public static void pintarFactura(Order order, ShoeModel[] inventario, double iva,
      double descuento) {
    String sixColFormat = "| %-6.6s | %-10.10s | %-20.20s | %8.8s | %8.8s€ | %12.12s€ |\n";
    String twoColFormat = "| %-30.30s | %12.2f€ |\n";

    //Pedido completo
    System.out.println(
        "+--------+------------+----------------------+----------+-----------+---------------+");
    System.out.printf(sixColFormat, "Código", "Estilo", "Modelo", "Unidades", "Precio ",
        "Precio Total ");
    System.out.println(
        "+--------+------------+----------------------+----------+-----------+---------------+");

    double precioTotal = 0;
    for (Shoe producto : order.getProductList()) {
      ShoeModel modelo = Arrays.stream(inventario)
          .filter(model -> model.getID() == producto.getModelId())
          .findFirst()
          .get();
      double precio = producto.getDesiredUnits() * modelo.getPrice();
      precioTotal += precio;
      System.out.printf(sixColFormat, modelo.getID(), modelo.getStyle(), modelo.getDescription(),
          producto.getDesiredUnits(), modelo.getPrice(), precio);
    }
    System.out.println(
        "+--------+------------+----------+-----------+---+------+-----------+---------------+");
    //Totales
    System.out.printf(twoColFormat, "Subtotal:", precioTotal);
    if (descuento < 1) {
      System.out.printf(twoColFormat, "Descuento:", precioTotal * descuento);
      precioTotal -= precioTotal * descuento;
      System.out.printf(twoColFormat, "Precio con descuento:", precioTotal);
    }
    System.out.printf(twoColFormat, "IVA:", precioTotal * iva);
    precioTotal += precioTotal * iva;
    System.out.printf(twoColFormat, "PRECIO FINAL:", precioTotal);
    System.out.println("+--------------------------------+---------------+");
  }

  /**
   * Muestra en rojo un mensaje de error pasado por parámetro
   *
   * @param mensaje mensaje para el usuario
   */
  public static void mostrarError(String mensaje) {
    System.out.println(Utils.COLOR_RED + mensaje + Utils.COLOR_WHITE);
  }

  /**
   * Muestra en verde un mensaje de éxito pasado por parámetro
   *
   * @param mensaje mensaje para el usuario
   */
  public static void mostrarExito(String mensaje) {
    System.out.println(Utils.COLOR_GREEN + mensaje + Utils.COLOR_WHITE);
  }

  /**
   * Muestra en blanco un mensaje pasado por parámetro
   *
   * @param mensaje mensaje para el usuario
   */
  public static void mostraMensaje(String mensaje) {
    System.out.println(mensaje);
  }

  //ENTRADAS

  /**
   * Muestra al usuario con un mensaje dado por parámetro y a continuación espera que la confirme
   * escribiendo "s" o  "n". Si escribe "s" devuelve true, si escribe "n" false y con otra entrada
   * lanza un mensaje de error y solicita de nuevo la entrada al usuario
   *
   * @param mensaje mensaje para el usuario
   * @return true si se confirma false si no
   */
  public static boolean pedirConfirmacion(String mensaje) {
    boolean entradaValida = false;
    String userInput = "";
    while (!entradaValida) {
      System.out.println(mensaje);
      userInput = INPUT.nextLine().trim();
      entradaValida = userInput.equalsIgnoreCase("s") || userInput.equalsIgnoreCase("n");
      if (!entradaValida) {
        mostrarError("Debe introducir s/n");
      }
    }
    return userInput.equalsIgnoreCase("s");
  }

  /**
   * Muestra al usuario con un mensaje dado por parámetro y a continuación espera una entrada de
   * texto. Comprueba que esta tiene contenido sin contar espacios laterales. Y si no la vuelve a
   * solicitar. Si tiene contenido la devuelve.
   *
   * @param mensaje mensaje para el usuario
   * @return entrada saneada
   */
  public static String pedirEntradaTexto(String mensaje) {
    String userInput = "";
    boolean entradaValida = false;
    while (!entradaValida) {
      System.out.println(mensaje);
      userInput = INPUT.nextLine().trim();
      entradaValida = userInput.length() > 0;
      if (!entradaValida) {
        mostrarError("Debe escribir algo");
      }
    }
    return userInput;
  }

  /**
   * Pide al usuario que pulse Enter para continuar la ejecución
   */
  public static void waitEnter() {
    System.out.println("Pulse ENTER para continuar");
    INPUT.nextLine();
  }
}
