package no.idatt1002.Model;

public class Priority {

    private PriorityEnum priority;

    private int sum;

    public Priority() {
    }

    public Priority(PriorityEnum priority, int sum) {
        this.priority = priority;
        this.sum = sum;
    }

    public String getPriority() {
        return priority.getPriority();
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public enum PriorityEnum {
        NECESSARY("Necessary"),
        SAVING("Saving"),
        OK("Ok"),
        UNNECESSARY("Unnecessary");

        private String priority;

        PriorityEnum(String priority) {
            this.priority = priority;
        }

        public String getPriority() {
            return priority;
        }
    }
}