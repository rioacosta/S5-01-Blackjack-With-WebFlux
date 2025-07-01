package cat.itacademy.S5_01_Blackjack_With_WebFlux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S501BlackjackWithWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(S501BlackjackWithWebFluxApplication.class, args);

		System.out.println("\n" +
				"╔══════════════════════════════════════════════════════════════════════════╗\n" +
				"║                          🃏 BLACKJACK API 🃏                              ║\n" +
				"║                                                                          ║\n" +
				"║  🚀 Application started successfully!                                    ║\n" +
				"║                                                                          ║\n" +
				"║  📖 Swagger UI: http://localhost:8080/webjars/swagger-ui/index.html     ║\n" +
				"║  📋 API Docs:   http://localhost:8080/v3/api-docs                        ║\n" +
				"║  ❤️ Health:     http://localhost:8080/actuator/health                   ║\n" +
				"║                                                                          ║\n" +
				"║  🎮 Ready to play Blackjack!                                             ║\n" +
				"╚══════════════════════════════════════════════════════════════════════════╝\n"
		);

	}
}
