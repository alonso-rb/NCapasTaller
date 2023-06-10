package com.rponce.Parcial2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.User;

public interface PlaylistRepository extends ListCrudRepository<Playlist, UUID> {

	List<Playlist> findByTitle(String title);
	List<Playlist> findAllByUser(User user);
}
