package com.artgeektech.househub.service;

import com.artgeektech.househub.domain.Note;
import com.artgeektech.househub.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guang on 10:27 AM 5/9/18.
 */
@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;


    public List<Note> pageing(int pageNum, int pageSize) {
        return null;
    }
}
