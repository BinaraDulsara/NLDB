package tm;

public class AttendenceTM {
    private String employeeName;
    private String status;

    public AttendenceTM(String employeeName, String status) {
        this.employeeName = employeeName;
        this.status = status;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
