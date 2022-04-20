package dam1.prog.tiendav2.view;

public record MenuItem(String optionNumber, String optionLabel) {

  public String getOptionNumber() {
    return optionNumber;
  }

  public String getOptionLabel() {
    return optionLabel;
  }
}
