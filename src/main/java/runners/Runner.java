package runners;

import io.cucumber.core.cli.Main;

public class Runner {

  public static void main(String[] args) {
    //
      Main.main("classpath:feature", "--glue", "stepdefinitions");
  }
}
