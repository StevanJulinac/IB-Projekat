package sendReceive;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
import model.Xml;

public class Receive {

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
    }
    
    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) { 
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                 
    return hexString;
    }
}
