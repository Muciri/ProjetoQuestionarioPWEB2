package br.edu.ifpb.ProjetoQuestionarioPWEB2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpb.pweb2.ProjetoQuestionarioPWEB2.Application;

@SpringBootTest(
		classes = Application.class,
		properties = {
				"spring.datasource.url=jdbc:h2:mem:questionario-test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
				"spring.datasource.driver-class-name=org.h2.Driver",
				"spring.datasource.username=sa",
				"spring.datasource.password=",
				"spring.jpa.hibernate.ddl-auto=create-drop",
				"spring.sql.init.mode=never"
		}
)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
