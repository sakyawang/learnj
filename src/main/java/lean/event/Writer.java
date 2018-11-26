package lean.event;

public class Writer extends Thread {

    private String name;

    private TaskService service;

    public Writer(String name, TaskService service) {
        this.name = name;
        this.service = service;
    }

    @Override
    public void run() {
        Task task = new Task();
        task.setName(name);
        service.add(task);
        try {
            Thread.sleep(1000 * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
