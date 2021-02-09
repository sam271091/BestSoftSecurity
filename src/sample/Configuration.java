package sample;

public class Configuration {

    private String confName;
    private String confDescription;
    private String confKey;

    public Configuration(String confName, String confDescription, String confKey) {
        this.confName = confName;
        this.confDescription = confDescription;
        this.confKey = confKey;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getConfDescription() {
        return confDescription;
    }

    public void setConfDescription(String confDescription) {
        this.confDescription = confDescription;
    }

    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }
}
