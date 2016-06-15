package texus.kavi.datamodel;

import java.util.ArrayList;

import texus.kavi.utility.Utility;
import texus.kavi.xml.TexXmlElement;
import texus.kavi.xml.TexXmlParser;

public class IndexData  {

//	<Index index_name="" latest_version="">
//	<file name="" version="" need_delete="" />

	public static String index_name = "";
	public static int latest_version = 0;

    public String name = "";
	public int version = 0;

	//If this is true means we have to delete all images from db
	public boolean need_delete = false;


	public static ArrayList<IndexData> getParsed( String xml) {
		ArrayList<IndexData> objects = new ArrayList<IndexData>();
		TexXmlParser xmlTree = new TexXmlParser(xml);
		TexXmlElement rootElement = xmlTree.rootElement;
		if(rootElement != null) {
			index_name = rootElement.getAttribute("index_name");
			latest_version = Utility.parseInt(rootElement.getAttribute("latest_version"));
			for( TexXmlElement element: rootElement.children) {
				if(element == null) {
					continue;
				}
				IndexData object =  new IndexData();
				object.name = element.getAttribute("name");
				object.need_delete = Utility.parseBoolean(element.getAttribute("need_delete"));
				object.version = Utility.parseInt(element.getAttribute("version"));
				objects.add(object);
			}
		}
		return objects;
	}

}
