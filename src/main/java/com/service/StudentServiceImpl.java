package com.service;
import com.Utility.AppUtils;
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
        student.setStudentName(studentRequest.getStudentName());
        student.setEmail(studentRequest.getEmail());
        student.setPhone(studentRequest.getPhone());
        student.setDob(studentRequest.getDob());
        student.setGender(studentRequest.getGender());
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
        student.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
        student.setUpdatedOn(Timestamp.valueOf(LocalDateTime.now()));

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

    @Override
    public Student StudentDetails(StudentRequest studentRequest) {
        Student student =studentRepository.findByEmail(studentRequest.getEmail(),true);
        if (studentRequest.getStudentName() != null) {
            student.setStudentName(studentRequest.getStudentName());
        }
        if (studentRequest.getPhone() != null) {
            student.setPhone(studentRequest.getPhone());
        }
        if (studentRequest.getDob() != null) {
            student.setDob(studentRequest.getDob());
        }
        if (studentRequest.getGender() != null) {
            student.setGender(studentRequest.getGender());
        }
        if (studentRequest.getOptionalSubject() != null) {
            student.setOptionalSubject(studentRequest.getOptionalSubject());
        }
        if (studentRequest.getProfilePic() != null) {
            student.setProfilePic(studentRequest.getProfilePic());
        }
        if (studentRequest.getAddress() != null) {
            student.setAddress(studentRequest.getAddress());
        }
        if (studentRequest.getPinCode() != null) {
            student.setPinCode(studentRequest.getPinCode());
        }
        if (studentRequest.getFatherName() != null) {
            student.setFatherName(studentRequest.getFatherName());
        }
        if (studentRequest.getFatherOccupation() != null) {
            student.setFatherOccupation(studentRequest.getFatherOccupation());
        }
        if (studentRequest.getMotherName() != null) {
            student.setMotherName(studentRequest.getMotherName());
        }
        if (studentRequest.getMotherOccupation() != null) {
            student.setMotherOccupation(studentRequest.getMotherOccupation());
        }
        if (studentRequest.getAdmissionDate() != null) {
            student.setAdmissionDate(studentRequest.getAdmissionDate());
        }
        if (studentRequest.getSemOrYear() != null) {
            student.setSemOrYear(studentRequest.getSemOrYear());
        }
        student.setUpdatedOn(AppUtils.getCurrentIstTime());
        Student student1 = studentRepository.save(student);
        return student1;
    }


    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
    }

//    public Student updateStudent(Long studentId ,StudentRequest studentRequest) {
//        Student student = findById(studentId);
//        student.setFirstName(studentRequest.getFirstName());
//        student.setLastName(studentRequest.getLastName());
//        student.setEmail(studentRequest.getEmail());
//        student.setPhone(studentRequest.getPhone());
//        student.setDob(studentRequest.getDob());
//        student.setGender(studentRequest.getGender());
//        student.setState(studentRequest.getState());
//        student.setCity(studentRequest.getCity());
//        student.setRollNumber(studentRequest.getRollNumber());
//        student.setOptionalSubject(studentRequest.getOptionalSubject());
//        student.setProfilePic(studentRequest.getProfilePic());
//        student.setAddress(studentRequest.getAddress());
//        student.setPinCode(studentRequest.getPinCode());
//        student.setFatherName(studentRequest.getFatherName());
//        student.setFatherOccupation(studentRequest.getFatherOccupation());
//        student.setMotherName(studentRequest.getMotherName());
//        student.setMotherOccupation(studentRequest.getMotherOccupation());
//        student.setAdmissionDate(studentRequest.getAdmissionDate());
//        student.setCourseId(studentRequest.getCourseId());
//        student.setSemOrYear(studentRequest.getSemOrYear());
//        student.setActiveStatus(studentRequest.getActiveStatus());
//        student.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
//        return studentRepository.save(student);
//    }

}
