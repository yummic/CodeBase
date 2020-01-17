package fun.clclcl.yummic.codebase.sample.springboot;

import fun.clclcl.yummic.codebase.sample.springboot.controller.fio.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import java.util.Date;

@SpringBootApplication
public class FooBootApplication implements ApplicationListener<ApplicationReadyEvent> {

	public static int port = 9000;

	DigestAuthenticationFilter aaa;



	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	public static void main(String[] args) {
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		SpringApplication.run(FooBootApplication.class, args);

	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
		executor.setPoolSize(20);
		executor.setThreadNamePrefix("taskExecutor-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(60);
		return executor;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

		System.out.println("Start main task : " + threadPoolTaskScheduler);
		threadPoolTaskScheduler.scheduleAtFixedRate(() -> {
			System.out.println("Main task run @" + new Date() + "[ " + Thread.currentThread().getName() + " ]");
		}, 60 * 1000);

		threadPoolTaskScheduler.schedule(() -> {
			System.out.println("trigger task." + new Date());
		}, new CronTrigger("0/30 * * * 1/5 *"));
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
