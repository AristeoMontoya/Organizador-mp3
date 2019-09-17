package organizacion;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

public class Proceso
{
	public static void Iniciar_proceso(String directorio) throws IOException, TagException
	{
		File directorio_raiz = new File(directorio);
		File directorio_artista;
		File directorio_album;
		String artista_actual, album_actual, invalidos = "[\\\\\\\\/:*?\\\"<>|]";;
		MP3File cancion;
		ID3v1 etiquetas;
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

		File archivos[] = directorio_raiz.listFiles(filtro);
		for (int i = 0; i < archivos.length; i++)
		{
			cancion = new MP3File(archivos[i]);
			etiquetas = cancion.getID3v1Tag();

			artista_actual = etiquetas.getLeadArtist();
			album_actual = etiquetas.getAlbumTitle().replaceAll(invalidos, " ");

			directorio_artista = new File(directorio + "/" + artista_actual);
			directorio_album = new File(directorio_artista.getPath() + "/" + album_actual);

			if (!directorio_artista.exists())
			{
				directorio_artista.mkdir();
			}
			if (!directorio_album.exists())
			{
				directorio_album.mkdir();
			}

			archivos[i].renameTo(new File(directorio_album + "/" + archivos[i].getName()));
		}
	}
}