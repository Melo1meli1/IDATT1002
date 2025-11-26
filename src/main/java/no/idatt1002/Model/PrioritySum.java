package no.idatt1002.Model;

public class PrioritySum {

    private String priority;
    private double sum;

    public PrioritySum() {
    }

    public PrioritySum(String priority, double sum) {
        this.priority = priority;
        this.sum = sum;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String category) {
        this.priority = category;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double d) {
        this.sum = d;
    }
}
