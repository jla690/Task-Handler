package cmpt213.assignment1.tasktracker;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 * Menu class which handles all the actions that are done by the user and
 * prints all the different parts of the menu. Also keeps an array of tasks which are
 * objects of the Information class.
 */
public class Menu {
    String menuTitle = " Hello! Welcome to the Task Tracker! ";
    int chosenOption;
    ArrayList<Information> tasks;
    int numOfTasks = 0;
    String[] menuOptions = {"List all tasks", "Add a task", "Remove a task", "Mark a task as complete", "List overdue incomplete tasks", "List upcoming incomplete tasks", "Exit"};

    Menu() {
        this.tasks = new ArrayList<>();
    }

    void menuPrint() {
        for (int i = 0; i < menuTitle.length() + 2; i++) {
            System.out.print("#");
        }
        System.out.printf("\n#%s#\n", menuTitle);
        for (int i = 0; i < menuTitle.length() + 2; i++) {
            System.out.print("#");
        }
        System.out.println();
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.printf("%d. ", i + 1);
            System.out.printf("%s\n", menuOptions[i]);
        }
        menuInput();
    }

    void menuInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a number between 1-7: ");
        chosenOption = scanner.nextInt();
        while (chosenOption > 7 || chosenOption < 1) {
            System.out.print("Invalid selection. Enter a number between 1-7: ");
            chosenOption = scanner.nextInt();
        }
        switch (chosenOption) {
            case 1 -> {
                listTasks(true);
                break;
            }
            case 2 -> {
                addTask();
                break;
            }
            case 3 -> {
                removeTask();
                break;
            }
            case 4 -> {
                markCompleted();
                break;
            }
            case 5 -> {
                listOverdue();
                break;
            }
            case 6 -> {
                listUpcoming();
                break;
            }
            case 7 -> {
                System.out.println("Thank you for using the system.");
                return;
            }
        }
    }

    void listOverdue() {
        GregorianCalendar current = new GregorianCalendar();
        for (int i = 0; i < numOfTasks; i++) {
            if (!tasks.get(i).taskCompleted && (tasks.get(i).taskDate.compareTo(current) < 0)) {
                System.out.println("Task #" + i + 1);
                System.out.println("Task: " + tasks.get(i).taskName);
                System.out.println("Notes: " + tasks.get(i).taskNotes);
                GregorianCalendar temp = tasks.get(i).taskDate;
                String date = temp.toZonedDateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm"));
                System.out.println(date);
                String msg = tasks.get(i).taskCompleted ? "Completed? Yes" : "Completed? No";
                System.out.println(msg);
                System.out.println();
            }
        }
        menuPrint();
    }

    void removeTask() {
        listTasks(false);
        System.out.print("Enter the task number you want to remove (0 to cancel): ");
        Scanner scanner = new Scanner(System.in);
        int chosenTask = scanner.nextInt();
        if (chosenTask == 0) {
            System.out.println("Task removal canceled.");
            menuPrint();
            return;
        }
        String removedTaskName = tasks.get(chosenTask - 1).taskName;
        tasks.remove(chosenTask - 1);
        numOfTasks--;
        System.out.println(removedTaskName + " has been removed.");
        menuPrint();
    }

    void listUpcoming() {
        GregorianCalendar current = new GregorianCalendar();
        for (int i = 0; i < numOfTasks; i++) {
            if (!tasks.get(i).taskCompleted && (tasks.get(i).taskDate.compareTo(current)) > 0) {
                System.out.println("Task #" + i + 1);
                System.out.println("Task: " + tasks.get(i).taskName);
                System.out.println("Notes: " + tasks.get(i).taskNotes);
                GregorianCalendar temp = tasks.get(i).taskDate;
                String date = temp.toZonedDateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm"));
                System.out.println(date);
                String msg = tasks.get(i).taskCompleted ? "Completed? Yes" : "Completed? No";
                System.out.println(msg);
                System.out.println();
            }
        }
        menuPrint();
    }

    void listTasks(boolean ret) {
        if (numOfTasks == 0) {
            System.out.println("No tasks to show. ");
            menuInput();
            return;
        }
        for (int i = 0; i < numOfTasks; i++) {
            int taskNum = i + 1;
            System.out.println("Task #" + taskNum);
            System.out.println("Task: " + tasks.get(i).taskName);
            System.out.println("Notes: " + tasks.get(i).taskNotes);
            GregorianCalendar temp = tasks.get(i).taskDate;
            String date = temp.toZonedDateTime().format(DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm"));
            System.out.println(date);
            String msg = tasks.get(i).taskCompleted ? "Completed? Yes" : "Completed? No";
            System.out.println(msg);
            System.out.println();
        }
        if (ret) {
            menuPrint();
        }
    }

    void markCompleted() {
        listTasks(false);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the task number you want to mark as completed (0 to cancel): ");
        chosenOption = scanner.nextInt();
        tasks.get(chosenOption - 1).taskCompleted = true;
        menuPrint();
    }

    void addTask() {
        Information newTask = new Information();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the new task: ");
        newTask.taskName = scanner.nextLine();
        System.out.print("Enter the notes of your new task: ");
        newTask.taskNotes = scanner.nextLine();
        System.out.print("Enter the year of the due date: ");
        int year = scanner.nextInt();
        while (year < 0) {
            System.out.println("Error: year must be > 0.");
            System.out.print("Enter the year of the due date: ");
            year = scanner.nextInt();
        }
        System.out.print("Enter the month of the due date (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter the day of the due date (1-28/29/30/31): ");
        int day = scanner.nextInt();
        while (true) {
            try {
                LocalDate date = LocalDate.of(year, Month.of(month), day);
                break;
            } catch (DateTimeException ex) {
                System.out.println("Error: this date does not exist.");
                System.out.print("Enter the day of the due date (1-28/29/30/31): ");
                day = scanner.nextInt();
            }
        }
        System.out.print("Enter the hour of the due date (0-23): ");
        int hour = scanner.nextInt();
        while (hour > 23 || hour < 0) {
            System.out.println("Error: hour must be between 0-23.");
            System.out.print("Enter the hour of the due date (0-23): ");
            hour = scanner.nextInt();
        }
        System.out.print("Enter the minute of the due date (0-59): ");
        int minute = scanner.nextInt();
        while (minute < 0 || minute > 59) {
            System.out.println("Error: minute must be between 0-59.");
            System.out.print("Enter the minute of the due date (0-59): ");
            minute = scanner.nextInt();
        }
        newTask.taskDate = new GregorianCalendar(year, month - 1, day, hour, minute);
        newTask.taskCompleted = false;
        System.out.println(newTask.taskName + " has been added to the list of tasks.");
        tasks.add(newTask);
        numOfTasks++;
        Collections.sort(tasks);
        menuPrint();
    }
}
