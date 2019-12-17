package fun.clclcl.yummic.codebase.sample.akka;

import akka.actor.*;

public class AkkMaster extends AbstractActor {

    public static class MasterInfo {
        private String info;
        private Long id;
        private String dest;

        public MasterInfo(String info, Long id, String dest) {
            this.info = info;
            this.id = id;
            this.dest = dest;
        }
    }

    public static class TaskResult {
        Long id;
        boolean result;

        public TaskResult(Long id, boolean result) {
            this.id = id;
            this.result = result;
        }

        @Override
        public String toString() {
            return "TaskResult{" +
                    "id=" + id +
                    ", result=" + result +
                    '}';
        }
    }

    @Override
    public Receive createReceive() {
        Receive receive = receiveBuilder().match(MasterInfo.class, info -> {
            System.out.println("Receive info : " + info.info);
            if (info.dest != null) {
                if (info.id == 0) {
                    //must return to sender.
                    getSender().tell("ok. i return to sender.", getSelf());
                    return;
                }
                //forward dest.
                Thread.sleep(2000);
                ActorRef printerActor = AkkAppMain.system.actorOf(Props.create(AkkWorker.class, AkkWorker::new));
                System.out.println("forward task to : " + info.dest);
                printerActor.tell(new AkkWorker.WorkTask("Write a program.", info.id), getSelf());
            }
        }).match(TaskResult.class, result -> {
            System.out.println("Get task result : " + result);
        }).matchAny(obj -> {
            System.out.println("Master cannot process : " + obj);
            unhandled(obj);
        }).build().orElse(null);
        return receive;
    }
}
