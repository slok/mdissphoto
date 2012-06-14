package org.mdissjava.mdisscore.view.searcher;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.mdissjava.mdisscore.solr.pojo.photo;
import org.mdissjava.mdisscore.solr.searcher.SolrImportDataMongo;
import org.mdissjava.mdisscore.solr.searcher.SolrImportDataMongoMain;

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
	private final String OPTION_TITLEFOTO ="photos";
	private final String OPTION_TITLEALBUM ="albums";
	private final String OPTION_TAGS ="tags";
	private final String OPTION_USERS ="users";
	
	private ArrayList<photo> photos;
	
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
			System.out.println("users");
			//TODO MYSQL query
		}
		
			//TODO MONGO query
		else if(this.selectedOption.equals(OPTION_TITLEFOTO)){
			System.out.println("searching... " + SOLR_MONGO_TITLEFOTO);
			importarFotosMongo(SOLR_MONGO_TITLEFOTO, searchText);
			System.out.println(OPTION_TITLEFOTO);
		}
		
		else if (this.selectedOption.equals(OPTION_TITLEALBUM)){
			importarFotosMongo(SOLR_MONGO_TITLEALBUM, searchText);
			System.out.println(OPTION_TITLEALBUM);
		}
		
		else if (this.selectedOption.equals(OPTION_TAGS)){
			importarFotosMongo(SOLR_MONGO_TAGS, searchText);
			System.out.println(OPTION_TAGS);
		}
			
		
	}

	public void importarFotosMongo(String selectedOption, String searchText) {
		try {			
			System.out.println("SearcherBean.importarFotosMongo()");
			//Invoke Load Mongo data function
			new SolrImportDataMongo();
			//create query
			List<String> Jarraysearch = SolrImportDataMongoMain.searchingByField(selectedOption, searchText);
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
			
			//Navigation to search-detail view
//			String outcome = "pretty:search-details";
//			FacesContext facesContext =  FacesContext.getCurrentInstance();
//			facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
//			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		
	
}
