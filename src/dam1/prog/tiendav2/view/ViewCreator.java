package dam1.prog.tiendav2.view;

import dam1.prog.tiendav2.Utils;
import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.Menu;
import dam1.prog.tiendav2.models.MenuItem;
import dam1.prog.tiendav2.models.ShoeModel;
import java.util.Arrays;
import java.util.Scanner;

public class ViewCreator {

  /**
   * Dado un array de los modelos del inventario imprime una tabla formateada de sus valores
   *
   * @param rows  modelos a imprimir
   */
  public static void pintarTabla( ShoeModel[] rows) {
    String fiveColFormat = "| %-6.6s | %-10.10s | %-20.20s | %s%8.8s%s | %8.8s€ |\n";
    if (rows.length > 0) {
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      System.out.printf(fiveColFormat, "Código", "Estilo", "Modelo", "", "Unidades", "", "Precio ");
      System.out.println("+--------+------------+----------------------+----------+-----------+");
      Arrays.stream(rows).forEach(model -> {
        String colored = model.getAvailableUnits() == 0 ? Utils.COLOR_RED : Utils.COLOR_GREEN;
        System.out.printf(fiveColFormat, model.getID(), model.getStyle(), model.getDescription(),
            colored,
            model.getAvailableUnits(), Utils.COLOR_WHITE, model.getPrice());
      });
      System.out.println("+--------+------------+----------------------+----------+-----------+");
    } else {
      System.out.println(Utils.COLOR_RED + "No hay productos en el inventario" + Utils.COLOR_WHITE);
    }
  }

  /**
   * Dado un array de los clientes imprime una tabla formateada de sus valores
   *
   * @param rows  clientes a imprimir
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
}
