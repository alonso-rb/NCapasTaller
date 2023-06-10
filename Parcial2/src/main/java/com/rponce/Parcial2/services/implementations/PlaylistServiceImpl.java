package com.rponce.Parcial2.services.implementations;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Parcial2.models.dtos.SavePlaylistDTO;
import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.User;
import com.rponce.Parcial2.repositories.PlaylistRepository;
import com.rponce.Parcial2.repositories.UserRepository;
import com.rponce.Parcial2.services.PlaylistService;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

	@Autowired
	private PlaylistRepository playlistRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SavePlaylistDTO info, User user) throws Exception {
		Playlist newPlaylist = new Playlist();
		newPlaylist.setTitle(info.getTitle());
		newPlaylist.setDescription(info.getDescription());
		newPlaylist.setUser(user);
		
		playlistRepository.save(newPlaylist);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Playlist findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return playlistRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Playlist> findByTitle(String title) {
		try {
			return playlistRepository.findByTitle(title);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Playlist> findAllByUser(User user) {
		try {
			return playlistRepository.findAllByUser(user);
		} catch (Exception e) {
			return null;
		}
		
	}
}
