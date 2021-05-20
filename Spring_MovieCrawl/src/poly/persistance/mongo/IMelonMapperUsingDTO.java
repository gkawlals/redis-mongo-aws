package poly.persistance.mongo;

import java.util.List;

import poly.dto.MelonDTO;

public interface IMelonMapperUsingDTO {
	
	public int insertSong(List<MelonDTO> pList, String colNm) throws Exception;
	
	
	public List<MelonDTO> getSongList(String colNm) throws Exception;
	
	
	
	public List<MelonDTO> getSingerCnt(String colNm) throws Exception;


	public List<MelonDTO> getSingerSongCnt(String colNm) throws Exception;
}
