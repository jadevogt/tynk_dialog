package best.tigers.tatffdialogutility.game.harlowtml;

public class HarlowTMLEntityToken extends HarlowTMLToken {
  private String entityName;
  private String entityValue;

  public HarlowTMLEntityToken(String entityName, String entityValue) {
    super(entityName);
    this.entityName = entityName;
    this.entityValue = entityValue;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getEntityValue() {
    return entityValue;
  }

  public void setEntityValue(String entityValue) {
    this.entityValue = entityValue;
  }
}
