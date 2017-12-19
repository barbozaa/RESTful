package helloTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import hello.Application;
import hello.Greeting;

import java.nio.charset.Charset;
import java.util.ArrayList;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

/**
 * @author Armando Barboza
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    
    private ArrayList<Greeting> object;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        object = new ArrayList<Greeting>();
        object.add(new Greeting(1, "Hello, World!"));
    }
    
    @Test
    public void testGet() throws Exception{
    		mockMvc.perform(get("/greeting"))
    					.andExpect(status().isOk())
    					.andExpect(content().contentType(contentType))
    					.andExpect( jsonPath("$.id", is((this.object.get(0).getId()) )) )
    					.andExpect( jsonPath("$.content", is(this.object.get(0).getContent() )));
    }


}
