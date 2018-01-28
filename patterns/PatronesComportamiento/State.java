public class State {

private final String ON_STATE = "Encendido";

private final String OFF_STATE = "Apagado";

// The current state, default is off.
private String currentState = OFF_STATE;

public String redButton() {
  if (currentState.equals(ON_STATE)) {
    currentState = OFF_STATE;
    return "Cambio del estado a apagado";
  } else {
    return "No se ha cambiado el estado";
  }
}

public String greenButton() {
  if (currentState.equals(OFF_STATE)) {
    currentState = ON_STATE;
    return "Cambio del estado a encendido";
  } else {
    return "No se ha cambiado el estado";
  }
}

public static void main(String[] args) {
  State simpleState = new State();
  System.out.println(simpleState.redButton());
  System.out.println(simpleState.greenButton());
  System.out.println(simpleState.greenButton());
  System.out.println(simpleState.redButton());
  System.out.println(simpleState.redButton());
  System.out.println(simpleState.greenButton());
}
}