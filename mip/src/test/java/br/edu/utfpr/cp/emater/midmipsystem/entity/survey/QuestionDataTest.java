package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuestionDataTest {

    private QuestionData questionData;

    @Test
    public void isRustResistantTest() {
        this.questionData = new QuestionData();
        this.questionData.setRustResistant(false);
        assertThat(this.questionData.isRustResistant()).isFalse();
        this.questionData.setRustResistant(true);
        assertThat(this.questionData.isRustResistant()).isTrue();
    }

    @Test
    public void isBtTest() {
        this.questionData = new QuestionData();
        this.questionData.setBt(false);
        assertThat(this.questionData.isBt()).isFalse();
        this.questionData.setBt(true);
        assertThat(this.questionData.isBt()).isTrue();
    }

    @Test
    public void builderTest() {
        this.questionData = new QuestionData();
        this.questionData = QuestionData.builder().bt(false).rustResistant(false).build();
    }

    @Test
    public void equalsQuestionDataTest() {
        QuestionData questionData = QuestionData.builder().bt(false).rustResistant(false).build();
        this.questionData = QuestionData.builder().bt(true).rustResistant(true).build();
        assertThat(this.questionData.equals(questionData)).isFalse();
    }

    @Test
    public void equalsTrueQuestionDataTest() {
        QuestionData questionData = QuestionData.builder().bt(false).rustResistant(false).build();
        this.questionData = questionData;
        assertThat(this.questionData.equals(questionData)).isTrue();
    }

    @Test
    public void hashCodeTrueQuestionDataTest() {
        QuestionData questionData = QuestionData.builder().bt(false).rustResistant(false).build();
        int value = questionData.hashCode();
        assertThat(questionData.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseQuestionDataTest() {
        QuestionData questionData = QuestionData.builder().bt(false).rustResistant(false).build();
        int value = questionData.hashCode();
        value++;
        assertThat(questionData.hashCode()==value).isFalse();
    }

}