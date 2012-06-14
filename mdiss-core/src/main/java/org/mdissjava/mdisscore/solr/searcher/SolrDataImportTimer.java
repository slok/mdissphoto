package org.mdissjava.mdisscore.solr.searcher;

//@Stateless
//public class SolrDataImportTimer {
//	
//	public timerSolr() {
//        System.out.println("-----Timer Started-----");
//    }
//
//     
//@SuppressWarnings("unused")
//@Schedule(second="*", minute="*/30", hour="*", dayOfWeek="Mon-Sun",  dayOfMonth="*", month="*", year="*", info="SolrTimer", persistent = false)
//	private void scheduledTimeout(final Timer t) {
//	
//	URL url;
//	HttpURLConnection conn;
//    String SolrJBoss = "http://jboss.mdiss.info/solr/mysql/dataimport?command=full-import";
//    
//    try {
//
//        url = new URL(SolrJBoss);
//        conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("type", "submit");
//        conn.setDoOutput(true);
//        conn.connect();
//
//        if(conn.getResponseCode() != 200){
//        	System.out.println("DataImport Failed");
//        }
//
//        conn.disconnect();
//       } catch (Exception e) {
//    	   e.printStackTrace();
//       }
//
//   }
	
//}
