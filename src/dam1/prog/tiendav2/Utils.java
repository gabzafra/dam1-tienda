package dam1.prog.tiendav2;

import java.util.Scanner;

public class Utils {

  /**
   * Dada una cadena comprueba si puede convertirse en entero
   *
   * @param number cadena con un entero
   * @return true si puede convertirse, false si no
   */
  static boolean isIntString(String number) {
    try {
      Integer.parseInt(number);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}
