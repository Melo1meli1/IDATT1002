package no.idatt1002.Model;

public class Budget {

    private int id;
    private String category;
    private int sum;
    private int user_id;
    private Boolean ie;
    private int priority;

    public Budget() {
    }

    public Budget(int id, String category, int sum, int user_id, Boolean ie, int priority) {
        this.id = id;
        this.category = category;
        this.sum = sum;
        this.user_id = user_id;
        this.ie = ie;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getSum() {
        return sum;
    }

    public int getUser_id() {
        return user_id;
    }

    public Boolean getIe() {
        return ie;
    }

    public void setIe(Boolean ie) {
        this.ie = ie;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
