package organizacion;

import java.util.Hashtable;

public class Artista
{
	private String Ruta;
	private Hashtable<String, String> Albumes;
	
	public Artista() {}
	
	public Artista(String ruta, Hashtable<String, String> albumes)
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
		if(Albumes.containsKey(album))
			return true;
		else
			return false;
	}
	public String getRutaAlbum(String album)
	{
		return Albumes.get(album);
	}
	public void agregarAlbum(String album, String ruta)
	{
		Albumes.put(album, ruta);
	}
}
