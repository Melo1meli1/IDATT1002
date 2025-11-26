package no.idatt1002.Model;

public class Income {

    private int id;
    private String name;
    private String category;
    private int sum;
    private boolean recurring;
    private String date;
    private String user_id;

    public Income() {
    }

    public Income(int id, String name, String category, int sum, boolean recurring, String date, String user_id) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.sum = sum;
        this.recurring = recurring;
        this.date = date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
