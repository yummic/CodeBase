package fun.clclcl.yummic.codebase.sample.springboot;

import fun.clclcl.yummic.codebase.sample.springboot.service.UserDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@WebAppConfiguration
public class FooBootApplicationTests {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	UserDao userDao;

	@BeforeMethod
	public void setUp() {
		//MockitoAnnotations.initMocks(this);
	}

	@Test
	public void contextLoads() {

		Assert.assertNotNull(context);
		Assert.assertNotNull(mockMvc);
		Assert.assertNotNull(userDao);
	}

	@Test
	public void testHello() throws Exception {
		//given(this.userDao.getUser()).willReturn("Boot");// NullPointerException here because of userDao is null.
		MvcResult result = mockMvc.perform(get("/hello")).andReturn();
		Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
		Assert.assertEquals(result.getResponse().getContentAsString(), "{\"id\":1,\"content\":\"Hello, Boot\"}");
	}

	@Configuration
	@Import(UserDao.class) // A @Component injected with ExampleService
	static class Config {
	}


}
