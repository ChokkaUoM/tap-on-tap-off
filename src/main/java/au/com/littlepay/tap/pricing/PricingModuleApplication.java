package au.com.littlepay.tap.pricing;

import au.com.littlepay.tap.pricing.service.TapOnOffService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PricingModuleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(PricingModuleApplication.class, args);
        TapOnOffService tapOnOffService = applicationContext.getBean(TapOnOffService.class);
        tapOnOffService.start();

    }

}
