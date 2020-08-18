package vista;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

public class Ventana_principal extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = -1728127814990949063L;

	private JButton btn_seleccionar_directorio, btn_iniciar;
	private JTextField txt_directorio;
	private JFileChooser File_Chooser;
	private String directorio;
	private boolean bandera_proceso;
	private organizacion.Proceso organizacion;

	public Ventana_principal() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Organizador de música");
		setSize(575, 97);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		iniciar();
	}

	public void iniciar() {
		try {
			InputStream imageInputStream = this.getClass().getResourceAsStream("/recursos/round_headset_black_18dp.png");
			BufferedImage bufferedImage = ImageIO.read(imageInputStream);
			this.setIconImage(bufferedImage);
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		File_Chooser = new JFileChooser();
		File_Chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.gridwidth = 2;
		this.add(new JLabel("Bienvenido. Elige el directorio a ordenar."), gbc);
		gbc.gridwidth = 1;

		txt_directorio = new JTextField("Directorio: ");
		txt_directorio.setEditable(false);
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(txt_directorio, gbc);

		gbc.weightx = 0.0;
		btn_seleccionar_directorio = new JButton("...");
		btn_seleccionar_directorio.addActionListener(e -> seleccionarDirectorio());
		gbc.gridx = 1;
		this.add(btn_seleccionar_directorio, gbc);

		btn_iniciar = new JButton("Iniciar ordenamiento");
		btn_iniciar.addActionListener(e -> iniciarProceso());
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		this.add(btn_iniciar, gbc);
	}

	public void seleccionarDirectorio() {
		if (File_Chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			directorio = "" + File_Chooser.getSelectedFile();
			txt_directorio.setText(directorio);
		}
	}

	public void iniciarProceso() {
		if (directorio == null) {
			JOptionPane.showMessageDialog(this, "No hay ningún directorio seleccionado", "Aviso", JOptionPane.PLAIN_MESSAGE);
		} else {
			organizacion = new organizacion.Proceso(directorio);
			organizacion.addPropertyChangeListener(this);
			bandera_proceso = false;
			organizacion.execute();
			btn_iniciar.setEnabled(false);
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == organizacion) {
			if (organizacion.isDone() && !bandera_proceso) {
				JOptionPane.showMessageDialog(this, "Proceso completado", "aviso", JOptionPane.PLAIN_MESSAGE);
				btn_iniciar.setEnabled(true);
				bandera_proceso = true;
			} else if (organizacion.isCancelled() && !bandera_proceso) {
				JOptionPane.showMessageDialog(this, "Proceso cancelado", "aviso", JOptionPane.PLAIN_MESSAGE);
				btn_iniciar.setEnabled(true);
				bandera_proceso = true;
			}
		}
	}
}