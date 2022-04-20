package dam1.prog.tiendav2;

import java.util.Arrays;

public class DbController {

  private MockDB db;

  public DbController() {
    this.db = new MockDB();
  }

  //Gestión de pedidos
  public boolean createOrder() {
    return true;
  }

  public boolean cancelOrder() {
    return true;
  }

  public boolean addShoeToOrder() {
    return true;
  }

  public boolean removeShoeFromOrder() {
    return true;
  }

  public boolean closeAnOrder() {
    return true;
  }

  //Gestión de clientes

  /**
   * Devuelve un array con todos los clientes dados de alta en el sistema
   *
   * @return todos los clientes del sistema
   */
  public Client[] getClients() {
    return db.getAllClients();
  }

  /**
   * Intenta añadir un nuevo cliente a la base de datos. Devuelve true si el proceso tiene éxito y
   * false si el usuario ya existe o si hay un error al intentar modificar la base de datos
   *
   * @param newClient nuevo cliente
   * @return true si se consigue añadir, false si hay algún problema
   */
  public boolean addNewClient(Client newClient) {
    boolean clientExists = Arrays.stream(db.getAllClients())
        .anyMatch(client -> client.getFullName().equals(newClient.getFullName()));
    if (clientExists) {
      return false;
    } else {
      if (db.addClient(newClient).getID() > 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  public boolean removeClient() {
    return true;
  }

  //Gestión de inventario

  /**
   * Devuelve un array con los datos de todos los modelos de zapatos que hay en la tienda
   *
   * @return el inventario completo
   */
  public ShoeModel[] getStock() {
    return db.getAllModels();
  }

  public boolean addShoeModel() {
    return true;
  }

  public boolean removeShoeModel() {
    return true;
  }

  public boolean increaseModelUnits() {
    return true;
  }

  public boolean decreaseModelUnits() {
    return true;
  }

}
