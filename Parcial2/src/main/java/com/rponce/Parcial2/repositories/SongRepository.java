package com.rponce.Parcial2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Parcial2.models.entities.Song;

public interface SongRepository extends ListCrudRepository<Song, UUID>{
	List<Song> findAll();
	List<Song> findByTitleContainingIgnoreCase(String title);
	
}
