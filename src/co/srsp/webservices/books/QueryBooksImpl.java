package co.srsp.webservices.books;

import java.util.List;

import javax.jws.WebService;
import co.srsp.webservices.books.model.BooksReviewsWSModel;

@WebService(endpointInterface = "co.srsp.webservices.books.QueryBooks")
public class QueryBooksImpl implements QueryBooks {

	//http://localhost:8080/SearchRetailServicesProject/queryBooks?wsdl
	
	@Override
	public BooksReviewsWSModel[] queryAuthor(String author) {
		 return queryBooks(null, author);
	}

	@Override
	public BooksReviewsWSModel[] queryTitle(String title) {
		return queryBooks(title, null);
	}

	@Override
	public BooksReviewsWSModel[] queryTitleAndAuthor(String title, String author) {
		// TODO Auto-generated method stub
		return queryBooks(title, author);
	}
	
	private BooksReviewsWSModel[] queryBooks(String title, String author){
		
	//	BooksAndReviewsService booksService = new BooksAndReviewsService();
	//	List<Books> books = booksService.searchBooksByTitleAndOrAuthor(title, author);	
	//	BooksReviewsWSModel[] returnModelArray = new BooksReviewsWSModel[books.size()];
	//	BooksReviewsWSModel model = null;
		
		int count = 0;
		
//		for(Books book : books){
//			model = new BooksReviewsWSModel();
//			model.setAuthorText(book.getAuthor());
//			model.setTitleText(book.getTitle());
//			model.setPublisherText(book.getPublisher());
//			model.setBooksID(book.getIdbooks());
//			returnModelArray[count] = model;
//			count++;
//		}
		
		return  null; //returnModelArray;
	}


}
