package organizacion;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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
		String artista_actual, album_actual;
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
			String invalidos = "[:?////]";
			artista_actual = etiquetas.getLeadArtist();
			album_actual = etiquetas.getAlbumTitle().replaceAll(invalidos, " ");
			
			carpeta = new File(directorio + "/" + artista_actual);
			if (carpeta.exists())
			{
				carpeta = new File(directorio + "/" + artista_actual + "/" + album_actual);
				if(carpeta.exists())
				{
					carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
					archivos[i].renameTo(carpeta);
				}
				else
				{
					carpeta = new File(carpeta.getPath());
					carpeta.mkdir();
					carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
					archivos[i].renameTo(carpeta);
				}
			}
			else
			{
				carpeta.mkdir();
				carpeta = new File(directorio + "/" + artista_actual + "/" + album_actual);
				carpeta.mkdir();
				carpeta = new File(carpeta.getPath() + "/" + archivos[i].getName());
				archivos[i].renameTo(carpeta);
			}
		}
	}
	
}
