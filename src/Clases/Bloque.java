/**
 * 
 */
package Clases;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

// TODO: Auto-generated Javadoc
/**
 * The Class Bloque.
 *
 * @author Cristian Camilo Vallecilla Cuellar 
 * Codigo: 1628790
 * Taller #1 PI: Bloques de Memoria
 *
 */
public class Bloque extends JButton {
	
	private static final int MAXSIZE = 150;
	private int fila,columna,tema;
	private String imagen;
	
	/**
	 * Instantiates a new bloque.
	 *
	 * @param color the color
	 * @param fila the fila
	 * @param columna the columna
	 * @param i the i
	 * @param tema the tema
	 */
	public Bloque(Color color,int fila, int columna, String i,int tema) {
		this.tema=tema;
		this.fila = fila;
		this.columna= columna;
		this.imagen= i;
		setOpaque(true);
		setBackground(color);
		Dimension size= new Dimension(MAXSIZE,MAXSIZE);
		setPreferredSize(size);
	}
	
	/**
	 * Gets the fila.
	 *
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}
	
	/**
	 * Gets the columna.
	 *
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}
	
	/**
	 * Gets the tema.
	 *
	 * @return the tema
	 */
	public int getTema() {
		return tema;
	}
	
	/**
	 * Gets the imagen.
	 *
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}
	
	/**
	 * Sets the image.
	 * Muestra u oculta con "efecto" la imagen de acuerdo a la necesidad.
	 * 
	 */
	public void setImage(Bloque[][] bloques, int f, int c,int l){
		Thread hilo = new Thread(){    //Se crea el hilo para poder tener las esperas.
            @Override
            public synchronized void run(){
                try {
	                	if(l==2){    //recibe un entero(l) de acuerdo a la necesidad para dar el tiempo de espera, 2 para cuando las imagenes no son iguales.  
	            	 		sleep(800);
	            	 	}else{
	                	 	if(l==3){    //recibe un entero(l) de acuerdo a la necesidad para dar el tiempo de espera,3 en caso de ser presionado el mismo boton
	                	 		sleep(20);
	                	 	}
	            	 	}
	                	
	                	for(int i=10;i<140; i+=5){   //Se reduce el tamaño del boton.
	             		   bloques[f][c].setSize(new Dimension(150-i,150));
	             		   sleep(5);
	             	    }
	                	
	                	if(l==2||l==3){   //Quitar imagen.
	                		bloques[f][c].setIcon(null);
	            	 	}else{
	            	 		if(getTema()==1){                                     //Si el tema elegido fue motos va a ser igual a 1 
								ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/"+imagen+".png"));
								
								bloques[f][c].setIcon(icon);                      //Pone la imagen.
								
							}else{												  //Si el tema elegido fue Lenguajes de programacion va a ser igual a 2 
								ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/"+imagen+imagen+".png"));
								
								bloques[f][c].setIcon(icon); //Pone la imagen.
							}
	            	 	}	
						
					   for(int i=10;i<155; i+=5){//Devuelve el boton a su tamaño normal.
		           		   bloques[f][c].setSize(new Dimension(i,150));
		           		   sleep(5);
		           	   }
                  
                } catch (InterruptedException ex) {
	                   ex.printStackTrace();
                  }
           }
		};
		hilo.start(); //Se inicia el hilo
	}
	
	/**
	 * Sets the imagen.
	 *Usado para reiniciar el juego.
	 * @param imagen the new imagen
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	/**
	 *Cambia la imagen sin tiempo de espera. 
	 */
	public void setImageSinHilo(Bloque[][] bloques,int f, int c){
		if(getTema()==1){
			ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/"+imagen+".png"));
			bloques[f][c].setIcon(icon);
		}else{
			ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/"+imagen+imagen+".png"));
			bloques[f][c].setIcon(icon);
		}
	}
	
}
