package sendReceive;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

public class Receive {

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
                    System.out.println("Hash: " + returnHex(hash));
                } catch (final IOException e) {
                    // handle errors here
                }
            }
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
