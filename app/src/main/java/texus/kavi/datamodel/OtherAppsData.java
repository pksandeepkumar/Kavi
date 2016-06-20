package texus.kavi.datamodel;

import java.util.ArrayList;

import texus.kavi.xml.TexXmlElement;
import texus.kavi.xml.TexXmlParser;

public class OtherAppsData {

	public static final String FILE_NAME = "OtherApps";

    public String name = "";
	public String desc = "";
	public String app_link = "";
	public String icon = "";


//	<app name="App Name" desc="" app_link="" icon=""/>
	public static ArrayList<OtherAppsData> getParsed(String xml) {
		ArrayList<OtherAppsData> objects = new ArrayList<OtherAppsData>();
		TexXmlParser xmlTree = new TexXmlParser(xml);
		TexXmlElement rootElement = xmlTree.rootElement;
		if(rootElement != null) {
			for( TexXmlElement element: rootElement.children) {
				if(element == null) {
					continue;
				}
                OtherAppsData object =  new OtherAppsData();
                object.name = element.getAttribute("name");
				object.desc = element.getAttribute("desc");
				object.app_link = element.getAttribute("app_link");
				object.icon = element.getAttribute("icon");
                objects.add(object);
			}
		}
		return objects;
	}


}
