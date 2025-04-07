package com.service;

import com.model.MarkSheet;
import com.repository.MarkSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MarkSheetServiceImpl implements MarkSheetService{
    private static final Logger logger = LoggerFactory.getLogger(MarkSheetServiceImpl.class);
    @Autowired
    MarkSheetRepository markSheetRepository;
    @Override
    public List<MarkSheet> getMarksList(String role, Long userId) {
        List<MarkSheet> studentMarks = new ArrayList<>();

        if ("admin".equals(role)) {
            studentMarks = markSheetRepository.findByIsActive(true);
        } else if ("faculty".equals(role)) {
            studentMarks = markSheetRepository.findByUserId(userId, true);
        }

        if (studentMarks == null || studentMarks.isEmpty()) {
            logger.warn("Student marks list is empty");
            return Collections.emptyList();
        }

        List<MarkSheet> markSheetList = new ArrayList<>();
        for (MarkSheet ms : studentMarks) {
            MarkSheet markSheet = new MarkSheet();
            markSheet.setId(ms.getId());
            markSheet.setExamRecordId(ms.getExamRecordId());
            markSheet.setRollNumber(ms.getRollNumber());
            markSheet.setSubjectId(ms.getSubjectId());
            markSheet.setQ1(ms.getQ1());
            markSheet.setQ2(ms.getQ2());
            markSheet.setQ3(ms.getQ3());
            markSheet.setQ4(ms.getQ4());
            markSheet.setQ5(ms.getQ5());
            markSheet.setQ6(ms.getQ6());
            markSheet.setQ7(ms.getQ7());
            markSheet.setQ8(ms.getQ8());
            markSheet.setQ9(ms.getQ9());
            markSheet.setQ10(ms.getQ10());
            markSheet.setTotalMarks(ms.getTotalMarks());
            markSheet.setIsActive(ms.getIsActive());
            markSheet.setCreatedBy(ms.getCreatedBy());
            markSheet.setUpdatedBy(ms.getUpdatedBy());
            markSheet.setCreatedOn(ms.getCreatedOn());
            markSheet.setUpdatedOn(ms.getUpdatedOn());
            markSheetList.add(markSheet);
        }

        logger.info("Fetched student marks list successfully");
        return markSheetList;
    }


}
