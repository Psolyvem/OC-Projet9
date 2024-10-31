package com.medilabo.noteManager.service;

import com.medilabo.noteManager.model.Note;
import com.medilabo.noteManager.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService
{
	NoteRepository noteRepository;

	public NoteService(NoteRepository noteRepository)
	{
		this.noteRepository = noteRepository;
	}

	public List<Note> getAll()
	{
		return noteRepository.findAll();
	}

	public Optional<Note> getNoteById(String id)
	{
		return noteRepository.findById(id);
	}

	public List<Note> getNoteByPatientId(int id)
	{
		return noteRepository.findByPatientId(id);
	}

	public void create(Note note)
	{
		noteRepository.insert(note);
		Logger.info("Creating note for patient n°" + note.getPatientId() + " with content : " + note.getContent());
	}

	public void update(Note note)
	{
		noteRepository.save(note);
	}

	public void delete(String id)
	{
		noteRepository.deleteById(id);
	}
	public void deleteAll()
	{
		noteRepository.deleteAll();
	}
}
