package no.idatt1002.Model;

public class PriorityCategory {

    private PriorityCategoryEnum priority;

    private String category;

    public PriorityCategory() {
    }

    public PriorityCategory(PriorityCategoryEnum priority, String category) {
        this.priority = priority;
        this.category = category;
    }

    public String getPriority() {
        return priority.getPriority();
    }

    public void setPriority(PriorityCategoryEnum priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public enum PriorityCategoryEnum {
        NECESSARY("Necessary"),
        SAVING("Saving"),
        OK("Ok"),
        UNNECESSARY("Unnecessary");

        private String priority;

        PriorityCategoryEnum(String priority) {
            this.priority = priority;
        }

        public String getPriority() {
            return priority;
        }

        public static PriorityCategoryEnum fromString(String priority) {
            for (PriorityCategoryEnum p : PriorityCategoryEnum.values()) {
                if (p.priority.equalsIgnoreCase(priority)) {
                    return p;
                }
            }
            throw new IllegalArgumentException("No enum constant found for the input string: " + priority);
        }
    }

}