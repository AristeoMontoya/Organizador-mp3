package app;

public class App_organizador
{

	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{

			@Override
			public void run()
			{
				vista.Ventana_principal ventana = new vista.Ventana_principal();
				ventana.setVisible(true);
			}
		});
	}

}
