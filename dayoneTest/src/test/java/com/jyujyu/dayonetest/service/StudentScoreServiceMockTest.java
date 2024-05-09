package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.MyCalculator;
import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentScoreServiceMockTest {

    @Test
    void firstSaveScoreMockTest() {

        //given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentPassRepository.class),
                Mockito.mock(StudentFailRepository.class)
        );

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository);

        String givenStudentName = "jyujyu";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();
        StudentPass expectStudentPass = StudentPass
                    .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        new MyCalculator(0.0)
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                                )
                .build();
        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository);

        String givenStudentName = "jyujyu";
        String givenExam = "testExam";
        Integer givenKorScore = 40;
        Integer givenEnglishScore = 10;
        Integer givenMathScore = 60;


        StudentScore expectStudentScore = StudentScore.builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .englishScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentFail expectStudentPass = StudentFail
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        new MyCalculator(0.0)
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                )
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);



        //when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("jyujyu").exam("testexam").avgScore(70.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("testexam").avgScore(80.0).build();
        StudentPass notExpectStudent3 =StudentPass.builder().id(3L).studentName("iamnt").exam("secondexam").avgScore(90.0).build();

        //repository에서 메소드를 실행한 리턴값을 고정적으로 정해준다.
        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository);

        var givenTestExam = "testexam";

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
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
        String givenTestExam = "testexam";

        StudentFail notExpectStudent3 = StudentFail.builder().id(1L).studentName("jyujyu").exam("secondExam").avgScore(50.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("test").exam(givenTestExam).avgScore(45.0).build();
        StudentFail expectStudent1 =StudentFail.builder().id(3L).studentName("iamnt").exam(givenTestExam).avgScore(30.0).build();

        //repository에서 메소드를 실행한 리턴값을 고정적으로 정해준다.
        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
                expectStudent1,expectStudent2,notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentPassRepository,
                studentFailRepository);



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
