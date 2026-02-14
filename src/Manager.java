import java.io.Serializable;

public class Manager implements Serializable {
    private String managerName;
    private String managerPassword;
    private boolean isMaster = false;
    private static final long serialVersionUID = 1L;

    public Manager(String managerName, String managerPassword) {
        this.managerName = managerName;
        this.managerPassword = managerPassword;
    }

    public String getManagerName() {
        return managerName;
    }
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setIsMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }
}
