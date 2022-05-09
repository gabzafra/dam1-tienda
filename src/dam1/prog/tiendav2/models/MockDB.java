package dam1.prog.tiendav2.models;


import dam1.prog.login06.Usuario;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MockDB {

  private Client[] clientsTable;
  private Order[] ordersTable;
  private ShoeModel[] modelsTable;

  public MockDB() {
    this.clientsTable = new Client[]{
        new Client(1, "Adam", true),
        new Client(2, "Betty", true),
        new Client(3, "Charlie", true),
        new Client(4, "Diane", false),
        new Client(5, "Ettore", false),
        new Client(6, "Fiona", true),
    };
    this.ordersTable = new Order[]{
        new Order(1, 3, "PAGADO",
            new ArrayList<>(
                List.of(
                    new Shoe(3, 2)
                )
            )
        ),
        new Order(2, 5, "PAGADO",
            new ArrayList<>(
                List.of(
                    new Shoe(3, 1),
                    new Shoe(2, 1),
                    new Shoe(7, 4)
                )
            )
        ),
        new Order(3, 2, "PAGADO",
            new ArrayList<>(
                List.of(
                    new Shoe(1, 1)
                )
            )
        ),
        new Order(4, 1, "CANCELADO",
            new ArrayList<>(
                List.of(
                    new Shoe(3, 1),
                    new Shoe(11, 2)
                )
            )
        )
    };
    this.modelsTable = new ShoeModel[]{
        new ShoeModel(1, "CLARKS 88127", "De Vestir", 3, 79.95),
        new ShoeModel(2, "FLUCHOS 74138", "De Vestir", 5, 99.95),
        new ShoeModel(3, "MOLINA 59256", "De Vestir", 0, 57.95),
        new ShoeModel(4, "CALLAGHAN 96163", "De Vestir", 10, 109.95),
        new ShoeModel(5, "ALPE 97622", "Sandalia", 23, 89.95),
        new ShoeModel(6, "FLUCHOS 97805", "Sandalia", 12, 84.95),
        new ShoeModel(7, "PITILLOS 98544", "Sandalia", 1, 69.95),
        new ShoeModel(8, "PITILLOS 98556", "Sandalia", 0, 64.95),
        new ShoeModel(9, "ADIDAS 95920", "Deportivo", 6, 99.95),
        new ShoeModel(10, "MÁS8000 97261", "Deportivo", 9, 84.95),
        new ShoeModel(11, "CONVERSE 98731", "Deportivo", 11, 69.95),
        new ShoeModel(12, "NIKE 96085", "Deportivo", 5, 69.95)
    };
  }

  //CREATE

  /**
   * Dados los datos de un nuevo cliente lo añade a la tabla de clientes proporcionándole un nuevo
   * id único. Si no puede añadirlo devuelve un Cliente con ID = -1
   *
   * @param newClient nuevo cliente
   * @return el Cliente con su nuevo id o id a -1 si hay error
   */
  public Client add(Client newClient) {
    int newId = 1;
    if (clientsTable.length > 0) {
      newId = Arrays.stream(clientsTable).map(Client::getID)
          .max(Comparator.naturalOrder()).get();
      newId++;
    }
    clientsTable = Arrays.copyOf(clientsTable, clientsTable.length + 1);
    try {
      newClient.setID(newId);
      clientsTable[clientsTable.length - 1] = newClient;
    } catch (Exception e) {
      return new Client(-1, "", false);
    }
    return clientsTable[clientsTable.length - 1];
  }

  /**
   * Dados los datos de un nuevo modelo lo añade a la tabla de inventario proporcionándole un nuevo
   * id único. Si no puede añadirlo devuelve un Modelo con ID = -1
   *
   * @param newModel nuevo cliente
   * @return el Cliente con su nuevo id o id a -1 si hay error
   */
  public ShoeModel add(ShoeModel newModel) {
    int newId = 1;
    if (modelsTable.length > 0) {
      newId = Arrays.stream(modelsTable).map(ShoeModel::getID)
          .max(Comparator.naturalOrder()).get();
      newId++;
    }
    modelsTable = Arrays.copyOf(modelsTable, modelsTable.length + 1);
    try {
      newModel.setID(newId);
      modelsTable[modelsTable.length - 1] = newModel;
    } catch (Exception e) {
      return new ShoeModel(-1, "", "", 0);
    }
    return modelsTable[modelsTable.length - 1];
  }

  /**
   * Dados los datos de un nuevo pedido lo añade a la tabla de pedidos proporcionándole un nuevo id
   * único. Si no puede añadirlo devuelve un Order con ID = -1
   *
   * @param newOrder nuevo cliente
   * @return el Cliente con su nuevo id o id a -1 si hay error
   */
  public Order add(Order newOrder) {
    int newId = 1;
    if (ordersTable.length > 0) {
      newId = Arrays.stream(ordersTable).map(Order::getID)
          .max(Comparator.naturalOrder()).get();
      newId++;
    }
    ordersTable = Arrays.copyOf(ordersTable, ordersTable.length + 1);
    try {
      newOrder.setID(newId);
      ordersTable[ordersTable.length - 1] = newOrder;
    } catch (Exception e) {
      return new Order(0, "");
    }
    return ordersTable[ordersTable.length - 1];
  }

  /**
   * Dado un objeto Order con los datos actualizados. Realiza la actualización de la tabla
   * ordersTable. Si tiene éxito devuelve el pedido con los datos nuevos. Si falla un pedido por
   * defecto con el ID = -1
   *
   * @param newOrder pedido con los datos actualizados
   * @return el pedido actualizado o si falla un pedido por defecto con ID = -1
   */
  public Order updateOrder(Order newOrder) {
    ordersTable = Arrays.stream(ordersTable)
        .map(order -> order.getID() == newOrder.getID() ? newOrder : order)
        .toArray(Order[]::new);
    return Arrays.stream(ordersTable)
        .filter(order -> order.getID() == newOrder.getID())
        .findFirst()
        .orElse(new Order());
  }

  //READ

  public Order[] getAllOrders() {
    return ordersTable;
  }

  public ShoeModel[] getAllModels() {
    return modelsTable;
  }

  public Client[] getAllClients() {
    return clientsTable;
  }

  //UPDATE

  /**
   * Dado un objeto modelo con los datos actualizados. Realiza la actualización de la tabla
   * modelsTable. Si tiene éxito devuelve el modelo con los datos nuevos. Si falla un modelo por
   * defecto con el ID = -1
   *
   * @param newModel modelo con los datos actualizados
   * @return el modelo actualizado o si falla un modelo por defecto con ID = -1
   */
  public ShoeModel updateModel(ShoeModel newModel) {
    modelsTable = Arrays.stream(modelsTable)
        .map(model -> model.getID() == newModel.getID() ? newModel : model)
        .toArray(ShoeModel[]::new);
    return Arrays.stream(modelsTable).filter(model -> model.getID() == newModel.getID()).findFirst()
        .orElse(new ShoeModel());
  }

//  public Order updateOrder(ShoeModel newOrder) {
//    ordersTable = Arrays.stream(ordersTable)
//        .map(order -> order.getID() == newOrder.getID() ? newOrder : order)
//        .toArray(Order[]::new);
//    return Arrays.stream(ordersTable).filter(model -> model.getID() == newOrder.getID()).findFirst()
//        .orElse(new Order());
//  }

  //DELETE

  /**
   * Dado un código ID de un cliente intenta eliminar en cascada su registro de la tabla
   * clientsTable.
   *
   * @param clientID id del registro a eliminar
   * @return true si se eliminó false si no
   */
  public boolean deleteClient(int clientID) {
    int initialAmtClients = clientsTable.length;

    ordersTable = Arrays.stream(ordersTable).filter(order -> order.getOwnerID() != clientID)
        .toArray(Order[]::new);

    clientsTable = Arrays.stream(clientsTable).filter(cliente -> cliente.getID() != clientID)
        .toArray(Client[]::new);

    return clientsTable.length < initialAmtClients;
  }

  /**
   * Dado un código ID de un pedido intenta eliminar su registro de la tabla orderTable.
   *
   * @param orderID id del registro a eliminar
   * @return true si se eliminó false si no
   */
  public boolean deleteOrder(int orderID) {
    int initialAmtOrders = ordersTable.length;
    ordersTable = Arrays.stream(ordersTable).filter(order -> order.getID() != orderID)
        .toArray(Order[]::new);
    return ordersTable.length < initialAmtOrders;
  }

  /**
   * Dado un código ID de un modelo intenta eliminar su registro de la tabla modelsTable.
   *
   * @param modelID id del registro a eliminar
   * @return true si se eliminó false si no
   */
  public boolean deleteModel(int modelID) {
    int initialAmtModels = modelsTable.length;
    modelsTable = Arrays.stream(modelsTable).filter(model -> model.getID() != modelID)
        .toArray(ShoeModel[]::new);
    return modelsTable.length < initialAmtModels;
  }
}


