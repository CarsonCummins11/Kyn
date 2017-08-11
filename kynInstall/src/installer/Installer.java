package installer;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
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
		JFrame f = new JFrame();
		JTextArea curLoad = new JTextArea();
		curLoad.setEditable(false);
		f.setSize(500,500);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Install kyn");
		f.setLayout(new GridLayout());
		f.add(curLoad);
		String downloadDirectory = "";
		JFileChooser choose = new JFileChooser();
		choose.setDialogTitle("Choose download directory");
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retVal = choose.showOpenDialog(f);
		if(retVal == JFileChooser.APPROVE_OPTION){
			downloadDirectory = choose.getSelectedFile().getPath();
			downloadDirectory.replaceAll("\"" , "/");
			downloadDirectory = downloadDirectory+"/kyn";
			new File(downloadDirectory).mkdir();
		for (int j = 0; j < URLS.length; j++) {
			curLoad.setText(curLoad.getText()+"Downloading "+j+" of "+URLS.length+"\n");
			try {
				URL website = new URL(URLS[j]);
				try (InputStream in = website.openStream()) {
				    Files.copy(in,(new File(downloadDirectory+"/"+getTag(URLS[j]))).toPath(), StandardCopyOption.REPLACE_EXISTING);
				    curLoad.setText(curLoad.getText()+"Finished Downloading "+getTag(URLS[j])+"\n");
				}catch(IOException e){
					e.printStackTrace();
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		}
		f.dispose();
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
		return curString;
	}

	public static void main(String[] args) {
		new Installer();

	}
	
}
