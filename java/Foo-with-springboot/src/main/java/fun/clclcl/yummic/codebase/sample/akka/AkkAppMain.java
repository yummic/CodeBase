package fun.clclcl.yummic.codebase.sample.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AkkAppMain {
    public static final ActorSystem system = ActorSystem.create("helloakka");
    public static void main(String[] args) throws IOException {

        ActorRef masterRef = system.actorOf(Props.create(AkkMaster.class, () -> new AkkMaster()), "master");
        System.out.println("***** send test1 from Main.");
        masterRef.tell(new AkkMaster.MasterInfo("master test1", 1L, "Xiaoming"), ActorRef.noSender());
        System.out.println("***** send test2 from Main.");
        masterRef.tell(new AkkMaster.MasterInfo("master test2", 2L, "Hong"), ActorRef.noSender());
        System.out.println("***** send ask from Main.");
        Timeout duration = Timeout.durationToTimeout(Duration.create(20, TimeUnit.SECONDS));
        Future<Object> answer = Patterns.ask(masterRef, new AkkMaster.MasterInfo("ask info", 0L, "For Ask"), duration);
        Object result = null;
        try {
            result = Await.result(answer, Duration.create(10, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Wait for result : " + result);
        System.out.println("***** send test3 from Main.");
        masterRef.tell(new AkkMaster.MasterInfo("master test3", 3L, "Leilei"), ActorRef.noSender());
        System.out.println("***** send task from Main.");
        masterRef.tell(new AkkWorker.WorkTask("task to master.", 4L), ActorRef.noSender());

//
//            //#create-actors iIlL
//            final ActorRef printerActor = system.actorOf(Printer.props(), "printerActor");
//            final ActorRef howdyGreeter = system.actorOf(Greeter.props("Howdy", printerActor), "howdyGreeter");
//            final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", printerActor), "helloGreeter");
//            final ActorRef goodDayGreeter = system.actorOf(Greeter.props("Good day", printerActor), "goodDayGreeter");
//            //#create-actors
//
//            //#main-send-messages
//            howdyGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
//            howdyGreeter.tell(new Greet(), ActorRef.noSender());
//
//            howdyGreeter.tell(new WhoToGreet("Lightbend"), ActorRef.noSender());
//            howdyGreeter.tell(new Greet(), ActorRef.noSender());
//
//            helloGreeter.tell(new WhoToGreet("Java"), ActorRef.noSender());
//            helloGreeter.tell(new Greet(), ActorRef.noSender());
//
//            goodDayGreeter.tell(new WhoToGreet("Play"), ActorRef.noSender());
//            goodDayGreeter.tell(new Greet(), ActorRef.noSender());
//            //#main-send-messages
//
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();

        system.terminate();
        System.out.println(">>> bye bye <<<");
    }
}
