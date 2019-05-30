package sign;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
	public static void main( String[] args )
    {
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream("./data/xml.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze= new ZipEntry("test.xml");
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream("./data/test.xml");

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            zos.close();

            System.out.println("Done");

        }catch(IOException ex){
           ex.printStackTrace();
        }
    }
}
