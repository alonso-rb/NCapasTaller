package com.rponce.Parcial2.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Parcial2.models.entities.Song;
import com.rponce.Parcial2.repositories.SongRepository;
import com.rponce.Parcial2.services.SongService;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository songRepository;
	
	@Override
	public List<Song> findAll() {
		return songRepository.findAll();
	}
	
	@Override
	public List<Song> findByTitleContainingIgnoreCase(String title) {
		try {
			return songRepository.findByTitleContainingIgnoreCase(title);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public Song findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return songRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}
}
