package organizacion;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;
import org.farng.mp3.id3.ID3v2_4;

@SuppressWarnings("rawtypes")
public class Proceso extends SwingWorker
{
	private String Directorio;

	public Proceso(String directorio)
	{
		Directorio = directorio;
	}

	@Override
	public Object doInBackground() throws Exception
	{

		File directorio_raiz = new File(Directorio);
		File directorio_artista;
		File directorio_album;
		String artista_actual, album_actual, invalidos = "[\\\\\\\\/:*?\\\"<>|]";

		MP3File cancion;

		ID3v1 etiquetasv1;
		ID3v2_4 etiquetasv4;

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
		ProgressMonitor monitor = new ProgressMonitor(null, "Trabajando en ello", "Comenzando", 0, archivos.length);

		monitor.setMillisToDecideToPopup(10);
		monitor.setMillisToPopup(10);

		for (int i = 0; i < archivos.length; i++)
		{

			try
			{
				cancion = new MP3File(archivos[i]);
				monitor.setNote(archivos[i].getName());
				monitor.setProgress(i);
				etiquetasv1 = cancion.getID3v1Tag();
				etiquetasv4 = new ID3v2_4(cancion.getID3v2Tag());

				try
				{
					artista_actual = etiquetasv1.getArtist();
				} catch (NullPointerException e)
				{
					artista_actual = etiquetasv4.getLeadArtist();
				}

				try
				{
					album_actual = etiquetasv1.getAlbum().replaceAll(invalidos, " ");
				} catch (NullPointerException e)
				{
					album_actual = etiquetasv4.getAlbumTitle().replaceAll(invalidos, " ");
				}

				directorio_artista = new File(Directorio + "/" + artista_actual);
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

			} catch (IOException e1)
			{
				e1.printStackTrace();
			} catch (TagException e1)
			{
				e1.printStackTrace();
			}
		}
		monitor.setNote("Tarea terminada");
		monitor.setProgress(archivos.length);
		return null;
	}

}