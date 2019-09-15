package organizacion;

import java.io.EOFException;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v1_1;

public class Proceso
{
	public static void Iniciar_proceso(String directorio) throws ClassNotFoundException, IOException, TagException
	{
		ArrayList<Artista> artistas;
		System.out.println("Todo jaló según lo planeado");
		System.out.println("directorio: " + directorio);
		boolean archivo = validar_archivo(directorio);
		if (archivo)
		{
			artistas = cargar_Archivo(directorio);
		} else
			System.out.println("no se encontró");
		artistas = new ArrayList<Artista>();
		organizarMP3(directorio, artistas);
		llenar_archivo(artistas, directorio);
	}

	public static void organizarMP3(String directorio, ArrayList<Artista> artistas) throws IOException, TagException
	{
		File carpeta = new File(directorio);
		FileFilter filtro = new FileFilter()
		{

			@Override
			public boolean accept(File pathname)
			{
				if (pathname.getName().endsWith(".mp3"))
					return true;
				else
					return false;
			}
		};

		File archivos[] = carpeta.listFiles(filtro);
		MP3File cancion;
		ID3v1 etiquetas;
		for (int i = 0; i < archivos.length; i++)
		{
			System.out.println(archivos[i].getName());
			cancion = new MP3File(archivos[i]);
			etiquetas = cancion.getID3v1Tag();
			if (artistas.contains(etiquetas.getArtist()))
			{
				
			}
			else
			{

			}
			System.out.println("Artista: " + etiquetas.getArtist());
		}
		System.out.println("Total de canciones: " + archivos.length);
	}

	public static boolean validar_archivo(String directorio)
	{
		File archivo = new File(directorio + "/listado de artistas.odm");

		if (archivo.exists())
			return true;
		else
			return false;
	}

	public static void llenar_archivo(ArrayList<Artista> artistas, String directorio) throws IOException
	{
		ObjectOutputStream oos = null;
		File archivo;
		try
		{
			archivo = new File(directorio + "/listado de artistas.odm");
			System.out.println("Archivo creado en: " + archivo.getPath());
			oos = new ObjectOutputStream(new FileOutputStream(archivo));
			for (int i = 0; i < artistas.size(); i++)
			{
				oos.writeObject(artistas.get(i));
			}
			JOptionPane.showMessageDialog(null, "Archivo guardado", "Aviso", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e)
		{
			JOptionPane.showConfirmDialog(null, "Error al guardar el achivo", "Aviso", JOptionPane.PLAIN_MESSAGE);
			System.out.println("No se pudo guardar " + e.getStackTrace());
		} finally
		{
			oos.close();
		}
	}

	public static ArrayList<Artista> cargar_Archivo(String directorio) throws IOException, ClassNotFoundException
	{

		ArrayList<Artista> artistas = new ArrayList<Artista>();
		ObjectInputStream ois = null;
		File archivo;
		try
		{
			archivo = new File(directorio + "/listado de artistas.odm");
			ois = new ObjectInputStream(new FileInputStream(archivo));
			do
			{
				artistas.add((Artista) ois.readObject());
			} while (true);
		} catch (EOFException e)
		{
			System.out.println("Archivo cargado");
			JOptionPane.showConfirmDialog(null, "Archivo cargado", "Aviso", JOptionPane.PLAIN_MESSAGE);
		} finally
		{
			ois.close();
		}
		return null;
	}

}
