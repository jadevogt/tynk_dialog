package best.tigers.tynkdialog.supertext.tokens;

public class SuperTextTagToken extends SuperTextToken {

  private final TagType tagType;
  private String tagName;
  private String tagValue;

  public SuperTextTagToken(String tagName, TagType tagType, String tagValue) {
    super("TagToken");
    this.tagName = tagName;
    this.tagType = tagType;
    this.tagValue = tagValue;
  }

  public SuperTextTagToken(TagType tagType, String tagValue) {
    this("", tagType, tagValue);
  }

  public SuperTextTagToken(TagType tagType) {
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

  @Override
  public String toString() {
    return "<SuperText Token | name: TagToken | tag: "
        + tagName
        + " | type: "
        + this.tagType.toString()
        + " | value: "
        + this.tagValue
        + ">";
  }

  public TagType getTagType() {
    return switch (getTagName()) {
      case "t", "wait", "time", "n", "f" -> TagType.ENTITY;
      default -> this.tagType;
    };
  }

  public enum TagType {
    OPEN,
    CLOSE,
    ENTITY
  }
}
