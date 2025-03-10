package com.service;

import com.model.RollGenerator;
import com.payload.request.RollNumberRequest;
import com.repository.RollGeneratorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RollGeneratorServiceImpl implements RollGeneratorService{
    private static final Logger logger = LoggerFactory.getLogger(RollGeneratorServiceImpl.class);

    @Autowired
    RollGeneratorRepository rollGeneratorRepository;
    @Override
    public RollGenerator addRollNumber(RollNumberRequest rollNumberRequest) {
        RollGenerator rollGenerator = new RollGenerator();
        if(rollNumberRequest.getId() !=null){
            rollGenerator = rollGeneratorRepository.findByIdAndIsActive(rollNumberRequest.getId(),true);
        }
        if(null != rollNumberRequest.getCourseId()){
            rollGenerator.setCourseId(rollNumberRequest.getCourseId());
        }
        if(null != rollNumberRequest.getRollNumber()){
            rollGenerator.setRollNumber(rollNumberRequest.getRollNumber());
        }
        if(null != rollNumberRequest.getSemOrYear()){
            rollGenerator.setSemOrYear(rollNumberRequest.getSemOrYear());
        }
        RollGenerator rollGenerator1 = rollGeneratorRepository.save(rollGenerator);
        logger.info("roll number added successfully");
        return  rollGenerator1;

    }

    @Override
    public RollGenerator getRollNumberByCourseIdAndSem(Long courseId, String semOrYear) {
        RollGenerator rollGenerator = rollGeneratorRepository.findByCourseIdAndSem(courseId,semOrYear,true);
        if(null == rollGenerator){
            logger.info("there is no roll number");
        }
        logger.info("get course Id and sem Or year");
        return rollGenerator;
    }

    @Override
    public Boolean deleteRollNumber(Long id) {
        Integer isDeleted = rollGeneratorRepository.deleteRollNumber(id);
        if(isDeleted !=0 ){
            logger.info("roll number deleted successfully");
            return  true;
        }
        return false;
    }

}
