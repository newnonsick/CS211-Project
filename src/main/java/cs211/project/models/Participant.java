package cs211.project.models;

public class Participant {
    private String partiName;
    private String partiUsername;
    private Boolean approved;

    public Participant(String partiName, String partiUsername){
        this.partiName = partiName;
        this.partiUsername = partiUsername;
    }

    public Participant(String partiName, String partiUsername, Boolean approved) {
        this.partiName = partiName;
        this.partiUsername = partiUsername;
        this.approved = approved;
    }
    public String getPartiName(){
        return partiName;
    }

    public void setPartiName(String partiName) {
        this.partiName = partiName;
    }
    public String getPartiUsername(){
        return partiUsername;
    }
    public void setPartiUsername(String partiUsername) {
        this.partiUsername = partiUsername;
    }

    public Boolean getApproved() {
        return approved;
    }
    public void setApproved(Boolean approve) {
        this.approved = approved;
        }

    public boolean isApproved(){
        return approved;
    }

}

