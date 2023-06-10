package com.rponce.Parcial2.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.rponce.Parcial2.repositories.SongXPlaylistRepository;
import com.rponce.Parcial2.services.SongXPlaylistService;
import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.Song;
import com.rponce.Parcial2.models.entities.SongXPlaylist;

import jakarta.transaction.Transactional;

@Service
public class SongXPlaylistServiceImpl implements SongXPlaylistService{

	@Autowired
	private SongXPlaylistRepository songXplaylistRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createSongXPlaylist(Date date, Playlist playlist, Song song) throws Exception {
		SongXPlaylist songXplaylist = new SongXPlaylist(
					song,
					playlist,
					date
				);
		songXplaylistRepository.save(songXplaylist);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
			UUID code = UUID.fromString(id);
			songXplaylistRepository.deleteById(code);
	}

	@Override
	public List<SongXPlaylist> findByPlaylist(Playlist playlist) {
		try {
			return songXplaylistRepository.findAllByPlaylist(playlist);
		} catch (Exception error) {
			return null;
		}
	}

}