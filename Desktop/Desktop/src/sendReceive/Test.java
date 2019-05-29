package sendReceive;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Image;

public class Test {
	
	public static void main(String argv[]) {
		try {
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		    //root elements
		    Document doc = docBuilder.newDocument();
		
		    Element user = doc.createElement("user");
		    doc.appendChild(user);
		    
		    //set attribute to user element
		    Attr username = doc.createAttribute("username");
		    username.setValue("Pera");
		    user.setAttributeNode(username);
		
		    for (Image i : Receive.xml.getImages()) {
			    //image elements
			    Element img = doc.createElement("img");
			    user.appendChild(img);
			
			    //set attribute to image element
			    Attr name = doc.createAttribute("imgname");
			    name.setValue(i.getName());
			    img.setAttributeNode(name);
			    
			    //set attribute to image element
			    Attr size = doc.createAttribute("imgsize");
			    size.setValue(Long.toString(i.getSize()));
			    img.setAttributeNode(size);
			    
			    //set attribute to image element
			    Attr hash = doc.createAttribute("imghash");
			    hash.setValue(i.getHash());
			    img.setAttributeNode(hash);
		    }
		    //date elements
		    Element date = doc.createElement("date");
		    user.appendChild(date);
		    
		    //set attribute to date element
		    Attr datee = doc.createAttribute("datee");
		    datee.setValue("1");
		    date.setAttributeNode(datee);
		
		    //write the content into xml file
		    TransformerFactory transformerFactory =  TransformerFactory.newInstance();
		    Transformer transformer = transformerFactory.newTransformer();
		    DOMSource source = new DOMSource(doc);
		
		    StreamResult result =  new StreamResult(new File("C:/Users/Pecar/git/AAAAAAAAAAAA/IB-Projekat/Desktop/Desktop/data/test.xml"));
		    transformer.transform(source, result);
		
		    System.out.println("Done");
		    for(Image i : Receive.xml.getImages()){
		    	System.out.println(i.getName());
		    }
		    
		}catch(ParserConfigurationException pce){
		    pce.printStackTrace();
		}catch(TransformerException tfe){
		    tfe.printStackTrace();
		}
    }
}
