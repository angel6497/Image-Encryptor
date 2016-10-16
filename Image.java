// This class just defines the Image object used by the ImageManager class

import java.io.*;

public class Image implements Serializable {

	private String name;
	private String filePath;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFilePath(String filePath){
		this.filePath = filePath;
	}

	public String getFilePath(){
		return filePath;
	}

	public Image(){
	}

	public Image(String name, String filePath){
		this.name = name;
		this.filePath = filePath;
	}

	public String toString(){
		return String.format("Image name: %s; File Path: %s"
								, name
								, filePath);
	}


}
