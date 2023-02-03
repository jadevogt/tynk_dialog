package best.tigers.tynkdialog.util.page;

import best.tigers.tynkdialog.exceptions.PageParseException;
import best.tigers.tynkdialog.game.page.AbstractPage;
import best.tigers.tynkdialog.game.page.BranchPage;
import best.tigers.tynkdialog.game.page.BranchPage.Leaf;
import best.tigers.tynkdialog.game.page.BranchRequirement;
import best.tigers.tynkdialog.game.page.BranchRequirement.Category;
import best.tigers.tynkdialog.game.page.BranchRequirement.Comparison;
import best.tigers.tynkdialog.game.page.BranchRequirement.ValueType;
import best.tigers.tynkdialog.util.ParseUtils;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Stream;
import jakarta.json.JsonObject;

public class BranchPageBuilder implements PageBuilder {
  private Leaf leaf;
  private String result;
  private ArrayList<BranchRequirement> requirements;
  @Override
  public void parseJson(JsonObject pageData) throws PageParseException {
    var rawLeaf = pageData.getString("leaf").toLowerCase(Locale.ROOT);
    leaf = switch (rawLeaf) {
      case "jump" -> Leaf.JUMP;
      case "add" -> Leaf.ADD;
      default -> Leaf.JUMP;
    };
    result = ParseUtils.getNullableTynkString(pageData.get("result")).orElse("");
    requirements = new ArrayList<>(pageData.getJsonArray("req")
        .stream()
        .filter(o -> o instanceof JsonObject)
        .map(JsonObject.class::cast)
        .filter(this::parseRequirement)
        .map(this::buildRequirement).toList());

  }

  private BranchRequirement buildRequirement(JsonObject req) {
    var flag = ParseUtils.getNullableTynkString(req.get("flag")).orElse("prog");
    var rawCategory = ParseUtils.getNullableTynkString(req.get("category")).orElse("story");
    String value;
    if (req.containsKey("value")) {
      value = req.getString("value");
    } else {
      value = req.getString("val");
    }
    var rawValueType = ParseUtils.getNullableTynkString(req.get("valueType")).orElse("int");
    var rawEval = ParseUtils.getNullableTynkString(req.get("eval")).orElse("=");
    BranchRequirement.Category category = switch (rawCategory.toLowerCase()) {
      case "story" -> Category.STORY;
      default -> null;
    };
    BranchRequirement.ValueType valueType = switch (rawValueType) {
      case "int", "integer" -> ValueType.INTEGER;
      case "bool", "boolean" -> ValueType.BOOLEAN;
      case "real", "real number" -> ValueType.REAL_NUMBER;
      case "string", "str" -> ValueType.STRING;
      default -> null;
    };
    BranchRequirement.Comparison comparison = switch (rawEval) {
      case "=" -> Comparison.EQUAL;
      case ">" -> Comparison.GREATER_THAN;
      case "<" -> Comparison.LESS_THAN;
      default -> null;
    };
    return new BranchRequirement(flag, category, value, valueType, comparison);
  }

  private boolean parseRequirement(JsonObject req) {
    String flag;
    String category;
    String valueType;
    String eval;
    try {
      flag = ParseUtils.getNullableTynkString(req.get("flag")).orElse("prog");
      category = ParseUtils.getNullableTynkString(req.get("category")).orElse("story");
      valueType = ParseUtils.getNullableTynkString(req.get("valueType")).orElse("int");
      eval = ParseUtils.getNullableTynkString(req.get("eval")).orElse("=");
    } catch (ClassCastException cce) {
      return false;
    }
    return true;
  }

  @Override
  public boolean verify() {
    return false;
  }

  @Override
  public AbstractPage build() {
    return new BranchPage(leaf, result, requirements);
  }
}
