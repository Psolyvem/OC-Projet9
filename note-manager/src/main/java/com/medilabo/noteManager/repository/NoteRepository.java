package com.medilabo.noteManager.repository;

import com.medilabo.noteManager.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String>
{
	public List<Note> findByPatientId(int patientId);
}
