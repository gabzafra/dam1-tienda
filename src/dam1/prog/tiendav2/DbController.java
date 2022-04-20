package dam1.prog.tiendav2;

import java.util.Arrays;
import java.util.stream.Stream;

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
  public Client[] getClients() {
    return db.getAllClients();
  }

  /**
   * Intenta añadir un nuevo cliente a la base de datos.
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
