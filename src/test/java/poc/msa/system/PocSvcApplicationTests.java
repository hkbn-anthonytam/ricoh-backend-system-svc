package poc.msa.system;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import poc.msa.system.PocSvcApplication;
import poc.msa.system.repository.EstateRepository;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = PocSvcApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-test.properties")
class PocSvcApplicationTests {

	@Autowired
    private MockMvc mvc;

    @Autowired
    private EstateRepository estateRepository;

	@Test
	void testEstate() throws Exception {
		//createTestEmployee("bob");

		mvc.perform(get("/languages")
		  .contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isOk());
		  //.andExpect(content()
		  //.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		  //.andExpect(content().string(containsString("Hello, World")));
	}

	private Matcher<? super String> containsString(String string) {
		return null;
	}
}
