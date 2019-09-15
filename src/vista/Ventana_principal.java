package vista;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.farng.mp3.TagException;

public class Ventana_principal extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -1728127814990949063L;

	private JLabel lbl_banner;
	private JButton btn_seleccionar_directorio, btn_iniciar;
	private JTextField txt_directorio;
	private JFileChooser File_Chooser;
	private String directorio;

	public Ventana_principal()
	{
		setTitle("Organizador de música");
		setSize(575, 97);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		iniciar();
	}

	public void iniciar()
	{
		try
		{
			InputStream imageInputStream = this.getClass().getResourceAsStream("/recursos/round_headset_black_18dp.png");
			BufferedImage bufferedImage = ImageIO.read(imageInputStream);
			this.setIconImage(bufferedImage);
		} catch (IOException exception)
		{
			exception.printStackTrace();
		}

		File_Chooser = new JFileChooser();
		File_Chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		lbl_banner = new JLabel("Bienvenido. Elige el directorio a ordenar");

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridwidth = 2;
		this.add(lbl_banner, gbc);
		gbc.gridwidth = 1;

		txt_directorio = new JTextField("Directorio: ");
		txt_directorio.setEditable(false);
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(txt_directorio, gbc);

		gbc.weightx = 0.0;
		btn_seleccionar_directorio = new JButton("...");
		btn_seleccionar_directorio.addActionListener(this);
		gbc.gridx = 1;
		this.add(btn_seleccionar_directorio, gbc);

		btn_iniciar = new JButton("Iniciar ordenamiento");
		btn_iniciar.addActionListener(this);
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		this.add(btn_iniciar, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btn_seleccionar_directorio)
		{
			if (File_Chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				directorio = "" + File_Chooser.getSelectedFile();
				txt_directorio.setText(directorio);
			}
		}

		if (e.getSource() == btn_iniciar)
		{
			if (directorio == null)
			{
				JOptionPane.showMessageDialog(this, "No hay ningún directorio seleccionado", "Aviso", JOptionPane.PLAIN_MESSAGE);
			} 
			else
			{
				try
				{
					organizacion.Proceso.Iniciar_proceso(directorio);
				} catch (ClassNotFoundException e1)
				{
					e1.printStackTrace();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				} catch (TagException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}
}
