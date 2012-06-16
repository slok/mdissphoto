package org.mdissjava.mdisscore.view.searcher;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.mdissjava.mdisscore.solr.pojo.photo;
import org.mdissjava.mdisscore.solr.pojo.users;
import org.mdissjava.mdisscore.solr.searcher.CommonsHttpSolrDataMongo;
import org.mdissjava.mdisscore.solr.searcher.CommonsHttpSolrDataMySQL;
import org.mdissjava.mdisscore.solr.searcher.SolrImportDataMongo;
import org.mdissjava.mdisscore.solr.searcher.SolrImportDataMySQL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@ViewScoped
@ManagedBean
public class SearcherBean {

	private List<String> searchOptions;
	private String selectedOption;
	private String searchText;
	private photo photoPojoSolr;
	
	private final String SOLR_MONGO_TITLEFOTO ="titleFoto";
	private final String SOLR_MONGO_TITLEALBUM ="titleAlbum";
	private final String SOLR_MONGO_TAGS ="tags";
	private final String SOLR_MYSQL_USERS ="nick";
	private final String OPTION_TITLEFOTO ="photos";
	private final String OPTION_TITLEALBUM ="albums";
	private final String OPTION_TAGS ="tags";
	private final String OPTION_USERS ="users";
//	private final int MAXENTRIES_PHOTO = 5;
	private int currentPage;
	
	private ArrayList<photo> photos;
	private ArrayList<users> users;
	
	public SearcherBean()
	{		
		try {
			this.searchOptions = new ArrayList<String>();
			this.searchOptions.add("photos");
			this.searchOptions.add("albums");
			this.searchOptions.add("tags");
			this.searchOptions.add("users");

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}
	
	public void search()
	{		
		System.out.println("Search option: " + this.selectedOption);
		
		if(this.selectedOption.equals(OPTION_USERS)){
			importHttpSolrDataMysql(SOLR_MYSQL_USERS, searchText);
			System.out.println(OPTION_USERS);			
		}
		
		else if(this.selectedOption.equals(OPTION_TITLEFOTO)){
			System.out.println("searching... " + SOLR_MONGO_TITLEFOTO);
			importHttpSolrDataMongo(SOLR_MONGO_TITLEFOTO, searchText);
			System.out.println(OPTION_TITLEFOTO);
		}
		
		else if (this.selectedOption.equals(OPTION_TITLEALBUM)){
			importHttpSolrDataMongo(SOLR_MONGO_TITLEALBUM, searchText);
			System.out.println(OPTION_TITLEALBUM);
		}
		
		else if (this.selectedOption.equals(OPTION_TAGS)){
			importHttpSolrDataMongo(SOLR_MONGO_TAGS, searchText);
			System.out.println(OPTION_TAGS);
		}
			
		
	}

	/**
	 * import data from table photos of Mongo
	 * @param selectedOption
	 * @param searchText
	 */
	public void importarFotosMongo(String selectedOption, String searchText) {
		System.out.println("SearcherBean.importarFotosMongo()");
		if(!searchText.equals("")) {
			try {			
				//Invoke Load Mongo data function
				new SolrImportDataMongo();
				//create query
				List<String> Jarraysearch = SolrImportDataMongo.searchingByField(selectedOption, searchText);
				System.out.println("tam lista coincidencias searchingByField: " + Jarraysearch.size());
				
				//Retrieve JSON data to parse to Pojo class called 'photo'
				JsonParser parser = new JsonParser();
				this.photos = new ArrayList<photo>();			
				for (String jstring : Jarraysearch) {
					JsonObject jsonObject = parser.parse(jstring).getAsJsonObject();
					photo fromJson = new Gson().fromJson(jsonObject, photo.class);
	//				System.out.println("fromJson: " + fromJson.getTitleFoto());				
					this.photos.add(fromJson);
				}	
				//TODO llamar al metodo photoPagination	(cargar el valor de currentPage)		
	//			this.photoPagination(currentPage, MAXENTRIES_PHOTO);
				//LISTAR DATOS
				int i= 0;
				System.out.println("lista photos: " + this.photos.size());
				for (photo p : this.photos) {
					i += 1;
					System.out.print("Ind " + i + ": " + "Photo title: " + p.getTitleFoto() + " , Album Title: " + p.getTitleAlbum() + " , Tags: ");				
					String[] tags = p.getTags();
					for (String tag : tags) {
						System.out.print("[" + tag + ", ");
					}
					System.out.print("]");
					System.out.println();
				}						

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  import data from table users of Mysql
	 * @param selectedOption
	 * @param searchText
	 */
	public void importarUsersMysql(String selectedOption, String searchText) {
		System.out.println("SearcherBean.importarUsersMysql()");
		if(!searchText.equals("")) {
			//Invoke Load MySql data function
			try {
				new SolrImportDataMySQL();
				
				//create query
				List<String> Jarraysearch = SolrImportDataMySQL.searchingByField(selectedOption, searchText);
				System.out.println("tam lista coincidencias searchingByField: " + Jarraysearch.size());
				
				//Retrieve JSON data to parse to Pojo class called 'users'
				JsonParser parser = new JsonParser();
				this.users = new ArrayList<users>();			
				for (String jstring : Jarraysearch) {
					JsonObject jsonObject = parser.parse(jstring).getAsJsonObject();
					users fromJson = new Gson().fromJson(jsonObject, users.class);
	//			System.out.println("fromJson: " + fromJson.getTitleFoto());				
					this.users.add(fromJson);
				}	
				int i= 0;
				System.out.println("lista users: " + this.users.size());
				for (users u : this.users) {
					i += 1;
					System.out.println("Ind " + i + ": " + "Username: " + u.getName() + " , surname: " + u.getSurname() + " , nick: " + u.getNick());				
				}
							
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	/**
	 *  Get the range of photoArray to show in the table
	 * @param currentPage
	 * @param maxEntries
	 * @return List<photo>
	 */
	private List<photo> photoPagination(int currentPage, int maxEntries) {
		List<photo> photos = new ArrayList<photo>();
		int startRange = maxEntries * (currentPage - 1);
		int finalRange = (startRange + maxEntries) - 1;
		List<photo> photoRange = new ArrayList<photo>(); 
		for(int i= startRange; i< finalRange; i++){
			photoRange.add(photos.get(i));
		}	
		return photoRange;

	}

	/**
	 * import data from table photos of Mongo connecting to the server
	 * @param selectedOption
	 * @param searchText
	 */
	public void importHttpSolrDataMongo(String selectedOption, String searchText){
		System.out.println("SearcherBean.importHttpSolrDataMongo()");
		if(!searchText.equals("")) {
			try {			
				//Invoke Load Mongo data function
				new CommonsHttpSolrDataMongo();
				//create query
				List<String> Jarraysearch = CommonsHttpSolrDataMongo.searchingByField(selectedOption, searchText);
				System.out.println("tam lista coincidencias searchingByField: " + Jarraysearch.size());
				
				//Retrieve JSON data to parse to Pojo class called 'photo'
				JsonParser parser = new JsonParser();
				this.photos = new ArrayList<photo>();			
				for (String jstring : Jarraysearch) {
					JsonObject jsonObject = parser.parse(jstring).getAsJsonObject();
					photo fromJson = new Gson().fromJson(jsonObject, photo.class);
	//				System.out.println("fromJson: " + fromJson.getTitleFoto());				
					this.photos.add(fromJson);
				}	
				//TODO llamar al metodo photoPagination	(cargar el valor de currentPage)		
	//			this.photoPagination(currentPage, MAXENTRIES_PHOTO);
				//LISTAR DATOS
				int i= 0;
				System.out.println("lista photos: " + this.photos.size());
				for (photo p : this.photos) {
					i += 1;
					System.out.print("Ind " + i + ": " + "Photo title: " + p.getTitleFoto() + " , Album Title: " + p.getTitleAlbum() + " , Tags: ");				
					String[] tags = p.getTags();
					for (String tag : tags) {
						System.out.print("[" + tag + ", ");
					}
					System.out.print("]");
					System.out.println();
				}						

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	/**
	 *  import data from table users of Mysql connecting to the server
	 * @param selectedOption
	 * @param searchText
	 */
	public void importHttpSolrDataMysql(String selectedOption, String searchText) {
		System.out.println("SearcherBean.importarHttpSolrDataMysql()");
		if(!searchText.equals("")) {
			//Invoke Load MySql data function
			try {
				new CommonsHttpSolrDataMySQL();
				
				//create query
				List<String> Jarraysearch = CommonsHttpSolrDataMySQL.searchingByField(selectedOption, searchText);
				System.out.println("tam lista coincidencias searchingByField: " + Jarraysearch.size());
				
				//Retrieve JSON data to parse to Pojo class called 'users'
				JsonParser parser = new JsonParser();
				this.users = new ArrayList<users>();			
				for (String jstring : Jarraysearch) {
					JsonObject jsonObject = parser.parse(jstring).getAsJsonObject();
					users fromJson = new Gson().fromJson(jsonObject, users.class);
	//			System.out.println("fromJson: " + fromJson.getTitleFoto());				
					this.users.add(fromJson);
				}	
				int i= 0;
				System.out.println("lista users: " + this.users.size());
				for (users u : this.users) {
					i += 1;
					System.out.println("Ind " + i + ": " + "Username: " + u.getName() + " , surname: " + u.getSurname() + " , nick: " + u.getNick());				
				}
							
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	

	public List<String> getSearchOptions() {
		return searchOptions;
	}

	public void setSearchOptions(List<String> searchOptions) {
		this.searchOptions = searchOptions;
	}

	public photo getPhotoPojoSolr() {
		return photoPojoSolr;
	}

	public void setPhotoPojoSolr(photo photoPojoSolr) {
		this.photoPojoSolr = photoPojoSolr;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public photo getPhotoClass() {
		return photoPojoSolr;
	}

	public void setPhotoClass(photo photoClass) {
		this.photoPojoSolr = photoClass;
	}

	public ArrayList<photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<photo> photos) {
		this.photos = photos;
	}

	public ArrayList<users> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<users> users) {
		this.users = users;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

		
}
