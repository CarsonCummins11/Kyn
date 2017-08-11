package installer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Installer {
public static final String[] URLS = {"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/femaleCarrier.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/femaleNonCarrier.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/kyn.jar",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/Logo_Banner.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/Logo_Square.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/maleCarrier.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/maleNonCarrier.png",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/PedigreeAnalysis.exe",
		"https://github.com/CarsonCummins11/Kyn/releases/download/v1.0.0/traits.txt"};
	public Installer() {
		for (int j = 0; j < URLS.length; j++) {
		/*try {
			URL website = new URL(URLS[j]);
			ReadableByteChannel rbc;
			try {
				System.out.println("beginning dowload "+(j+1)+" of "+URLS.length);
				rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(getTag(URLS[j]));
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				System.out.println("download finished");
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		*/
			try {
				URL website = new URL(URLS[j]);
				try (InputStream in = website.openStream()) {
				    Files.copy(in,(new File(getTag(URLS[j]))).toPath(), StandardCopyOption.REPLACE_EXISTING);
				}catch(IOException e){
					
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private String getTag(String s) {
		String curString = "";
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='/'){
				curString="";
			}else{
				curString+=s.charAt(i);
			}
		}
		System.out.println(curString);
		return curString;
	}

	public static void main(String[] args) {
		new Installer();

	}
	
}
