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

import fr.evolving.database.Base;
import fr.evolving.database.Base.datatype;
import fr.evolving.database.LocalBase;
import fr.evolving.database.SqlBase;

public class ServerList extends List {
	HashMap parameters;
	String url;
	Base.datatype model;
	
	public ServerList(String url,Base.datatype model,Skin skin) {
		super(skin);
		this.url=url;
		this.model=model;
		parameters = new HashMap();
		parameters.put("version", "last");
		Refresh();
	}
	
	public boolean getBackend(String base,datatype model) {
		String[] realbase=base.split(":");
		if (realbase[0].contains("mysql"))
			return SqlBase.isHandling(model);
		else
			return LocalBase.isHandling(model);
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
	        			if (getBackend(child.getText(),ServerList.this.model))
	        				resultstring.add(child.getText());
	        		ServerList.this.setItems(resultstring);
	        			
	        }
	        @Override
	        public void failed(Throwable t) {
	        }
	        
	        public void cancelled() {
       }
	 });
	}
}
