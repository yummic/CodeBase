package fun.clclcl.yummic.codebase.sample.akka;

import akka.actor.AbstractActor;

public class AkkWorker extends AbstractActor {

    public static class WorkTask {
        Long id;
        String task;

        public WorkTask(String task, Long id) {
            this.task = task;
            this.id = id;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(WorkTask.class, workTask -> {
            Thread.sleep(5000);
            System.out.println("Work to complete task : " + workTask.task);
            getSender().tell(new AkkMaster.TaskResult(workTask.id, true), getSelf());
        }).build();
    }

}
