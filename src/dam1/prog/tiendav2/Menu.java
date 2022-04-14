package dam1.prog.tiendav2;

public enum Menu {
  MENU_1("Menu 1", new MenuItem[]{new MenuItem(1, "Ir a 2"), new MenuItem(2, "Salir")}),
  MENU_2("Menu 2", new MenuItem[]{new MenuItem(1, "Ir a 1"), new MenuItem(2, "Salir")});

  private final String title;
  private final MenuItem[] listaOpciones;

  Menu(String title, MenuItem[] listaOpciones) {
    this.title = title;
    this.listaOpciones = listaOpciones;
  }

  public MenuItem[] getListaOpciones() {
    return listaOpciones;
  }

  public MenuItem[] title() {
    return listaOpciones;
  }
}
