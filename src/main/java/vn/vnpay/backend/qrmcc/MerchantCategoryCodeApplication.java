package vn.vnpay.backend.qrmcc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableJpaRepositories(
		basePackages = "vn.vnpay.backend.qrmcc.repository",
		entityManagerFactoryRef = "onEntityManager",
		transactionManagerRef = "onTransactionManager"
)
@EnableRetry
@ComponentScan({"vn.vnpay.backend.qrmcc",
		"vn.vnpay.common.qrdatabase.management.on",
		"vn.vnpay.common.qrdatabase.config"
		})
@Slf4j
public class MerchantCategoryCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantCategoryCodeApplication.class, args);
	}

}
