package ib.project;

import java.io.File;
import java.util.ResourceBundle;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import ib.project.rest.DemoController;
import ib.project.storage.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

	private static String DATA_DIR_PATH;
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		DATA_DIR_PATH = rb.getString("dataDir");
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
//		 create files folder in target/classes
//		 avoid spaces in name of project, workspace etc.
		 new File(DemoController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + DATA_DIR_PATH).mkdirs();
	}
	
//	@Bean
//    CommandLineRunner init(StorageService storageService) {
//        return (args) -> {
//            storageService.deleteAll();
//            storageService.init();
//        };
//    }
}
 