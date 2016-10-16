/*
 * This class gives the user a command line menu that allows to create
 * a set of images that can be saved through serialization. The images
 * in a set can be searched by name, or file path. The images in a
 * certain set can be encrypted or decrypted through a Caesar Cypher.
 */

import java.util.*;
import java.io.*;

public class ImageManager {

	private static Scanner input = new Scanner(System.in);
	private static ArrayList<Image> images = new ArrayList<Image>();
	private static boolean done = false;

	// The main method uses a switch to call any of the options in the menu given to the user
	public static void main(String[] args){
		Image current = new Image();
		while(!done){
			int choice = printMenu();
			Scanner input = new Scanner(System.in);
			switch(choice){
			case 1:
				images.add(inputImage());
				System.out.println("Image added.");
				break;
			case 2:
				int choice2 = printFindMenu();
				switch(choice2){
					case 1:
						System.out.println("Whats the name of the image?");
						String searchName = input.nextLine();
						current = findImageName(searchName);
						if(current != null){
							System.out.println("Image found:");
							System.out.println(current);
						}
						else{
							System.out.println("Image not found.");
						}
						break;
					case 2:
						System.out.println("Whats the file path of the image?");
						searchName = input.nextLine();
						current = findImageFilePath(searchName);
						if(current != null){
							System.out.println("Image found:");
							System.out.println(current);
						}
						else{
							System.out.println("Image not found.");
						}
						break;
				}
				break;
			case 3:
				if(images.size() == 0){
					System.out.println("There are no images yet.");
				}
				else{
					for(Image i: images){
						System.out.println("--------------------------------------");
						System.out.println(i);
						System.out.println("--------------------------------------");
					}
				}
				break;
			case 4:
				System.out.println("What image do you want to encrypt?(Enter name)");
				String imgName = input.nextLine();
				for(int i = 0; i < images.size(); i++){
					if(images.get(i).getName().equalsIgnoreCase(imgName)){
						current = images.get(i);
						System.out.println("Enter the file path to create the encrypted file:");
						String outputFilePath = input.nextLine();
						String newFilePath;
						System.out.println("What key do you want to use to encrypt/decrypt?");
						int key = input.nextInt();
						if(key > 0){
							newFilePath = outputFilePath + current.getName() + "_ENCRYPTED";
						}
						else{
							newFilePath = outputFilePath + current.getName() + "_DECRYPTED.jpg";
						}
						encrypt(current, key, newFilePath);
						break;
					}
					else if(i == images.size()-1){
						System.out.println("The file is not in the system yet.");
					}
				}
				break;
			case 5:
				try{
					System.out.println("Where do you want to save the images?");
					String filePath = input.nextLine();
					filePath = filePath + "Images_List";
					FileOutputStream fos = new FileOutputStream(filePath);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					System.out.println("Images saved.");
					oos.writeObject(images);
					oos.close();
					oos = null;
					fos.close();
					fos = null;
				}
				catch(IOException e){
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				try{
					System.out.println("Where is the file that you want to load?");
					String filePath = input.nextLine();
					FileInputStream fis = new FileInputStream(filePath);
					ObjectInputStream ois = new ObjectInputStream(fis);
					images = (ArrayList<Image>)ois.readObject();
					System.out.println("Images loaded.");
					ois.close();
					ois = null;
					fis.close();
					fis = null;
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				default:
					done = true;
			}
		}
	}


	// This method just prints the menu and returns the input from the user
	public static int printMenu(){
		System.out.println(String.format("%s \n%s \n1. %s \n2. %s \n3. %s \n4. %s \n5. %s \n6. %s \n7. %s \n%s"
											, "****************************************************"
											, "What would you like to do?"
											, "Add image."
											, "Find image."
											, "See all images."
											, "Encrypt/Decrypt image."
											, "Save image list."
											, "Load image list."
											, "Close system."
											, "****************************************************"));
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		return choice;
	}

	// This method facilitates the creation of Image objects, by getting the user's input
	public static Image inputImage(){
		System.out.println("Whats the name of the image?");
		String name = input.nextLine();
		System.out.println("What's the image file path?");
		String filePath = input.nextLine();
		Image i = new Image(name, filePath);
		return i;
	}

	// This method prints the menu for finding an image and returns the user's input
	public static int printFindMenu(){
		System.out.println(String.format("%s \n1. %s \n2. %s"
											, "Choose how to find your image:"
											, "By name."
											, "By file path."));
		int choice = input.nextInt();
		return choice;
	}

	// This methods iterates though a list and looks for an image by name
	public static Image findImageName(String name){
		for(Image i: images){
			if(i.getName().equalsIgnoreCase(name)){
				return i;
			}
		}
		return null;
	}

	// This methods iterates though a list and looks for an image by file path
	public static Image findImageFilePath(String filePath){
		for(Image i: images){
			if(i.getFilePath().equals(filePath)){
				return i;
			}
		}
		return null;
	}

	// This method implement a Caesar Cypher by pushing every byte in the image by the guve key
	public static void encrypt(Image i, int key, String newFilePath){
		try{
			FileInputStream fis = new FileInputStream(i.getFilePath());
			FileOutputStream fos = new FileOutputStream(newFilePath);
			PrintStream ps = new PrintStream(fos);
			boolean done = false;
			while(!done){
				int next = fis.read();
				if(next == -1){
					done = true;
					System.out.println("Complete.");
				}
				else{
					ps.write((byte)next + key);
				}
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}

	}

}
