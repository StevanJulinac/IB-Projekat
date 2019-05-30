package sign;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Image;
import model.Xml;

public class Test {
	
	public static void main(String argv[]) {
		 
		}

	static void CreateXML(){
		final String OUT_FILE = "./data/testSigned.xml";
		
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			//Element rootElement = doc.createElement("company");
			//doc.appendChild(rootElement);

			// user elements
			Element user = doc.createElement("user");
		    doc.appendChild(user);

			//set attribute to user element
		    Attr username = doc.createAttribute("username");
		    username.setValue("Pera");
		    user.setAttributeNode(username);

		    
		    
		    for (Image i : Xml.getImages()){
		    						
				// img elements
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

		    saveDocument(doc, OUT_FILE);
			System.out.println("Signing of document done");
			// write the content into xml file
			
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);			
		}
		
		catch (ParserConfigurationException pce) {
            pce.printStackTrace();
          }
	}

	/**
	 * Snima DOM u XML fajl 
	 */
	private static void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			
			transformer.transform(source, result);

			f.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
