package poly.service.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import poly.dto.MelonDTO;
import poly.persistance.mongo.impl.MelonMapperUsingDTO;
import poly.service.IMelonServiceUsingDTO;
import poly.util.CmmUtil;
import poly.util.DateUtil;

@Service("MelonServiceUsingDTO")
public class MelonServiceUsingDTO implements  IMelonServiceUsingDTO {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="MelonMapperUsingDTO")
	public MelonMapperUsingDTO melonMapper;

	@Override
	public int collectMelonSongUsingDTO() throws Exception {

		log.info(this.getClass().getName() + " .collectionMelonSong start !");
		
		int res = 0;
		
		List<MelonDTO> pList = new LinkedList<MelonDTO>();
		
		//멜론 Top100 중 50위 까지 정보 가져오기 페이지 
		String url = "https://www.melon.com/chart/index.htm";
		
		// JSOUP 라이브러리를 통해 사이트 접속되면, 그 사이트의 전체 HTML소스 저장할 변수 
		Document doc = null;
		
		doc = Jsoup.connect(url).get();
		
		//<div class ="service_list_song"> 이 태그 내에서 있는 html 소스만 element에 저장됨 
		Elements element = doc.select("div.service_list_song");
		
		
		// Iterator을 사용하여 멜론 차트 정보를 가쟈오기 
		Iterator<Element> songList = element.select("div.wrap_song_info").iterator();
		
		
		while (songList.hasNext())
		{
			Element songInfo = songList.next();
			
			//크롤링을 통해 데이터 저장하기 
			String song = CmmUtil.nvl(songInfo.select("div.ellipsis.rank01 a").text());// 노래 
			String singer = CmmUtil.nvl(songInfo.select("div.ellipsis.rank02 a").eq(0).text());// 가수
			
			log.info(" song : " + song);
			log.info("singer : " + singer);
			
			if((song.length() > 0 ) && (singer.length() > 0)) {
				MelonDTO pDTO = new MelonDTO();
				
				pDTO.setCollect_time(DateUtil.getDateTime("yyyyMMddhhmmss"));
				pDTO.setSong(song);
				pDTO.setSinger(singer);
				
				//한번에 여러개의 데이터를 MongoDB에 저장할 List 형태의 데이터 저장하기 
				pList.add(pDTO);
				
				// 사용이 완료되면 메모리 비우기 
				pDTO = null;
			}
			
			songInfo = null;
			
		}
		
		doc = null;
		
		//생성할 컬랙션 명 
		String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");
		
		melonMapper.insertSong(pList, colNm);
		
		log.info(this.getClass().getName() + " .collectionMelonSong end !");
		
		return res;
	}

	@Override
	public List<MelonDTO> getSongList() throws Exception {
		log.info(this.getClass().getName() + ".getSongList Start!");

		String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

		List<MelonDTO> rList = melonMapper.getSongList(colNm);

		if (rList == null) {
			rList = new LinkedList<MelonDTO>();
		}

		log.info(this.getClass().getName() + ".getSongList End!");

		return rList;
	}

	@Override
	public List<MelonDTO> getSingerSongCnt() throws Exception {
		log.info(this.getClass().getName() + ".getSingerSongCnt Start!");

		String colNm = "MELON_" + DateUtil.getDateTime("yyyyMMdd");

		List<MelonDTO> rList = melonMapper.getSingerSongCnt(colNm);

		if (rList == null) {
			rList = new LinkedList<MelonDTO>();
		}

		log.info(this.getClass().getName() + ".getSingerSongCnt End!");
		
		return rList;
	}

}
