package br.edu.utfpr.cp.emater.midmipsystem.repository.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Field;
import br.edu.utfpr.cp.emater.midmipsystem.entity.survey.Survey;
import br.edu.utfpr.cp.emater.midmipsystem.repository.survey.SurveyRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


@RunWith(SpringRunner.class)
@DataJpaTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FieldRepositoryTest {


    private Field field;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private SurveyRepository surveyRepository;

    @Before
    public void setUp(){
        this.field = Field.builder().name("").location("2").build();
    }

    @Test(expected = ConstraintViolationException.class)
    public  void setNameExceptionNameLessThan5FieldTest() throws ParseException {
        this.field.setName("test");
        this.fieldRepository.save(this.field);
    }


    @Test (expected = ConstraintViolationException.class)
    public  void setNameExceptionNameBigThan50FFieldTest() {
        this.field.setName("123456789-123456789-123456789-123456789-123456789-1");
        this.fieldRepository.save(this.field);
    }

}