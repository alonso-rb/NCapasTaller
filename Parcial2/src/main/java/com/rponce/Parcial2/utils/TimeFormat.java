package com.rponce.Parcial2.utils;

import org.springframework.stereotype.Component;

@Component
public class TimeFormat {

	public String FormatToMMSS(Integer duration) {
		Integer min = duration / 60;
		Integer sec = duration % 60;
		
		return String.format("%02d:%02d", min , sec);
	}

}
