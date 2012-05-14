package org.mdissjava.solr.mongodb;

import org.apache.solr.client.solrj.beans.Field;

public class album {
//	@Field
//	private String id;
//	@Field
//	private String photoId;
	@Field
	private String titleAlbum;
//	@Field
//	private Album album;
//	@Field
//	private String publicPhoto;
//	@Field
//	private String votes;
//	@Field
//	private String uploadDate;
//	@Field
//	private String nextPhoto;
//	@Field
//	private String backwardPhoto;
//	@Field
//	private String metadata;
//	@Field
//	private List<String> tags;
//	@Field
//	private String dataId;
//	@Field
//	private String plus18;
	
	@Override
	public String toString(){
//		return "ID: " + id + " , title: " + title+ " , album: " + album + " , publicPhoto: " + publicPhoto + 
//				" , votes: " + votes + " , uploadDate: " + uploadDate + " , nextPhoto: " + nextPhoto + 
//				" , backwardPhoto: " +	backwardPhoto + " , metadata: " + metadata + " , tags: " + tags +
//				" , dataId: " + dataId + " , plus18: " + plus18;
//		
		return "Title: " + titleAlbum;
	}
}
