package com.service;
import com.model.Student;
import com.payload.request.StudentRequest;
import com.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentRepository studentRepository;
    @Override
    public Student registerStudent(StudentRequest studentRequest) {

        Student student = new Student();
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setState(studentRequest.getState());
        student.setCity(studentRequest.getCity());
        student.setRollNumber(studentRequest.getRollNumber());
        student.setOptionalSubject(studentRequest.getOptionalSubject());
        student.setProfilePic(studentRequest.getProfilePic());
        student.setAddress(studentRequest.getAddress());
        student.setPinCode(studentRequest.getPinCode());
        student.setFatherName(studentRequest.getFatherName());
        student.setFatherOccupation(studentRequest.getFatherOccupation());
        student.setMotherName(studentRequest.getMotherName());
        student.setMotherOccupation(studentRequest.getMotherOccupation());
        student.setAdmissionDate(studentRequest.getAdmissionDate());
        student.setCourseId(studentRequest.getCourseId());
        student.setSemOrYear(studentRequest.getSemOrYear());
        student.setActiveStatus(true);
        student.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        student.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));
        student.setUserId(studentRequest.getUserId());
        student.setPassword(studentRequest.getPassword());

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentList(Long courseId, String semOrYear, String rollNumber) {
        if (courseId != null && semOrYear != null && rollNumber != null) {
            return studentRepository.findByCourseIdAndSemOrYearAndRollNumber(courseId, semOrYear, rollNumber);
        } else if (courseId != null && semOrYear != null) {
            return studentRepository.findByCourseIdAndSemOrYear(courseId, semOrYear);
        } else {
            return studentRepository.findAll();
        }
    }


    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long studentId ,StudentRequest studentRequest) {
        Student student = findById(studentId);
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
        student.setState(studentRequest.getState());
        student.setCity(studentRequest.getCity());
        student.setRollNumber(studentRequest.getRollNumber());
        student.setOptionalSubject(studentRequest.getOptionalSubject());
        student.setProfilePic(studentRequest.getProfilePic());
        student.setAddress(studentRequest.getAddress());
        student.setPinCode(studentRequest.getPinCode());
        student.setFatherName(studentRequest.getFatherName());
        student.setFatherOccupation(studentRequest.getFatherOccupation());
        student.setMotherName(studentRequest.getMotherName());
        student.setMotherOccupation(studentRequest.getMotherOccupation());
        student.setAdmissionDate(studentRequest.getAdmissionDate());
        student.setCourseId(studentRequest.getCourseId());
        student.setSemOrYear(studentRequest.getSemOrYear());
        student.setActiveStatus(studentRequest.getActiveStatus());
        student.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
        return studentRepository.save(student);
    }

}
