package com.rodrigomoreira.myapp.repositories;

import static com.rodrigomoreira.myapp.constants.CourseConstants.COURSE;
import static com.rodrigomoreira.myapp.constants.CourseConstants.INVALID_COURSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.rodrigomoreira.myapp.domain.courses.Course;

@DataJpaTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    void afterEach(){
        COURSE.setId(null);
    }

    @Test
    void testCreateCourse_SuccessfulCase() {
        Course course = courseRepository.save(COURSE);
        Course sut = testEntityManager.find(Course.class, course.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(COURSE.getName());
    }

    @Test
    void testCreateCourse_ErrorCase1() {
        Course emptyCourse = new Course();

        assertThatThrownBy(() -> courseRepository.save(emptyCourse)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> courseRepository.save(INVALID_COURSE)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreateCourse_ErrorCase2() {
        Course course = testEntityManager.persistFlushFind(COURSE);
        testEntityManager.detach(course);
        course.setId(null);

        assertThatThrownBy(() -> courseRepository.save(course))
            .isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindCourseById_SuccessfulCase() {
        Course course = testEntityManager.persistFlushFind(COURSE);

        Optional<Course> result = courseRepository.findCourseById(course.getId());
        
        assertThat(result.isPresent()).isTrue();

    }

    @Test
    void testFindCourseById_ErrorCase() {
        Optional<Course> result = courseRepository.findCourseById(COURSE.getId());

        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void testFindCourseByName_SuccessfulCase() {
        Course course = testEntityManager.persistFlushFind(COURSE);

        Optional<Course> result = courseRepository.findCourseByName(course.getName());
        
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void testFindCourseByName_ErrorCase() {
        Optional<Course> result = courseRepository.findCourseByName(COURSE.getName());
        
        assertThat(result.isEmpty()).isTrue();
    }
}
