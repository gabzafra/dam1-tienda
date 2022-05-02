package dam1.prog.tiendav2;

public class Utils {

  //Colores
  public static final String COLOR_WHITE = "\u001B[0m";
  public static final String COLOR_RED = "\u001B[31m";
  public static final String COLOR_GREEN = "\u001B[32m";

  /**
   * Dada una cadena comprueba si puede convertirse en entero
   *
   * @param number cadena con un entero
   * @return true si puede convertirse, false si no
   */
  public static boolean isIntString(String number) {
    try {
      Integer.parseInt(number);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  /**
   * Dada una cadena comprueba si puede convertirse en double
   *
   * @param number cadena con un double
   * @return true si puede convertirse, false si no
   */
  public static boolean isDoubleString(String number) {
    try {
      Double.parseDouble(number);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
