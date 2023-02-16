package best.tigers.tynkdialog.game.page;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import lombok.Getter;
import lombok.Setter;

public class BranchRequirement {


  @Getter
  private final String flag;
  @Getter
  private final Category category;
  @Getter
  private final String value;
  @Getter
  private final ValueType valueType;
  @Getter
  private final Comparison comparison;

  @Override
  public String toString() {
    return "%s: %s (%s) %s %s".formatted(category, flag, valueType, comparison, value);
  }

  public BranchRequirement(String flag, Category category, String value, ValueType valueType, Comparison comparison) {
    this.flag = flag;
    this.category = category;
    this.value = value;
    this.valueType = valueType;
    this.comparison = comparison;
  }

  public BranchRequirement() {
    this("", Category.STORY, "", ValueType.INTEGER, Comparison.EQUAL);
  }

  public JsonObject serialize() {
    JsonObjectBuilder result = Json.createObjectBuilder();
    result.add("flag", flag);
    result.add("category", category.toString());
    result.add("val", value);
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
      case GREATER_THAN -> comparisonValue = ">";
      default -> comparisonValue = "=";
    }
    result.add("eval", comparisonValue);
    return result.build();
  }

  public enum Category {
    STORY {
      @Override
      public String toString() {
        return "story";
      }
    },
    SKILL{
      @Override
      public String toString() {
        return "skills";
      }
    },
    DIALOG_SEEN{
      @Override
      public String toString() {
        return "dialogSeen";
      }
    },
    DUNGEON{
      @Override
      public String toString() {
        return "dungeon";
      }
    },
    CH00{
      @Override
      public String toString() {
        return "ch00";
      }
    },
    CH01{
      @Override
      public String toString() {
        return "ch01";
      }
    },
    CH02{
      @Override
      public String toString() {
        return "ch02";
      }
    },
    CH03{
      @Override
      public String toString() {
        return "ch03";
      }
    },
    CH04{
      @Override
      public String toString() {
        return "ch04";
      }
    },
    CH05{
      @Override
      public String toString() {
        return "ch05";
      }
    },
    CH06{
      @Override
      public String toString() {
        return "ch06";
      }
    },
    CH07{
      @Override
      public String toString() {
        return "ch07";
      }
    },
    CH08{
      @Override
      public String toString() {
        return "ch08";
      }
    },
    OTHER{
      @Override
      public String toString() {
        return "other";
      }
    },
  }

  public enum Comparison {
    LESS_THAN {
      @Override
      public String toString() {
        return "<";
      }
    },
    GREATER_THAN {
      @Override
      public String toString() {
        return ">";
      }
    },
    EQUAL {
      @Override
      public String toString() {
        return "==";
      }
    },
  }

  public enum ValueType {
    REAL_NUMBER {
      @Override
      public String toString() {
        return "real";
      }
    },
    BOOLEAN {
      public String toString() {
        return "bool";
      }
    },
    STRING {
      public String toString() {
        return "string";
      }
    },
    INTEGER {
      public String toString() {
        return "int";
      }
    },
  }
}

