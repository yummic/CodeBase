package fun.clclcl.yummic.codebase.sample.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("task")
public class TaskController {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    DateFormatter dateFormat = new DateFormatter();

    Map<String, ScheduledFuture> taskMap = new HashMap<>();

    @GetMapping
    public String getTasks() {
        return taskMap.keySet().stream().collect(Collectors.joining(","));
    }

    @PostMapping("/{name}")
    public String addTask(@PathVariable String name) throws ParseException {
        System.out.println("try to add task : " + threadPoolTaskScheduler);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        ScheduledFuture<?> task = threadPoolTaskScheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task " + name + " run @ " + Calendar.getInstance().getTime() + "[ " + Thread.currentThread().getName() + " ]");
        }, calendar.getTime(), 10 * 1000);
        taskMap.put(name, task);
        return "success";
    }

    @DeleteMapping("/{name}")
    public String deleteTask(@PathVariable String name) throws ParseException {
        System.out.println("try to delete task : " + threadPoolTaskScheduler);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        ScheduledFuture task = taskMap.get(name);
        if (task != null) {
            task.cancel(true);
            System.out.println("delete task:" + task + name);
        }
        return "success";
    }
}
