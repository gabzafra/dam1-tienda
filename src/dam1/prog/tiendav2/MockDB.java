package dam1.prog.tiendav2;


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
        new Order(1, 3, "completed", new Shoe[]{new Shoe(2, 1)}),
        new Order(2, 5, "completed", new Shoe[]{
            new Shoe(7, 1),
            new Shoe(10, 1),
            new Shoe(11, 1)
        }),
        new Order(3, 2, "completed", new Shoe[]{new Shoe(1, 2)}),
        new Order(4, 1, "completed", new Shoe[]{
            new Shoe(4, 1),
            new Shoe(11, 3)
        })
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

  public Client[] getClientsTable() {
    return clientsTable;
  }

  public Order[] getOrdersTable() {
    return ordersTable;
  }

  public ShoeModel[] getAllModels() {
    return modelsTable;
  }

  public Client[] getAllClients() {
    return clientsTable;
  }

  public Client addClient(Client newClient) {
    int newId = 1;
    if (clientsTable.length > 0) {
      newId = Arrays.stream(clientsTable).map(client -> client.getID())
          .max(Comparator.naturalOrder()).get();
      newId++;
    }
    clientsTable = Arrays.copyOf(clientsTable, clientsTable.length + 1);
    try {
      newClient.setID(newId);
      clientsTable[clientsTable.length - 1] = newClient;
    } catch (Exception e) {
      return new Client("", false);
    }
    return clientsTable[clientsTable.length - 1];
  }
}


