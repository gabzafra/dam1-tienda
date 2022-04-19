package es.everis.programacion.finalTres;

import java.util.Scanner;

public class Zapateria {

  public static void main(String[] args) {
    String sandalias = "", deportivo = "", vestir = "", entrada = "", nombreCliente;
    boolean haTerminado = false, hayDescuento = false;
    int opcionSeleccionada;

    Scanner input = new Scanner(System.in);
    //        pintar mensaje intro
    System.out.println("Bienvenido a su TPV");
    System.out.println("Por favor introduzca el nombre del cliente:");
    //        obtener nombre del cliente
    nombreCliente = input.nextLine();
    //        bucle principal
    while (!haTerminado) {
      System.out.println("Por favor elija el tipo de zapato a a\u0148adir a la cesta:");
      //            si ya tenemos algo en la cesta de la compra lo pintamos
      if (sandalias.length() > 0 || deportivo.length() > 0 || vestir.length() > 0) {
        pintarCabecera();
        pintarBloquePedido(sandalias);
        pintarBloquePedido(deportivo);
        pintarBloquePedido(vestir);
        System.out.println(
            "+--------------------------------------------------------------------------+");
      }
      //            pintamos el menu principal
      System.out.println("1. Sandalia");
      System.out.println("2. Zapato deportivo");
      System.out.println("3. Zapato de vestir");
      System.out.println("9. Realizar pago");
      opcionSeleccionada = input.nextInt();
      if (opcionSeleccionada == 9) {
        haTerminado = true;
      } else {
        if (opcionSeleccionada == 1) {
          sandalias = addProduct(sandalias, "Sandalia");
        } else if (opcionSeleccionada == 2) {
          deportivo = addProduct(deportivo, "Zapato deportivo");
        } else if (opcionSeleccionada == 3) {
          vestir = addProduct(vestir, "Zapato de vestir");
        } else {
          System.err.println("Debe elegir una opcion del menu!");
        }
      }
    }
    //      preguntar si hay que hacer descuento
    System.out.println("Desea aplicar un descuento de cliente habitual (S/N) ?");
    while (entrada.length() == 0) {
      entrada = input.nextLine();
    }
    if (entrada.equalsIgnoreCase("S")) {
      hayDescuento = true;
    }

    //      pintar factura
    pintarFactura(nombreCliente, sandalias, deportivo, vestir, hayDescuento);
  }

  /**
   * Añade un nuevo producto a la lista. Toma una lista de los productos de un tipo que hay en la
   * cesta y un descriptor. La lista de productos es una serie de modelo|precio|cantidad. Pregunta
   * al usuario por estos valores del nuevo producto y los añáde al final de la lista
   *
   * @param productos string separado por "|" donde almacenamos los datos de cada producto de un
   *                  tipo que hay en la cesta
   * @param slug      descriptor del tipo de producto a añádir antes de cada modelo
   * @return el string con la lista de procuctos con el nuevo añadido al final
   */
  static String addProduct(String productos, String slug) {
    Scanner entrada = new Scanner(System.in);
    String modelo, listaActualizada;
    double precio;
    int cantidad;
    System.out.println(
        "Introduzca el modelo de zapato, los nombres de los modelos no pueden contener el caracter"
            + " '|' :");
    modelo = slug + " - " + entrada.nextLine().trim().replace("|", "");
    System.out.println("Introduzca el precio del zapato :");
    precio = incluirMargen(entrada.nextDouble());
    System.out.println("Introduzca cuantos zapatos del modelo " + modelo + " desea el cliente :");
    cantidad = entrada.nextInt();
    listaActualizada = productos + modelo + "|" + precio + "|" + cantidad + "|";
    return listaActualizada;
  }

  /**
   * Toma una de las listas de los productos en la cesta y suma su coste total
   *
   * @param zapatos lista formateada de un tipo de zapatos
   * @return precio de todos los zapatos de la lista
   */
  static double calcularPrecioTotal(String zapatos) {
    String listaZapatos = zapatos;
    double costeTotal = 0, precio;
    while (listaZapatos.length() > 0) {
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      precio = Double.parseDouble(listaZapatos.substring(0, listaZapatos.indexOf("|")));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      costeTotal += precio * Integer.parseInt(listaZapatos.substring(0, listaZapatos.indexOf("|")));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
    }
    return costeTotal;
  }

  /**
   * Toma una de las listas de los productos en la cesta y cuenta cuantos zapatos hay
   *
   * @param zapatos lista formateada de un tipo de zapatos
   * @return número de zapatos que hay en la lista
   */
  static int calcularUnidadesTotales(String zapatos) {
    String listaZapatos = zapatos;
    int unidades = 0;
    while (listaZapatos.length() > 0) {
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      unidades += Integer.parseInt(listaZapatos.substring(0, listaZapatos.indexOf("|")));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
    }
    return unidades;
  }

  /**
   * Pinta por pantalla una cabecera para la cesta de la compra
   */
  static void pintarCabecera() {
    System.out.println(
        "+--------------------------------------------------------------------------+");
    System.out.println(
        "| MODELO                                  UNIDADES       PRECIO     TOTAL  |");
    System.out.println(
        "+--------------------------------------------------------------------------+");
  }

  /**
   * Dada una lista de productos en un string formateado. Pinta un bloque de la cesta de la compra
   * con los productos de la lista.
   *
   * @param listaProductos lista formateada de un tipo de zapatos
   */
  static void pintarBloquePedido(String listaProductos) {
    String listaZapatos = listaProductos;
    String unidades, modelo;
    double precio;
    while (listaZapatos.length() > 0) {
      modelo = listaZapatos.substring(0, listaZapatos.indexOf("|"));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      precio = Double.parseDouble(listaZapatos.substring(0, listaZapatos.indexOf("|")));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      unidades = listaZapatos.substring(0, listaZapatos.indexOf("|"));
      listaZapatos = listaZapatos.substring(listaZapatos.indexOf("|") + 1);
      System.out.printf(
          "| %-40s%-10s%10.2f\u20ac %10.2f\u20ac|\n",
          modelo, unidades, precio, (Integer.parseInt(unidades) * precio));
    }
  }

  /**
   * Dadas las tres listas con los productos de la cesta, el nombre del cliente y si se debe aplicar
   * un descuento. Calcula y pinta por pantalla una factura compuesta de: - cesta de la compra -
   * subtotal de la compra - descuento si lo hay - subtotal con el descuento aplicado si lo hay -
   * iva aplicable - total con impuestos - instrucciones para la paqueteria con el número de cada
   * tipo de zapatos que hay en el pedido
   *
   * @param nombreCliente nombre del cliente
   * @param sandalias     lista de las sandalias que hay en el pedido
   * @param deportivo     lista del calzado deportivo que hay en el pedido
   * @param vestir        listo de los zapatos de vestir que hay en el pedido
   * @param hayDescuento  true si hay que aplicar un descuento, false si no
   */
  static void pintarFactura(
      String nombreCliente,
      String sandalias,
      String deportivo,
      String vestir,
      boolean hayDescuento) {

    double precioFinal =
        calcularPrecioTotal(sandalias)
            + calcularPrecioTotal(deportivo)
            + calcularPrecioTotal(vestir);
    //        pintamos la cesta
    pintarCabecera();
    pintarBloquePedido(sandalias);
    pintarBloquePedido(deportivo);
    pintarBloquePedido(vestir);
    System.out.println(
        "+-----------------------------------------------------+--------------------+");
    System.out.println("|                                           SUBTOTAL  |");
    //        subtotal antes de descuentos e impuestos
    System.out.printf("|%50.2f\u20ac  |\n", precioFinal);
    //        miramos si hay que aplicar un descuento
    if (hayDescuento) {
      System.out.println("|                     DESCUENTO CLIENTE HABITUAL(8%)  |");
      System.out.printf("|%50.2f\u20ac  |\n", precioFinal * 0.08);

      precioFinal -= precioFinal * 8 / 100;
      System.out.println("|                               PRECIO CON DESCUENTO  |");
      System.out.printf("|%50.2f\u20ac  |\n", precioFinal);
    }
    //        aplicamos el iva y damos el total
    System.out.println("|                                           IVA(21%)  |");
    System.out.printf("|%50.2f\u20ac  |\n", precioFinal * 0.21);
    System.out.println("|                                              TOTAL  |");

    precioFinal += precioFinal * 0.21;

    System.out.printf("|%50.2f\u20ac  |\n", precioFinal);
    //        calculamos cuantos zapatos de cada tipo hay en el pedido
    System.out.println("+-----------------------------------------------------+");
    System.out.println("Receptor del pedido: " + nombreCliente);
    System.out.println("Totales de paqueteria");
    System.out.println("Sandalias: " + calcularUnidadesTotales(sandalias));
    System.out.println("Zapato deportivo: " + calcularUnidadesTotales(deportivo));
    System.out.println("Zapato de vestir: " + calcularUnidadesTotales(vestir));
  }

  /**
   * Devuelve el precio suministrado con un margen del 55% aplicado
   *
   * @param precio el precio de un producto introducido por el vendedor
   * @return precio con un margen aplicado
   */
  static double incluirMargen(double precio) {
    return precio + precio * 0.55;
  }
}
