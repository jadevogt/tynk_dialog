package best.tigers.tynkdialog.supertext.tokens;

import lombok.Getter;
import lombok.Setter;

public class SuperTextTagToken extends SuperTextToken {

  private final TagType tagType;
  @Getter
  @Setter
  private String tagName;
  @Getter
  @Setter
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
