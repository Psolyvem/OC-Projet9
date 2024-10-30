package com.medilabo.webclient.controller;

import com.medilabo.webclient.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "note-manager", url = "localhost:9100/note-manager")
public interface NoteManagerClient
{
	@GetMapping("/notes")
	List<Note> getAllNotes();

	@GetMapping("/note/{id}")
	Note getNoteById(@PathVariable String id);

	@GetMapping("/notes/{id}")
	List<Note> getNotesByPatientId(@PathVariable int id);

	@PostMapping("/notes")
	ResponseEntity<String> createNote(@RequestBody Note note);

	@PutMapping("/notes/{id}")
	ResponseEntity<String> updateNote(@PathVariable String id, Note note);

	@DeleteMapping("/notes/{id}")
	ResponseEntity<String> deleteNote(@PathVariable String id);
}
