package dam1.prog.tiendav2.control;

import dam1.prog.tiendav2.Utils;
import dam1.prog.tiendav2.models.Client;
import dam1.prog.tiendav2.models.MockDB;
import dam1.prog.tiendav2.models.Order;
import dam1.prog.tiendav2.models.Shoe;
import dam1.prog.tiendav2.models.ShoeModel;
import java.util.Arrays;

public class DbController {

  private final MockDB db;

  public DbController() {
    this.db = new MockDB();
  }
  //Gestión de pedidos

  /**
   * Intenta crear un nuevo pedido de un cliente. El nuevo pedido tendrá como id del propietario el
   * id por parámetro y su estado inicial será OPEN.
   *
   * @param clientId propietario del nuevo pedido
   * @return nueva Order o Order con ID -1 si hubo error en la bd
   */
  public Order createOrder(int clientId) {
    Order order = new Order(clientId, "OPEN");
    return db.add(order);
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
      return db.add(newClient).getID() > 0;
    }
  }

  /**
   * Intenta eliminar un cliente de la base de datos
   *
   * @param clientId código Id del cliente
   * @return true si se elimina con éxito, false si no
   */
  public boolean removeClient(String clientId) {
    try {
      return db.deleteClient(Integer.parseInt(clientId));
    } catch (Exception e) {
      return false;
    }
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

  /**
   * Intenta añadir un nuevo modelo de zapato a la base de datos. Devuelve true si el proceso tiene
   * éxito y false si ya hay un zapato con esa descripción o si hay un error al intentar modificar
   * la base de datos
   *
   * @param newShoe nuevo model de zapato
   * @return true si se consigue añadir, false si hay algún problema
   */
  public boolean addShoeModel(ShoeModel newShoe) {
    boolean modelExists = Arrays.stream(db.getAllModels())
        .anyMatch(model -> model.getDescription().equals(newShoe.getDescription()));
    if (modelExists) {
      return false;
    } else {
      return db.add(newShoe).getID() > 0;
    }
  }

  /**
   * Dado un objeto ShoeModel con los nuevos valores intenta modificar su registro en la base de
   * datos
   *
   * @param modelo con los valores actualizado
   * @return modelo ya actualizado o un modelo con ID -1 si hubo un error
   */
  public ShoeModel updateShoeModel(ShoeModel modelo) {
    return db.updateModel(modelo);
  }


  /**
   * Intenta eliminar un modelo de zapato de la base de datos
   *
   * @param modelId código Id del modelo
   * @return true si se elimina con éxito, false si no
   */
  public boolean removeShoeModel(String modelId) {
    try {
      return db.deleteModel(Integer.parseInt(modelId));
    } catch (Exception e) {
      return false;
    }
  }

  public boolean increaseModelUnits() {
    return true;
  }

  public boolean decreaseModelUnits() {
    return true;
  }

}
