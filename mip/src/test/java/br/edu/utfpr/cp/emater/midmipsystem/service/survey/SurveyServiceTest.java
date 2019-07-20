package br.edu.utfpr.cp.emater.midmipsystem.service.survey;


import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import br.edu.utfpr.cp.emater.midmipsystem.service.base.FieldService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyServiceTest {


    private MockMvc mockMVC;

    @Before
    public void setUp(){
        this.mockMVC = MockMvcBuilders.webAppContextSetup((WebApplicationContext) this).build();
    }


}
