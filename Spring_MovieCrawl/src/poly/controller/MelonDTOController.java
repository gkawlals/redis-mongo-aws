package poly.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.MelonDTO;
import poly.service.IMelonServiceUsingDTO;

@Controller
public class MelonDTOController {

	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "MelonServiceUsingDTO")
	private IMelonServiceUsingDTO melonService;
	
	@RequestMapping(value = "melon/collectMelonSongUsingDTO")
	@ResponseBody
	public String collectMelonRankUsingDTO(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info(this.getClass().getName() + ".collectMelonRankUsingDTO Start!");

		melonService.collectMelonSongUsingDTO();

		log.info(this.getClass().getName() + ".collectMelonRankUsingDTO End!");

		return "success";
	}
	
	@RequestMapping(value = "melon/getSongListUsingDTO")
	@ResponseBody
	public List<MelonDTO> getSongListUsingDTO(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info(this.getClass().getName() + ".getSongListUsingDTO Start!");

		List<MelonDTO> rList = melonService.getSongList();

		log.info(this.getClass().getName() + ".getSongListUsingDTO End!");

		return rList;
	}
	
	@RequestMapping(value = "melon/getSingerSongCntUsingDTO")
	@ResponseBody
	public List<MelonDTO> getSingerSongCntUsingDTO(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info(this.getClass().getName() + ".getSingerSongCntUsingDTO Start!");

		List<MelonDTO> rList = melonService.getSingerSongCnt();

		log.info(this.getClass().getName() + ".getSingerSongCntUsingDTO End!");

		return rList;
	}
}
