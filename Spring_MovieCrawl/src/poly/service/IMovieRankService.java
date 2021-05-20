package poly.service;

import java.util.List;

import poly.dto.MovieDTO;

public interface IMovieRankService {
	
	List<MovieDTO> getMovieRank(MovieDTO pDTO) throws Exception;
}
