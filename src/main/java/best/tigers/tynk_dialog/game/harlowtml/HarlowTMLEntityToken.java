package best.tigers.tynk_dialog.game.harlowtml;

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
    public String getEntityValue() {
        return entityValue;
    }
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }
}
