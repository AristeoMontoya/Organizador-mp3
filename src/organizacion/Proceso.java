package organizacion;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class Proceso
{
	public static void Iniciar_proceso(String directorio) throws ClassNotFoundException, IOException, TagException
	{
		System.out.println("Todo jaló según lo planeado");
		System.out.println("directorio: " + directorio);
		organizarMP3(directorio);
	}

	public static void organizarMP3(String directorio) throws IOException, TagException
	{
		File carpeta = new File(directorio);
		String artista_actual, album_actual, log = "";
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
			cancion = new MP3File(archivos[i]);
			etiquetas = cancion.getID3v1Tag();
			artista_actual = etiquetas.getArtist();
			album_actual = etiquetas.getAlbum();
			
			carpeta = new File(directorio + "/" + artista_actual);
			log += "Artista actual " + artista_actual + "\n";
			log += "Album actual " + album_actual + "\n";
			log += "Cancion actual" + etiquetas.getTitle() + "\n";
			if (carpeta.exists())
			{
				log += "Se encontró la carpeta de " + artista_actual + "\n";
				carpeta = new File(directorio + "/" + artista_actual + "/" + album_actual);
				if(carpeta.exists())
				{
					log += "Se encontró la carpeta del álbum " + album_actual + "\n";
					carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
					archivos[i].renameTo(carpeta);
				}
				else
				{
					log += "No se encontró la carpeta del álbum " + album_actual + "\n";
					carpeta.mkdir();
					carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
					archivos[i].renameTo(carpeta);
				}
			}
			else
			{
				log += "No se encontró la carpeta de " + artista_actual + "\n";
				carpeta.mkdir();
				log += "Creada en " + carpeta.getPath() + "\n";
				carpeta = new File(carpeta.getPath() + "/" + album_actual);
				carpeta.mkdir();
				log += "Carpeta de álbum creada en " + carpeta.getPath() + "\n";
				carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
				archivos[i].renameTo(carpeta);
				log += "Ruta actual del archivo : " + archivos[i].getPath() + "\n";
			}
			log += (i + 1) + ")-------------------------------------------------------------------------\n";
		}
		log += "Total de canciones: " + archivos.length;
		crearLog(log,directorio);
	}
	
	public static void crearLog(String log, String directorio) throws FileNotFoundException
	{
		File archivo_log = new File(directorio + "/log.txt");
		PrintWriter salida = new PrintWriter(archivo_log);
		salida.println(log);
	}
}
