package dam1.prog.tiendav2;

public class MockDB {
    private User[] usersTable;
    private Order[] orderTable;

  public MockDB(User[] usersTable, Order[] orderTable) {
    this.usersTable = usersTable;
    this.orderTable = orderTable;
  }

  public User[] getUsersTable() {
    return usersTable;
  }

  public void setUsersTable(User[] usersTable) {
    this.usersTable = usersTable;
  }

  public Order[] getOrderTable() {
    return orderTable;
  }

  public void setOrderTable(Order[] orderTable) {
    this.orderTable = orderTable;
  }
}
