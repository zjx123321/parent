import com.zjx.test.producer.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan
public class Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		KafkaSender sender = applicationContext.getBean(KafkaSender.class);

		for (int i = 0; i < 3; i++) {
			//调用消息发送类中的消息发送方法
			sender.send();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
