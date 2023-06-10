package com.rponce.Parcial2.services;

import java.util.List;

import com.rponce.Parcial2.models.entities.Song;


public interface SongService {
	List<Song> findAll();
	List<Song> findByTitleContainingIgnoreCase(String title);
	Song findOneById(String id);
}
