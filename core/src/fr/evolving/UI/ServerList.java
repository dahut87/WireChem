package fr.evolving.UI;

import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import fr.evolving.assets.AssetLoader;
import fr.evolving.database.Base;
import fr.evolving.database.Base.datatype;
import fr.evolving.database.LocalBase;
import fr.evolving.database.SqlBase;

public class ServerList extends List {
	HashMap parameters;
	String url;
	Base.datatype model;
	Worldlist list;
	
	public ServerList(String url,Base.datatype model,Skin skin) {
		super(skin);
		this.url=url;
		this.model=model;
		parameters = new HashMap();
		parameters.put("version", "last");
	}
	
	public String getUrl() {
		return (String)this.getSelected();
	}
	
	public Base.datatype getModel() {
		return model;
	}
	
	public void setWorldlist(Worldlist list) {
		this.list=list;
	}

	public void Refresh() {
	 HttpRequest httpGet = new HttpRequest(HttpMethods.GET);
	 httpGet.setUrl(url);
	 httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));
     //If you want basic authentication, add this header
     String authHeader = "Basic " + Base64Coder.encodeString("evolving:--evolvE2016__");
     httpGet.setHeader("Authorization", authHeader);
     httpGet.setHeader("Content-Type", "text/xml");
     httpGet.setHeader("Accept", "text/xml");


	 Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
	        public void handleHttpResponse(HttpResponse httpResponse) {
	        	String Response = "";
	        	Array<Element> resultxml;
	        	Array<String> resultstring=new Array<String>();
	        	if (httpResponse.getStatus().getStatusCode()==200)
	        		Response = httpResponse.getResultAsString();
	        		XmlReader xml = new XmlReader();
	        		XmlReader.Element xml_element = xml.parse(Response);
	        		resultxml= xml_element.getChildrenByName("server");
	        		for(Element child : resultxml) 
	        			if (AssetLoader.Datahandler.isBackend(ServerList.this.model,child.getText()))
	        				resultstring.add(child.getText());
	        		String old=AssetLoader.Datahandler.getOld(ServerList.this.model);
	        		if (!resultstring.contains(old,false))
	        			resultstring.add(old);
	        		ServerList.this.setItems(resultstring);
	        		ServerList.this.setSelectedIndex(resultstring.indexOf(old, false));
	        		if (list!=null && ServerList.this.model==Base.datatype.gamedata)
	        			list.Refresh();
	        }
	        @Override
	        public void failed(Throwable t) {
	        	Array<String> resultstring=new Array<String>();
        		String old=AssetLoader.Datahandler.getOld(ServerList.this.model);
        		resultstring.add(old);
        		ServerList.this.setItems(resultstring);
        		ServerList.this.setSelectedIndex(resultstring.indexOf(old, false));
        		if (list!=null && ServerList.this.model==Base.datatype.gamedata)
        			list.Refresh();
	        }
	        
	        public void cancelled() {
       }
	 });
	}
}
