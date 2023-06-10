package com.rponce.Parcial2.services;

import java.util.List;

import com.rponce.Parcial2.models.dtos.SavePlaylistDTO;
import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.User;

public interface PlaylistService {
	void save(SavePlaylistDTO info, User userCode) throws Exception;
	List<Playlist> findAll();
	Playlist findOneById(String id);
	List<Playlist> findByTitle(String title);
	List<Playlist> findAllByUser(User user);
}
