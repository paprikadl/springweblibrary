package by.project.library.springweblibrary.dao;

import by.project.library.springweblibrary.domain.Book;

public interface BookDao extends GeneralDao<Book>{

    //List<Book> findTopBooks (int limit);

    byte[] getContent (long id);

    //Page<Book> findByGenre(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection, long genreId);

    void updateViewCount (long viewCount, long id);

    void updateRating (long totalRating, long totalVoteCount, int avgRating, long id);

}
