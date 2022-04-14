package dam1.prog.tiendav2;

public enum Menu {
  MENU_1("Menu 1", new MenuItem[]{new MenuItem(1, "Ir a 2"), new MenuItem(0, "Salir")}),
  MENU_2("Menu 2", new MenuItem[]{new MenuItem(1, "Ir a 1"), new MenuItem(0, "Salir")});

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
