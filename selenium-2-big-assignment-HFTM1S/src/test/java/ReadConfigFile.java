
import java.io.FileReader;
import java.util.Properties;

public class ReadConfigFile { 

	public Properties properties;
	
	public Properties loadProperties() {
		try (FileReader reader = new FileReader("src\\otherfiles\\config.txt")) {
             this.properties = new Properties();
            properties.load(reader);
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return properties;
	}
}

