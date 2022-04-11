package es.everis.programacion.act35;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

public class Calculadora {

  //Colores para los mensajes al usuario
  static final String ANSI_RESET = "\u001B[0m";
  static final String ANSI_RED = "\u001B[31m";
  static final String ANSI_GREEN = "\u001B[32m";

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

//      Variables pare el control de la navegacion
    Menu menuActual = Menu.MENU_PRINCIPAL;
    SeleccionMenu opcionSeleccionada = new SeleccionMenu("", false);
    boolean haTerminado = false;

//      Variable para el resultado de la operacion
    String resultado = "";

    //Bucle principal del programa
    while (!haTerminado) {
      //comprobamos si se desea terminar el programa
      if (opcionSeleccionada.opcion.equals("X")) {
        haTerminado = true;
        //comprobamos si se desea volver atras
      } else if (opcionSeleccionada.opcion.equals("V")) {
        menuActual = Menu.MENU_PRINCIPAL;
        opcionSeleccionada.opcion = "";
        opcionSeleccionada.hayError = false;
      } else {
        //Pintamos el menu actual
        pintarMenu(menuActual.getListaOpciones(), opcionSeleccionada.hayError);
        //Seleccionamos una opcion valida del menu actual
        opcionSeleccionada = esSeleccionValida(menuActual.getListaOpciones(),
            input.nextLine().trim().toUpperCase());

        if (!opcionSeleccionada.hayError) {
          //Segun la opcion seleccionada hacemos la operacion que toque
          switch (menuActual) {
            //El menu principal no tiene operaciones, opciones para cambiar de menu
            case MENU_PRINCIPAL -> {
              switch (opcionSeleccionada.opcion) {
                case "1" -> menuActual = Menu.MENU_BASICO;
                case "2" -> menuActual = Menu.MENU_AVANZADO;
                case "3" -> menuActual = Menu.MENU_TRIGONO;
                default -> {
                }// No se ejecutara dado que las opciones ya estan validadas
              }
            }
            case MENU_BASICO -> {
              switch (opcionSeleccionada.opcion) {
                //SUMA
                case "1" -> resultado = resolverOperacion(input, Operacion.SUMA);
                //RESTA
                case "2" -> resultado = resolverOperacion(input, Operacion.RESTA);
                //PRODUCTO
                case "3" -> resultado = resolverOperacion(input, Operacion.PRODUCTO);
                //DIVISION
                case "4" -> resultado = resolverOperacion(input, Operacion.DIVISION);
                //MODULO
                case "5" -> resultado = resolverOperacion(input, Operacion.MODULO);
                default -> {
                }// No se ejecutara dado que las opciones ya estan validadas
              }
            }
            case MENU_AVANZADO -> {
              switch (opcionSeleccionada.opcion) {
                //VALOR ABSOLUTO
                case "1" -> resultado = resolverOperacion(input, Operacion.VALOR_ABSOLUTO);
                //RAIZ CUADRADA
                case "2" -> resultado = resolverOperacion(input, Operacion.RAIZ_CUADRADA);
                //RAIZ CUBICA
                case "3" -> resultado = resolverOperacion(input, Operacion.RAIZ_CUBICA);
                //POTENCIA
                case "4" -> resultado = resolverOperacion(input, Operacion.POTENCIA);
                //POTENCIA DE e
                case "5" -> resultado = resolverOperacion(input, Operacion.POTENCIA_E);
                //LOGARITMO BASE 10
                case "6" -> resultado = resolverOperacion(input, Operacion.LOG10);
                //LOGARITMO NEPERIANO
                case "7" -> resultado = resolverOperacion(input, Operacion.LN);
                //INVERSO
                case "8" -> resultado = resolverOperacion(input, Operacion.INVERSA);
                //FACTORIAL
                case "9" -> resultado = resolverOperacion(input, Operacion.FACTORIAL);
                default -> {
                }// No se ejecutara dado que las opciones ya estan validadas
              }
            }
            case MENU_TRIGONO -> {
              switch (opcionSeleccionada.opcion) {
                //SENO
                case "1" -> resultado = resolverOperacion(input, Operacion.SENO);
                //COSENO
                case "2" -> resultado = resolverOperacion(input, Operacion.COSENO);
                //TANGENTE
                case "3" -> resultado = resolverOperacion(input, Operacion.TANGENTE);
                default -> {
                }// No se ejecutara dado que las opciones ya estan validadas
              }
            }
            default -> {
            }// No se ejecutara dado que las opciones ya estan validadas
          }
        }
        //Si tenemos un resultado se pinta por pantalla
        if (resultado.length() > 0) {
          pintarResultado(resultado);
          //Nos movemos al menu de salida y reiniciar estados
          menuActual = Menu.MENU_FINAL;
          resultado = "";
          opcionSeleccionada.opcion = "";
          opcionSeleccionada.hayError = false;
        }

      }
    }

    System.out.println("Sesion terminada. Hasta otra!");

  }

  /**
   * Dando un parametro array de String[][] imprime por pantalla un menu del cual el valor [0][0] es
   * el titulo, el valor de [0][1] el mensaje al usuario y cada pareja [i][j] restante un par
   * "codigo": "opcion del menu". Ademas, si el parametro error es true, añade un aviso de error por
   * pantalla
   *
   * @param contenido contiene un array bidimesional con los contenidos del menu
   * @param error     indica si es necesario imprimir un mensaje de error
   */
  static void pintarMenu(String[][] contenido, boolean error) {
    System.out.println(contenido[0][0]);
    System.out.println("--------------");
    if (error) {
      System.out.println(
          ANSI_RED + "*** ATENCION El valor introducido no es una opcion del menu ***"
              + ANSI_RESET);
    }
    System.out.println(contenido[0][1]);
    System.out.println("--------------");
    for (int i = 1; i < contenido.length; i++) {
      for (int j = 0; j < contenido[i].length; j++) {
        System.out.print(contenido[i][j] + ". ");
      }
      System.out.println();
    }
    System.out.println("--------------");
  }

  /**
   * Dada una cadena con el resultado de la operacion, le da formato y lo imprime por pantalla
   *
   * @param resultado la cadena con el resultado de la operacion
   */
  static void pintarResultado(String resultado) {
    System.out.println("--------------");
    System.out.println(ANSI_GREEN + "El resultado de su operacion es: " + (new BigDecimal(resultado,
        MathContext.DECIMAL64)).setScale(4, RoundingMode.HALF_UP) + ANSI_RESET);
    System.out.println("--------------");
  }

  /**
   * Dando un parametro array de String[][] comprueba si en los codigos de las parejas
   * "codigo":"opcion del menu" relevantes (las de [i][j] para i>0) coinciden con la entrada del
   * usuario. Si hay coincidencia devuelve un objeto SeleccionMenu con la entrada en el atributo
   * opcion y hayError con valor false. Si no la hay a opcion se le asigna una cadena vacia y
   * hayError sera true.
   *
   * @param menu           contiene un array bidimesional con los contenidos del menu
   * @param entradaUsuario la entrada por teclado del usuario
   * @return objeto con la entrada y true o false segun hay o no error
   */
  static SeleccionMenu esSeleccionValida(String[][] menu, String entradaUsuario) {
    SeleccionMenu resultado = new SeleccionMenu("", true);
    for (int i = 1; i < menu.length; i++) {
      if (entradaUsuario.equals(menu[i][0])) {
        resultado.opcion = menu[i][0];
        resultado.hayError = false;
        return resultado;
      }
    }
    return resultado;
  }

  /**
   * Pide al usuario un operando usando un mensaje dado como parametro. Hace una verificacion de la
   * entrada segun el parametro tipoDeVerificacion:
   * * <ul>
   * <li>"!n" valida que es un numero Integer mayor o igual que 0 y menor que 21</li>
   * <li>">0" valida que es un numero Double mayor que 0</li>
   * <li>"!0" valida que es un numero Double distinto de 0</li>
   * <li>">=0" valida que es un numero Double mayor o igual que 0</li>
   * <li>Cualquier otra cadena valida que es un numero Double.</li>
   * </u>
   * Si la verificacion no es correcta imprime un mensaje de error.
   * Finalmente devuelve un objeto Operando con la entrada del usuario y un booleano
   * de control a true si se valido con exito y a false si no.
   *
   * @param entradaTeclado     objeto Scanner que toma los datos del usuario
   * @param mensajeAlUsuario   mensaje que se da al usuario para pedir la entrada de datos
   * @param tipoDeVerificacion codigo de la verificacion que se quiere realizar
   * @param mensajeError       mensaje que se muestra si falla la verificacion
   * @return objeto Operando con la entrada del usuario y un booleano de control de verificacion
   */
  static Operando pedirOperando(Scanner entradaTeclado, String mensajeAlUsuario,
      String tipoDeVerificacion, String mensajeError) {
    System.out.println(mensajeAlUsuario);
    Operando nOper = validarOperando(entradaTeclado.nextLine(), tipoDeVerificacion);
    if (!nOper.esValido) {
      System.err.println(mensajeError);
    }
    return (nOper);
  }

  /**
   * Dada una cadena de caracteres. Devuelve true si es un numero Double valido y false si no
   *
   * @param str cadena a validar
   * @return true o false segun valide o no la cadena
   */
  static boolean esUnDouble(String str) {
    try {
      Double.parseDouble(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Dada una cadena de caracteres. Devuelve true si es un numero Integer valido y false si no
   *
   * @param str cadena a validar
   * @return true o false segun valide o no la cadena
   */
  static boolean esUnEntero(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Dada una cadena de texto. Valida que es un operando valido. El criterio de validacion depende
   * del string tipoValidacion:
   * <ul>
   *   <li>"!n" valida que es un numero Integer mayor o igual que 0 y menor que 21</li>
   *  <li>">0" valida que es un numero Double mayor que 0</li>
   *  <li>"!0" valida que es un numero Double distinto de 0</li>
   *  <li>">=0" valida que es un numero Double mayor o igual que 0</li>
   * <li>Cualquier otra cadena valida que es un numero Double.</li>
   * </u>
   *
   * @param entradaUsuario cadena de texto con la entrada por teclado del usuario
   * @param tipoValidacion el tipo de verificacion que se desea
   * @return un objeto Operando con el valor numerico de la cadena y un booleano de validacion
   */
  static Operando validarOperando(String entradaUsuario, String tipoValidacion) {
    String entradaUsuarioSan = entradaUsuario.trim();
    double numeroQueValidar;
//      Validacion para entero >= 0
    if (tipoValidacion.equals("!n") && esUnEntero(entradaUsuarioSan)) {
      numeroQueValidar = Double.parseDouble(entradaUsuarioSan);
      //el factorial mas grande que cabe en un long es !20
      if (numeroQueValidar >= 0 && numeroQueValidar <= 20) {
        return (new Operando(numeroQueValidar, true));
      } else {
        return (new Operando(numeroQueValidar, false));
      }
//      Validacion para valores Double
    } else if (esUnDouble(entradaUsuarioSan)) {
      switch (tipoValidacion) {
        case ">0" -> {
          numeroQueValidar = Double.parseDouble(entradaUsuarioSan);
          if (numeroQueValidar > 0) {
            return (new Operando(numeroQueValidar, true));
          } else {
            return (new Operando(numeroQueValidar, false));
          }
        }
        case "!0" -> {
          numeroQueValidar = Double.parseDouble(entradaUsuarioSan);
          if (numeroQueValidar != 0) {
            return (new Operando(numeroQueValidar, true));
          } else {
            return (new Operando(numeroQueValidar, false));
          }
        }
        case ">=0" -> {
          numeroQueValidar = Double.parseDouble(entradaUsuarioSan);
          if (numeroQueValidar >= 0) {
            return (new Operando(numeroQueValidar, true));
          } else {
            return (new Operando(numeroQueValidar, false));
          }
        }
        default -> {
          return (new Operando(Double.parseDouble(entradaUsuarioSan), true));
        }
      }
//      Devolver error cuando no es un valor trasladable a numero
    } else {
      return (new Operando(0, false));
    }
  }

  /**
   * Pide al usuario los operandos necesarios para realizar una operación dada y devuelve el
   * resultado de esta operacion
   *
   * @param input     Objeto scanner para la entrada del usuario
   * @param operacion Objeto con la operacion que se desea realizar
   * @return resutado de la operacion
   */
  static String resolverOperacion(Scanner input, Operacion operacion) {
    String resultado = "";
    Operando primerOperando = new Operando(0, false);
    //Si la operaciones es de un operando
    if (operacion.esUnaria()) {
      //Controlamos la entrada
      while (!primerOperando.esValido) {
        primerOperando = pedirOperando(input, "Por favor introduzca el operando",
            operacion.getSlugDeValidacion(), operacion.getMensajeError());
      }
      //Realizamos la operacion
      switch (operacion) {
        case VALOR_ABSOLUTO -> resultado = String.valueOf(Math.abs(primerOperando.operando));
        case RAIZ_CUADRADA -> resultado = String.valueOf(Math.sqrt(primerOperando.operando));
        case RAIZ_CUBICA -> resultado = String.valueOf(Math.cbrt(primerOperando.operando));
        case POTENCIA_E -> resultado = String.valueOf(Math.exp(primerOperando.operando));
        case LOG10 -> resultado = String.valueOf(Math.log10(primerOperando.operando));
        case LN -> resultado = String.valueOf(Math.log(primerOperando.operando));
        case INVERSA -> resultado = String.valueOf(1 / primerOperando.operando);
        case FACTORIAL -> resultado = String.valueOf(factorial((long) primerOperando.operando));
        case SENO -> resultado = String.valueOf(Math.sin(primerOperando.operando));
        case COSENO -> resultado = String.valueOf(Math.cos(primerOperando.operando));
        case TANGENTE -> resultado = String.valueOf(Math.tan(primerOperando.operando));
        default -> {
        }
      }
      //Si la operacion es de dos operandos
    } else {
      Operando segundoOperando = new Operando(0, false);
      //Controlamos las entradas
      while (!primerOperando.esValido) {
        //Para este primer operando nos se realizan comprobaciones adicionales a ser del tipo correcto
        primerOperando = pedirOperando(input, "Por favor introduzca el primer operando",
            "", operacion.getMensajeError());
      }
      while (!segundoOperando.esValido) {
        segundoOperando = pedirOperando(input, "Por favor introduzca el segundo operando",
            operacion.getSlugDeValidacion(), operacion.getMensajeError());
      }
      //Realizamos la operacion
      switch (operacion) {
        case SUMA -> resultado = String.valueOf(primerOperando.operando + segundoOperando.operando);
        case RESTA -> resultado = String.valueOf(
            primerOperando.operando - segundoOperando.operando);
        case PRODUCTO -> resultado = String.valueOf(
            primerOperando.operando * segundoOperando.operando);
        case DIVISION -> resultado = String.valueOf(
            primerOperando.operando / segundoOperando.operando);
        case MODULO -> resultado = String.valueOf(
            primerOperando.operando % segundoOperando.operando);
        case POTENCIA -> resultado = String.valueOf(Math.pow(primerOperando.operando,
            segundoOperando.operando));
        default -> {
        }
      }
    }
    return resultado;
  }

  /**
   * Devuelve un valor long que es n!. ¡Cuidado! El factorial maximo que admite un long es 20!
   *
   * @param n entero del que se quiere el factorial
   * @return factorial de n
   */
  static long factorial(long n) {
    if (n == 0) {
      return 1;
    } else {
      return (n * factorial(n - 1));
    }
  }

  //Valores para la navegacion "MENU_PRINCIPAL"/"MENU_BASICO"/"MENU_AVANZADO"/"MENU_TRIGONO"/"MENU_FINAL" segun en que menu este el usuario
  enum Menu {
    MENU_PRINCIPAL(new String[][]{
        {"MENU PRINCIPAL",
            "Por favor seleccione el numero de la accion que desea realizar o X para terminar"},
        {"1", "ARITMETICA BASICA"},
        {"2", "ARITMETICA AVANZADA"},
        {"3", "FUNCIONES TRIGONOMETRICAS"},
        {"X", "TERMINAR"}
    }),
    MENU_BASICO(new String[][]{
        {"ARITMETICA BASICA",
            "Por favor seleccione el numero de la accion que desea realizar, V para volver al menu anterior o X para terminar"},
        {"1", "SUMA"},
        {"2", "RESTA"},
        {"3", "PRODUCTO"},
        {"4", "DIVISION"},
        {"5", "MODULO"},
        {"V", "VOLVER AL MENU PRINCIPAL"},
        {"X", "TERMINAR"}
    }),
    MENU_AVANZADO(new String[][]{
        {"ARITMETICA AVANZADA",
            "Por favor seleccione el numero de la accion que desea realizar, V para volver al menu anterior o X para terminar"},
        {"1", "VALOR ABSOLUTO"},
        {"2", "RAIZ CUADRADA"},
        {"3", "RAIZ CUBICA"},
        {"4", "POTENCIA"},
        {"5", "POTENCIA DE e"},
        {"6", "LOGARITMO BASE 10"},
        {"7", "LOGARITMO NEPERIANO"},
        {"8", "INVERSA"},
        {"9", "FACTORIAL"},
        {"V", "VOLVER AL MENU PRINCIPAL"},
        {"X", "TERMINAR"}
    }),
    MENU_TRIGONO(new String[][]{
        {"FUNCIONES TRIGONOMETRICAS",
            "Por favor seleccione el numero de la accion que desea realizar, V para volver al menu anterior o X para terminar"},
        {"1", "SENO"},
        {"2", "COSENO"},
        {"3", "TANGENTE"},
        {"V", "VOLVER AL MENU PRINCIPAL"},
        {"X", "TERMINAR"}
    }),
    MENU_FINAL(new String[][]{
        {"MENU FINAL", "Desea continuar?"},
        {"V", "VOLVER AL MENU PRINCIPAL"},
        {"X", "TERMINAR"}
    });

    private final String[][] listaOpciones;

    Menu(String[][] listaOpciones) {
      this.listaOpciones = listaOpciones;
    }

    public String[][] getListaOpciones() {
      return listaOpciones;
    }
  }

  //Valores que describen las operaciones posibles. Atendiendo al numero de operandos, la verificacion de la
  // entrada de datos y el posible mensaje de error al usuario
  enum Operacion {
    SUMA(false, "Debe introducir un numero", ""),
    RESTA(false, "Debe introducir un numero", ""),
    PRODUCTO(false, "Debe introducir un numero", ""),
    DIVISION(false, "Debe introducir un numero, recuerde que el divisor no puede ser 0", "!0"),
    MODULO(false, "Debe introducir un numero, recuerde que el divisor debe ser mayor que 0", "!0"),
    POTENCIA(false, "Debe introducir un numero", ""),
    VALOR_ABSOLUTO(true, "Debe introducir un numero", ""),
    RAIZ_CUADRADA(true, "Debe introducir un numero mayor o igual que 0", ">=0"),
    RAIZ_CUBICA(true, "Debe introducir un numero", ""),
    POTENCIA_E(true, "Debe introducir un numero", ""),
    LOG10(true, "Debe introducir un numero mayor que 0", ">0"),
    LN(true, "Debe introducir un numero mayor que 0", ">0"),
    INVERSA(true, "Debe introducir un numero, recuerde que el divisor no puede ser 0", "!0"),
    FACTORIAL(true, "Debe introducir un numero entre 0 y 20", "!n"),
    SENO(true, "Debe introducir un numero", ""),
    COSENO(true, "Debe introducir un numero", ""),
    TANGENTE(true, "Debe introducir un numero", "");

    private final boolean unaria;
    private final String mensajeError;
    private final String slugDeValidacion;

    Operacion(boolean unaria, String mensajeError, String slugDeValidacion) {
      this.unaria = unaria;
      this.mensajeError = mensajeError;
      this.slugDeValidacion = slugDeValidacion;
    }

    public String getMensajeError() {
      return mensajeError;
    }

    public String getSlugDeValidacion() {
      return slugDeValidacion;
    }

    public boolean esUnaria() {
      return unaria;
    }

  }

  //Almacena un operando y si esta validado o no
  static class Operando {

    double operando;
    boolean esValido;

    public Operando(double operando, boolean esValido) {
      this.operando = operando;
      this.esValido = esValido;
    }
  }

  //Almacena la opcion que ha seleccionado el usuario y si es valida o no
  static class SeleccionMenu {

    String opcion;
    boolean hayError;

    public SeleccionMenu(String opcion, boolean hayError) {
      this.opcion = opcion;
      this.hayError = hayError;
    }
  }
}
