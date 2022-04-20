package dam1.prog.tiendav2;

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
  public boolean addNewClient() {
    return true;
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
