package com.medilabo.noteManager.controller;

import com.medilabo.noteManager.model.Note;
import com.medilabo.noteManager.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController
{
	NoteService noteService;

	public NoteController(NoteService noteService)
	{
		this.noteService = noteService;
	}

	@GetMapping("/notes")
	public List<Note> getNotes()
	{
		return noteService.getAll();
	}

	@GetMapping("/note/{id}")
	public Note getNoteById(@PathVariable String id)
	{
		try
		{
			return noteService.getNoteById(id).get();
		}
		catch (Exception e)
		{
			Logger.error("Note not found");
			return null;
		}
	}

	@GetMapping("/notes/{id}")
	public List<Note> getNoteByPatientId(@PathVariable int id)
	{
		return noteService.getNoteByPatientId(id);
	}

	@PostMapping("/notes")
	public ResponseEntity<String> createNote(@RequestBody Note note)
	{
		if (!(note == null) && note.getPatientId() > 0 && !note.getContent().isEmpty())
		{
			try
			{
				note.setId(null);
				noteService.create(note);
				return ResponseEntity.status(HttpStatus.CREATED).body("Note added for patient with id " + note.getPatientId());
			}
			catch (Exception e)
			{
				Logger.error("Unable to add note");
				return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
			}
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("/notes/{id}")
	public ResponseEntity<String> updateNote(@PathVariable String id, @RequestBody Note note)
	{
		if (!(note == null) && note.getPatientId() > 0 && !note.getContent().isEmpty())
		{
			try
			{
				note.setId(id);
				noteService.update(note);
				return ResponseEntity.status(HttpStatus.OK).body("Note updated for patient with id " + note.getPatientId());
			}
			catch (Exception e)
			{
				Logger.error("Unable to update note");
				return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
			}
		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/notes/{id}")
	public ResponseEntity<String> deleteNote(@PathVariable String id)
	{
		try
		{
			noteService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body("Note deleted");
		}
		catch (Exception e)
		{
			Logger.error("Unable to delete note");
			return ResponseEntity.status(HttpStatusCode.valueOf(500)).build();
		}
	}

	@GetMapping("/setup")
	public void setupDB()
	{
		Logger.info("Setting up DB");
		noteService.deleteAll();
		Note note1 = new Note();
		Note note2 = new Note();
		Note note3 = new Note();
		Note note4 = new Note();
		Note note5 = new Note();
		Note note6 = new Note();
		Note note7 = new Note();
		Note note8 = new Note();
		Note note9 = new Note();
		note1.setPatientId(1);
		note1.setContent("Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");
		noteService.create(note1);
		note2.setPatientId(2);
		note2.setContent("Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
		noteService.create(note2);
		note3.setPatientId(2);
		note3.setContent("Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
		noteService.create(note3);
		note4.setPatientId(3);
		note4.setContent("Le patient déclare qu'il fume depuis peu");
		noteService.create(note4);
		note5.setPatientId(3);
		note5.setContent("Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
		noteService.create(note5);
		note6.setPatientId(4);
		note6.setContent("Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
		noteService.create(note6);
		note7.setPatientId(4);
		note7.setContent("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
		noteService.create(note7);
		note8.setPatientId(4);
		note8.setContent("Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");
		noteService.create(note8);
		note9.setPatientId(4);
		note9.setContent("Taille, Poids, Cholestérol, Vertige et Réaction");
		noteService.create(note9);
	}
}
