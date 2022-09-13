package best.tigers.tynk_dialog.game.harlowtml;

public class HarlowTMLTagToken extends HarlowTMLToken {
  private String tagName;
  private final TagType tagType;
  private String tagValue;

  public HarlowTMLTagToken(String tagName, TagType tagType, String tagValue) {
    super("TagToken");
    this.tagName = tagName;
    this.tagType = tagType;
    this.tagValue = tagValue;
  }

  public HarlowTMLTagToken(TagType tagType, String tagValue) {
    this("", tagType, tagValue);
  }

  public HarlowTMLTagToken(TagType tagType) {
    this(tagType, "");
  }

  public String getTagValue() {
    return tagValue;
  }

  public void setTagValue(String value) {
    this.tagValue = value;
  }

  public String getTagName() {
    return this.tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  public String toString() {
    return "<HarlowTML Token | name: TagToken | tag: "
        + tagName
        + " | type: "
        + this.tagType.toString()
        + " | value: "
        + this.tagValue
        + ">";
  }

  public TagType getType() {
    switch (getTagName()) {
      case "t":
      case "wait":
      case "time":
        return TagType.ENTITY;
      default:
        return this.tagType;
    }
  }

  public enum TagType {
    OPEN,
    CLOSE,
    ENTITY
  }
}
