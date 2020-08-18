package organizacion;

import com.mpatric.mp3agic.Mp3File;
import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class Proceso extends SwingWorker<Void, Void> {
	private final String Directorio;

	public Proceso(String directorio) {
		Directorio = directorio;
	}

	@Override
	public Void doInBackground() {
		File directorio_raiz = new File(Directorio);
		File directorio_artista;
		File directorio_album;
		String artista_actual, album_actual, invalidos = "[\\\\/:*?\"<>|]";
		Mp3File cancion;

		FileFilter filtro = pathname -> pathname.getName().endsWith(".mp3");

		File[] archivos = directorio_raiz.listFiles(filtro);
		ProgressMonitor monitor = new ProgressMonitor(null, "Trabajando en ello", "Comenzando", 0, archivos.length);

		monitor.setMillisToDecideToPopup(10);
		monitor.setMillisToPopup(10);

		for (int i = 0; i < archivos.length; i++) {

			try {
				cancion = new Mp3File(archivos[i]);
				monitor.setNote(archivos[i].getName());
				monitor.setProgress(i);

				if (cancion.hasId3v1Tag()) {
					artista_actual = cancion.getId3v1Tag().getArtist();
					album_actual = cancion.getId3v1Tag().getAlbum().replaceAll(invalidos, " ");
				} else {
					artista_actual = cancion.getId3v2Tag().getArtist();
					album_actual = cancion.getId3v2Tag().getAlbum().replaceAll(invalidos, " ");
				}

				directorio_artista = new File(Directorio + "/" + artista_actual);
				directorio_album = new File(directorio_artista.getPath() + "/" + album_actual);

				if (!directorio_artista.exists()) {
					if (!directorio_artista.mkdir()) {
						System.out.println("No se creó directorio para artista: " + artista_actual);
						System.out.println(directorio_artista);
						char c = artista_actual.charAt(0);
						System.out.println("Caracter: " + (int) c);
					}
				}
				if (!directorio_album.exists()) {
					if (!directorio_album.mkdir()) {
						System.out.println("No se creó directorio para álbum: " + album_actual);
						System.out.println(directorio_album);
					}
				}

				archivos[i].renameTo(new File(directorio_album + "/" + archivos[i].getName()));

			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		monitor.setNote("Tarea terminada");
		monitor.setProgress(archivos.length);
		return null;
	}
}
