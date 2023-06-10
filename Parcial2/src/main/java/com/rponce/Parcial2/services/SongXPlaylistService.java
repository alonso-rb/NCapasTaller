package com.rponce.Parcial2.services;

import java.util.Date;
import java.util.List;

import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.Song;
import com.rponce.Parcial2.models.entities.SongXPlaylist;

public interface SongXPlaylistService {
	void createSongXPlaylist(Date date, Playlist playlist, Song song) throws Exception;
	void deleteById(String id) throws Exception;
	List<SongXPlaylist> findByPlaylist(Playlist playlist);
}