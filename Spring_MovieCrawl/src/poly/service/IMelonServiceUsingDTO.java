package poly.service;

import java.util.List;

import poly.dto.MelonDTO;

public interface IMelonServiceUsingDTO {
	
	public int collectMelonSongUsingDTO() throws Exception ;
	
	public List<MelonDTO> getSongList() throws Exception;
	
	public List<MelonDTO> getSingerSongCnt() throws Exception;




}
