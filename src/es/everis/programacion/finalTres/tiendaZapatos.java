package es.everis.programacion.finalTres;

import java.util.Scanner;

public class tiendaZapatos {
    public static void main(String[] args) {
        String sandalias = "", deportivo = "", vestir = "", entrada = "";
        boolean haTerminado = false, hayDescuento = false;
        int opcionSeleccionada;
        double precioFinal;
        Scanner input = new Scanner(System.in);
//        pintar mensaje intro
        System.out.println("Bienvenido a su TPV");
        while (!haTerminado) {
            System.out.println("Por favor elija el tipo de zapato a añadir a la cesta:");
            if (sandalias.length() > 0 || deportivo.length() > 0 || vestir.length() > 0) {
                pintarCabecera();
                pintarBloquePedido(sandalias);
                pintarBloquePedido(deportivo);
                pintarBloquePedido(vestir);
                System.out.println("+--------------------------------------------------------------------------+");
            }
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
        if (entrada.equalsIgnoreCase("S")) hayDescuento = true;


//      pintar factura
        pintarCabecera();
        pintarBloquePedido(sandalias);
        pintarBloquePedido(deportivo);
        pintarBloquePedido(vestir);
        System.out.println("+-----------------------------------------------------+--------------------+");
        System.out.println("|                                          SUBTOTAL   |");
        precioFinal = calcularPrecioTotal(sandalias) + calcularPrecioTotal(deportivo) + calcularPrecioTotal(vestir);
        System.out.printf("|%50.2f   |\n", precioFinal);
        if (hayDescuento) {
            precioFinal -= precioFinal * 8 / 100;
            System.out.println("|                        DESCUENTO CLIENTE HABITUAL   |");
            System.out.printf("|%50.2f   |\n", precioFinal);
        }
        System.out.println("|                                          IVA(21%)   |");
        System.out.printf("|%50.2f   |\n", precioFinal * 0.21);
        System.out.println("|                                             TOTAL   |");
        precioFinal += precioFinal * 0.21;
        System.out.printf("|%50.2f   |\n", precioFinal);
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Totales de paqueteria");
        System.out.println("Sandalias: " + calcularUnidadesTotales(sandalias));
        System.out.println("Zapato deportivo: " + calcularUnidadesTotales(deportivo));
        System.out.println("Zapato de vestir: " + calcularUnidadesTotales(vestir));
    }

    static String addProduct(String productos, String slug) {
        Scanner entrada = new Scanner(System.in);
        String modelo, listaActualizada;
        double precio;
        int cantidad;
        System.out.println("Introduzca el modelo de zapato, los nombres de los modelos no pueden contener el caracter" +
                " '|' :");
        modelo = slug + " - " + entrada.nextLine().trim().replace("|", "");
        System.out.println("Introduzca el precio del zapato :");
        precio = incluirMargen(entrada.nextDouble());
        System.out.println("Introduzca el cuantos zapatos del modelo " + modelo + " desea el cliente :");
        cantidad = entrada.nextInt();
        listaActualizada = productos + modelo + "|" + precio + "|" + cantidad + "|";
        return listaActualizada;
    }

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

    static void pintarCabecera(){
        System.out.println("+--------------------------------------------------------------------------+");
        System.out.println("| MODELO                                   UNIDADES   PRECIO     TOTAL     |");
        System.out.println("+--------------------------------------------------------------------------+");
    }

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
            System.out.printf("| %-40s %-10s %-10.2f %-10.2f|\n", modelo, unidades, precio,
                    (Integer.parseInt(unidades) * precio));
        }
    }

    static double incluirMargen(double precio) {
        return precio + precio * 0.55;
    }
}
