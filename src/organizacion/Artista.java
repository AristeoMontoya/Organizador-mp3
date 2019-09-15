package organizacion;
import java.util.ArrayList;

public class Artista
{
	private String Ruta;
	private ArrayList<String> Albumes;
	
	public Artista() {}
	
	public Artista(String ruta, ArrayList<String> albumes)
	{
		Ruta = ruta;
		Albumes = albumes;
	}
	
	public String getRuta()
	{
		return Ruta;
	}
	
	public void setRuta(String ruta)
	{
		Ruta = ruta;
	}
	
	public boolean validaAlbum(String album)
	{
		if(Albumes.contains(album))
			return true;
		else
			return false;
	}
	public String getRutaAlbum(String album)
	{
		return Ruta + "/" + album;
	}
	public void agregarAlbum(String album, String ruta)
	{
		Albumes.add(album);
	}
}
