package dam1.prog.tiendav2;

public class MockDB {
    private User[] usersTable;
    private Order[] ordersTable;
    private Shoe[] productsTable;
    private int[][] userOrdersTable;
    private int[][] ordersProductsTable;

  public MockDB(User[] usersTable, Order[] ordersTable, Shoe[] productsTable,
      int[][] userOrdersTable, int[][] ordersProductsTable) {
    this.usersTable = usersTable;
    this.ordersTable = ordersTable;
    this.productsTable = productsTable;
    this.userOrdersTable = userOrdersTable;
    this.ordersProductsTable = ordersProductsTable;
  }
}
