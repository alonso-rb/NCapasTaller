package com.rponce.Parcial2.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.SongXPlaylist;

public interface SongXPlaylistRepository 
extends ListCrudRepository<SongXPlaylist, UUID>{
		public List<SongXPlaylist> findAllByPlaylist(Playlist playlist);
}
