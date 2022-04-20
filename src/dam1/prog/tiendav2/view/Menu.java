package dam1.prog.tiendav2.view;

public enum Menu {
  MENU_PRINCIPAL("MENU PRINCIPAL", new MenuItem[]{
      new MenuItem("1", "Nuevo pedido"),
      new MenuItem("2", "Gestión de clientes"),
      new MenuItem("3", "Gestión de inventario"),
      new MenuItem("0", "Salir del programa")}
  ),
  MENU_CLIENTES("CLIENTES", new MenuItem[]{
      new MenuItem("1", "Ver clientes"),
      new MenuItem("2", "Añadir cliente"),
      new MenuItem("3", "Eliminar cliente"),
      new MenuItem("9", "Volver atrás"),
      new MenuItem("0", "Salir del programa")}
  ),
  MENU_INVENTARIO("INVENTARIO", new MenuItem[]{
      new MenuItem("1", "Ver inventario"),
      new MenuItem("2", "Añadir nuevo modelo"),
      new MenuItem("3", "Eliminar modelo"),
      new MenuItem("4", "Modificar stock"),
      new MenuItem("9", "Volver atrás"),
      new MenuItem("0", "Salir del programa")}
  ),
  MENU_PEDIDOS("PEDIDOS", new MenuItem[]{
      new MenuItem("1", "Añadir producto"),
      new MenuItem("2", "Eliminar producto"),
      new MenuItem("3", "Realizar pago"),
      new MenuItem("4", "Cancelar pedido")}
  );

  private final String title;
  private final MenuItem[] optionList;

  Menu(String title, MenuItem[] optionList) {
    this.title = title;
    this.optionList = optionList;
  }

  public String getTitle() {
    return title;
  }

  public MenuItem[] getOptionList() {
    return optionList;
  }
}
