package com.service;

import com.model.MarkSheet;

import java.util.List;

public interface MarkSheetService {
    List<MarkSheet> getMarksList(String role, Long userId);
}
