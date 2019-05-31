package sendReceive;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Image;
import model.Xml;

public class makeXML {

	private static ArrayList<Image> images = new ArrayList<Image>();

	public static Xml xml;

    static final File dir = new File("C:/IB_Slike");

    // lista podrzanih ekstenzija
    static final String[] EXTENSIONS = new String[]{
        "png", "bmp", "jpg" 
    };
    // filter za identifikaciju slika na osnovu njihovih ekstenzija
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {
        if (dir.isDirectory()) { 
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
         
                try {
                   
                    BufferedImage buffImg = ImageIO.read(f);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(buffImg, "jpg", outputStream);
                    byte[] data = outputStream.toByteArray();
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(data);
                    byte[] hash = md.digest();


                    System.out.println("image: " + f.getName());
                    System.out.println(" size  : " + f.length());
                    System.out.println("size: " + f.length());
                    System.out.println("Hash: " + returnHex(hash));

                    Image image = new Image(f.getName(), f.length(), returnHex(hash));
                    images.add(image);
                } catch (final IOException e) {
                    // handle errors here
                }
            }
            xml = new Xml("username", images);
        }
        
        CreateXML();
    }

    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) { 
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                  
                                         
    return hexString;
  }    
               
static void CreateXML(){	
		
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
		    
		    
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("./data/test47.xml"));
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			transformer.transform(source, result);
			
			// write the content into xml file
			
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);			
		}
		
		catch (ParserConfigurationException pce) {
            pce.printStackTrace();
          } catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
