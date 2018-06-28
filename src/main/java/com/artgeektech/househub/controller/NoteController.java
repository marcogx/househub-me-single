package com.artgeektech.househub.controller;

import com.artgeektech.househub.domain.Note;
import com.artgeektech.househub.exception.ResourceNotFoundException;
import com.artgeektech.househub.repository.NoteRepository;
import com.artgeektech.househub.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by guang on 4:04 PM 4/21/18.
 */
@Controller
@RequestMapping("/api")
public class NoteController {
    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    // Get All Notes
    @GetMapping("/notes/test2")
    @ResponseBody
    public List<Note> testNotes2() {
        return noteRepository.findBySearchTerm("", 0, 100);
    }

    // Get All Notes
    @GetMapping("/notes/test3")
    @ResponseBody
    public Long testNotes3() {
        return noteRepository.findCount("marco");
    }


    // Get All Notes
    @GetMapping("/notes/test1")
    @ResponseBody
    public List<Note> testNotes() {
        return noteRepository.findByContentIgnoreCaseContaining("marco");
    }

    // Get All Notes
    @GetMapping("/notes")
    @ResponseBody
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Create a new Note
    @PostMapping("/notes")
    @ResponseBody
    public Note createNote(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Create a new Note via form
    @PostMapping("/form/notes")
    public String createNoteForm(@Valid @RequestBody Note note, ModelMap modelMap) {
        noteRepository.save(note);
        return "homepage/index";
    }

    // Create a new Note via form
    @GetMapping("/form/notes")
    public String createNoteForm2(Note note, ModelMap modelMap) {
//        noteRepository.save(note);
        return "homepage/index";
    }

    // Get a Single Note
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    // Update a Note
    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody Note noteDetails) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updatedNote = noteRepository.save(note);
        return updatedNote;
    }

    // Delete a Note
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
