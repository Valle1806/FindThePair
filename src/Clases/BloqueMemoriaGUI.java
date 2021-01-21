/**
 * 
 */
package Clases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GUIBM.
 * @author Cristian Camilo Vallecilla Cuellar 
 * Codigo: 1628790
 * Taller #1 PI: Bloques de Memoria
 *
 */
public class BloqueMemoriaGUI extends JFrame{
	
	
	private JLabel titulo;
	private Container contenedor,contenedor1;
	public static final int GRIDSIZE1=4,GRIDSIZE2=5;
	private Bloque[][] bloques;
	private JPanel marco,panel;
	private  static int contador;
	private ImageIcon icon;
	String[] temas={"Lenguajes Programacion","Motos"};
	private Control control;
	private JComboBox comboB;
	String tema;
	Escucha escucha;
	
	
	/**
	 *  Inicia la vista.
	 */
	public BloqueMemoriaGUI() {
		initGUI();
		
		this.setTitle("Bloques de memoria");
		Image icon1 = new ImageIcon((getClass().getResource("/imagenes/inicio.png"))).getImage();
	    this.setIconImage(icon1);
		setResizable(false); 
		this.setLocation(300,70);
		this.setVisible(true);
		
		pack();
	}
	
	/**
	 * Inits the GUI.
	 * Muestra el  ComboBox
	 */
	public void initGUI(){
		contenedor1= this.getContentPane();
		
		panel= new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel elegir= new JLabel("Elige un tema:");
		elegir.setForeground(new Color(20,100,210));
		elegir.setBackground(Color.WHITE);
		elegir.setOpaque(true);
		elegir.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		icon= new ImageIcon(getClass().getResource("/imagenes/inicio.png"));
		JLabel imagenInicial= new JLabel();
		imagenInicial.setIcon(icon);
		
		comboB= new JComboBox(temas);     //Se crea el ComboBox
		comboB.setForeground(new Color(20,100,210));
		comboB.setBackground(Color.WHITE);
		
		escucha = new Escucha();
		comboB.addActionListener(escucha); //Se asigna la escucha al ComboBox
		
		panel.add(elegir, BorderLayout.CENTER);
		panel.add(imagenInicial,BorderLayout.NORTH);
		panel.add(comboB,BorderLayout.SOUTH);  
		
		contenedor1.add(panel);
		pack();
		setLocationRelativeTo(null);
	}
	
	/**
	 * Mostrar tablero. 
	 */
	public void mostrarTablero(){
		titulo= new JLabel("BUSCA LAS PAREJAS");
		titulo.setFont(new Font("TimesRoman", Font.BOLD, 20));
		titulo.setForeground(Color.BLUE);
		titulo.setHorizontalAlignment(JLabel.CENTER);
		
		contenedor = this.getContentPane();
		contenedor.add(titulo,BorderLayout.PAGE_START);
		
		control= new Control(tema);    //Se crea el objeto control
		bloques= control.getBloques(); //Se asigna la matriz del objeto control al atributo de la clase de la vista
		marco= new JPanel();
		marco.setLayout(new GridLayout(GRIDSIZE1,GRIDSIZE2));

		//Se asigna la escucha a cada boton y se agrega el boton al JPanel.
		for(int x=0; x<GRIDSIZE1; x++){
			for(int y=0; y<GRIDSIZE2; y++){
				bloques[x][y].addActionListener(escucha);
				marco.add(bloques[x][y]);
			}
		}
		contenedor.add(marco, BorderLayout.CENTER);
		
		control.mostrarImagenesInicio(bloques);//Se muestran las cartas al inicio.
		pack();
	}
	
	
	
	/**
	 * The Class Escucha
	 */
	private class Escucha implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent evento) {
			if(evento.getSource()== comboB){
				JComboBox temaCB= (JComboBox)evento.getSource();
				tema= (String)temaCB.getSelectedItem(); //Se asigna el item seleccionado del ComboBox.
				
				panel.setVisible(false); //Se cierra el panel que tiene el ComboBox.
				mostrarTablero();        //Se muestra el panel que contiene la matriz.
			}else{
				Bloque ventana =(Bloque)evento.getSource();
				contador+=1;  //Contador para determinar cuando comparar cartas, solo se compara cuando este sea par.
				
				control.cambiarImagen(bloques, ventana.getFila(), ventana.getColumna());  //Se cambia la imagen del boton.
				
				control.juzgar(bloques,ventana.getFila(), ventana.getColumna(),contador); //Se realiza la funcion juzgar, que es donde se comparan las imagenes
				
				if(control.fin()){
					int decision = JOptionPane.showConfirmDialog(null, "¡Ganaste!\nQuieres volver a jugar?", "Fin de Ronda", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					control.reinicio(bloques, decision,contador);  
					
				}
			}
		}	
	}
	
}



