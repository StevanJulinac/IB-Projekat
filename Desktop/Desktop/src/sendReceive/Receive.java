package sendReceive;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
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
import sendReceive.Test;

public class Receive {
	
	private static ArrayList<Image> images = new ArrayList<Image>();
	public static Xml xml;
    static final File dir = new File("C:/IB_Slike");
    
    private static final String KEY_STORE_FILE = "./data/primer.jks";

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
    
    public void Start() throws NoSuchAlgorithmException, Exception{
    	BuildXML();
    }
    

	private void BuildXML() throws NoSuchAlgorithmException, Exception {
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
                    
                    Image image = new Image(f.getName(), f.length(), returnHex(hash));
                    images.add(image);

                } catch (final IOException e) {
                    // handle errors here
                }
            }
            xml = new Xml("username", images);
        }
        
        Test.CreateXML();
     
    }
    
    
    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = "";
        for (int i=0; i < inBytes.length; i++) { 
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                 
    return hexString;
    }
    
    
    /**
	 * Ucitava privatni kljuc is KS fajla
	 * alias primer
	 */
	private PrivateKey readPrivateKey() {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, "primer".toCharArray());
			
			if(ks.isKeyEntry("primer")) {
				PrivateKey pk = (PrivateKey) ks.getKey("primer", "primer".toCharArray());
				return pk;
			}
			else
				return null;
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
			return null;
		} 
	}


	/**
	 * Ucitava sertifikat is KS fajla
	 * alias primer
	 */
	private Certificate readCertificate() {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, "primer".toCharArray());
			
			if(ks.isKeyEntry("primer")) {
				Certificate cert = ks.getCertificate("primer");
				return cert;
				
			}
			else
				return null;
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
}