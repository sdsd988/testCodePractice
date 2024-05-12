package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.MyCalculator;
import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.*;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentScoreServiceMockTest {

    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    public void beforeEach() {
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);
        studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository);
    }

    @Test
    @DisplayName("첫번쨰 Mock 테스트")
    void firstSaveScoreMockTest() {
        //given
        String givenStudentName = "jyujyu";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest() {

        //given : 평균 점수가 60점 이상인 경우

        StudentScore expectStudentScore = StudentScoreTestDataBuilder.passed()
                .build();
        StudentPass expectStudentPass = StudentPassFixture.create(expectStudentScore);
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        //when
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        //then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore captruerdStudentScore = studentScoreArgumentCaptor.getValue();
        assertEquals(expectStudentScore.getStudentName(),captruerdStudentScore.getStudentName());
        assertEquals(expectStudentScore.getExam(),captruerdStudentScore.getExam());
        assertEquals(expectStudentScore.getKorScore(),captruerdStudentScore.getKorScore());
        assertEquals(expectStudentScore.getEnglishScore(),captruerdStudentScore.getEnglishScore());
        assertEquals(expectStudentScore.getMathScore(),captruerdStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam());
        assertEquals(expectStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());

    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
    public void saveScoreMockTest2() {

        //given : 평균 점수가 60점 이상인 경우


        StudentScore expectStudentScore = StudentScoreFixture.failed();

        StudentFail expectStudentPass = StudentFailFixture.create(expectStudentScore);

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        //when
        studentScoreService.saveScore(
                expectStudentScore.getStudentName(),
                expectStudentScore.getExam(),
                expectStudentScore.getKorScore(),
                expectStudentScore.getEnglishScore(),
                expectStudentScore.getMathScore()
        );

        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

        StudentScore captruerdStudentScore = studentScoreArgumentCaptor.getValue();
        assertEquals(expectStudentScore.getStudentName(),captruerdStudentScore.getStudentName());
        assertEquals(expectStudentScore.getExam(),captruerdStudentScore.getExam());
        assertEquals(expectStudentScore.getKorScore(),captruerdStudentScore.getKorScore());
        assertEquals(expectStudentScore.getEnglishScore(),captruerdStudentScore.getEnglishScore());
        assertEquals(expectStudentScore.getMathScore(),captruerdStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        assertEquals(expectStudentPass.getStudentName(), capturedStudentFail.getStudentName());
        assertEquals(expectStudentPass.getExam(), capturedStudentFail.getExam());
        assertEquals(expectStudentPass.getAvgScore(), capturedStudentFail.getAvgScore());

    }

    @Test
    @DisplayName("합격자 명단 가져오기 검증")
    void getPassStudentListTest() {
        //given
        var givenTestExam = "testexam";
        StudentPass expectStudent1 = StudentPassFixture.create("jyujyu", givenTestExam);
        StudentPass expectStudent2 = StudentPassFixture.create("testName", givenTestExam);
        StudentPass notExpectStudent3 = StudentPassFixture.create("anotherStudent", "notGivenExam");

        //repository의 메소드를 실행한 리턴값을 고정적으로 정해준다. -> service 테스트를 위해 repository 결과값이 필요하기 때문.
        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));


        //when
        var expectResponse = List.of(expectStudent1, expectStudent2)
                .stream()
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenTestExam);

        assertIterableEquals(expectResponse, responses);

        expectResponse.forEach((response)-> System.out.println(response.getStudentName()+" "+response.getAvgScore()));

        System.out.println("==========================");
        responses.forEach((response)-> System.out.println(response.getStudentName()+" "+response.getAvgScore()));

    }

    @Test
    @DisplayName("불합격자 명단 가져오기 검증")
    void getFailStudentListTest() {
        //given
        String givenTestExam = "testexam";

        StudentFail notExpectStudent3 = StudentFailFixture.create("test1", "notGivenTest");
        StudentFail expectStudent2 = StudentFailFixture.create("test2",givenTestExam);
        StudentFail expectStudent1 = StudentFailFixture.create("test3", givenTestExam);

        //repository에서 메소드를 실행한 리턴값을 고정적으로 정해준다.
        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));

        //when
        var expectResponse = List.of(expectStudent1, expectStudent2)
                .stream()
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenTestExam);

        assertIterableEquals(expectResponse, responses);

        expectResponse.forEach((response)-> System.out.println(response.getStudentName()+" "+response.getAvgScore()));

        System.out.println("==========================");
        responses.forEach((response)-> System.out.println(response.getStudentName()+" "+response.getAvgScore()));

    }
}
