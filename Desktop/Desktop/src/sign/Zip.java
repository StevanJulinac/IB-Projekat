package sign;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.nio.file.FileVisitResult;
//import java.nio.file.Paths;
//import java.nio.file.SimpleFileVisitor;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


//Source code to create a zip file from a given folder
//This example program recursively adds all files in the folder
//Works only with Java 7 and above
public class Zip {
 public static void main(String[] args) throws Exception {
     Zip zf = new Zip();
      
 }

 // Uses java.util.zip to create zip file
 public void zip(final Path sourceFolderPath, Path zipPath) throws Exception {
     final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
     Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
             zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
             Files.copy(file, zos);
             zos.closeEntry();
             return FileVisitResult.CONTINUE;
         }
     });
     System.out.println("Zipovanje zavrseno");
     zos.close();
 }
}