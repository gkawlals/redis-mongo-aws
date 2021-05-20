package poly.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import poly.dto.MovieDTO;
import poly.service.IMovieRankService;
import poly.util.CmmUtil;
import poly.util.DateUtil;


@Controller
public class MovieRankController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "MovieRankService")
	private IMovieRankService movieRankService;
	
	@RequestMapping(value = "rank/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info(this.getClass().getName() + ".index Start!");
		
		log.info(this.getClass().getName() + ".index Out");
		
		return "/rank/index";
	}
	
	@RequestMapping(value = "rank/movie")
	@ResponseBody
	public List<MovieDTO> rank(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info(this.getClass().getName() + ".rank Start!");
		
		List<MovieDTO> rList = null;
		
		String send_msg = CmmUtil.nvl(request.getParameter("send_msg"));
		
		if ((send_msg.indexOf("영화") > -1) || (send_msg.indexOf("영하") > -1) || (send_msg.indexOf("연하") > -1) 
				|| (send_msg.indexOf("연화") > -1) && ((send_msg.indexOf("순위") > -1) || (send_msg.indexOf("순이") > -1))) {
			
			MovieDTO pDTO = new MovieDTO();
			
			pDTO.setRank_check_time(DateUtil.getDateTime("yyyyMMdd"));
			
			rList = movieRankService.getMovieRank(pDTO);
			
			if (rList == null) {
				rList = new ArrayList<MovieDTO>();
			
			}
		}
		
		log.info(this.getClass().getName() + ".rank End!");
		
		return rList;
	}
			
}
