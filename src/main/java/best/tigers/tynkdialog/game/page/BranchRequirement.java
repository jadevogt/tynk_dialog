package best.tigers.tynkdialog.game.page;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import lombok.Getter;
import lombok.Setter;

public class BranchRequirement {


  @Getter
  @Setter
  private String flag;
  @Getter
  @Setter
  private Category category;
  @Getter
  @Setter
  private String value;
  @Getter
  @Setter
  private ValueType valueType;
  @Getter
  @Setter
  private Comparison comparison;

  public BranchRequirement(String flag, Category category, String value, ValueType valueType) {
    this.flag = flag;
    this.category = category;
    this.value = value;
    this.valueType = valueType;
  }

  public JsonObject serialize() {
    JsonObjectBuilder result = Json.createObjectBuilder();
    result.add("flag", flag);
    switch (category) {
      default -> result.add("category", "story");
    }
    result.add("value", value);
    String typeValue;
    switch (valueType) {
      case STRING -> typeValue = "str";
      case REAL_NUMBER -> typeValue = "real";
      case BOOLEAN -> typeValue = "bool";
      default -> typeValue = "int";
    }
    result.add("valType", typeValue);
    String comparisonValue;
    switch (comparison) {
      case LESS_THAN -> comparisonValue = "<";
      //case LESS_THAN_OR_EQUAL -> comparisonValue = "<=";
      case GREATER_THAN -> comparisonValue = ">";
      //case GREATER_THAN_OR_EQUAL -> comparisonValue = ">=";
      default -> comparisonValue = "=";
    }
    result.add("eval", comparisonValue);
    return result.build();
  }

  public enum Category {
    STORY,
  }

  public enum Comparison {
    LESS_THAN,
    //LESS_THAN_OR_EQUAL,
    GREATER_THAN,
    //GREATER_THAN_OR_EQUAL,
    EQUAL,
  }

  public enum ValueType {
    REAL_NUMBER,
    BOOLEAN,
    STRING,
    INTEGER,
  }
}