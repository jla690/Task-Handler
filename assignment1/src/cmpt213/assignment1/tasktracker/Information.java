package cmpt213.assignment1.tasktracker;

import java.util.GregorianCalendar;

/**
 * Information class which stores the different components of every task.
 * Implements the compareTo function for use in sorting dates.
 */
public class Information implements Comparable<Information> {
    String taskName;
    String taskNotes;
    GregorianCalendar taskDate;
    boolean taskCompleted;

    Information(String name, String notes, GregorianCalendar date, boolean completed) {
        this.taskDate = date;
        this.taskCompleted = completed;
        this.taskName = name;
        this.taskNotes = notes;
    }

    Information() {

    }

    public String toString() {
        return taskName + ", " + taskNotes + ", " + taskDate.getTime() + ", Completed? " + taskCompleted;
    }

    @Override
    public int compareTo(Information compared) {
        return this.taskDate.compareTo(compared.taskDate);
    }
}
