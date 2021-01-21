/**
 * 
 */
package Clases;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class Control.
 * 
 * @author Cristian Camilo Vallecilla Cuellar 
 * Codigo: 1628790
 * Taller #1 PI: Bloques de Memoria
 *
 */
public class Control {
	
	
	private Vector<String> imagenes;
	private static int FILA=4,COLUMNA=5;
	private Bloque[][] bloques;
	private static int f1,c1,estadoJuego;
	private boolean fin;
	private Color color;
	private int necesidad;
	
	/**
	 * Instantiates a new control.
	 *
	 * @param tema the tema
	 */
	public Control(String tema) {
		
		estadoJuego=0;
		fin= false;
		
		imagenRandom();     //Se crea y llena el vector
		asignarImagenes(tema);     //Se asignan las imagenes a cada boton
		
	}
	
	/**
	 * Imagen random. 
	 * Crea y llena un vector de Strings que luego es usado para asignar las imagenes de manera aleatoria.
	 */
	public void imagenRandom(){
		imagenes= new Vector<String>(20);
			for(int i=1; i<11; i++){
				imagenes.add(""+i);
				imagenes.add(""+i);
			}
	}
	
	/**
	 * Asignar imagenes.
	 *
	 * @param tema the tema
	 */
	public void asignarImagenes(String tema){
		bloques= new Bloque[FILA][COLUMNA];
		if(tema.equals("Motos")){
			necesidad=1;
		}
		else{
			if(tema.equals("Lenguajes Programacion")){
				necesidad=2;
			}
			else{
				System.exit(-1);
			}
		}
		Random aleatorio=new Random();
		//Se crean los botones con su color, tema(a), posiciones e imagen aleatoria cada uno, para esto se usa el vector de Strings y se obtienen cada uno de sus elementos
		//por medio de un Ramdon, en cada iteracion se  remueve el componente del vector para no repetir imagenes.
		for(int x=0; x<FILA; x++){
			for(int y=0; y<COLUMNA; y++){
						
				int pos=aleatorio.nextInt(imagenes.size());
				color = new Color(20,aleatorio.nextInt(150),210);
				
				bloques[x][y]= new Bloque(color,x,y,imagenes.elementAt(pos),necesidad);
				bloques[x][y].setFocusPainted(false);
				
				imagenes.removeElementAt(pos);
						
			}
		}
		
	}
	
	/**
	 * Gets the bloques.
	 *
	 * @return the bloques
	 */
	public Bloque[][] getBloques() {
		return bloques;
	}
	
	/**
	 * Fin.
	 *
	 * @return true, if successful
	 * Determina cuando se gana el juego
	 */
	public boolean fin(){
		if (estadoJuego==10){
			fin=true;
		}
		return fin;
	}
	
	/**
	 * Sets the fin.
	 */
	public void setFin() {
		estadoJuego=0;
		fin=false;
	}

	/**
	 * Mostrar cartas inicio.
	 *
	 * @param bloques the bloques
	 */
	public void mostrarImagenesInicio(Bloque[][] bloques){
		Thread hilo = new Thread(){ 
            @Override
            public synchronized void run(){
                try {
                	sleep(1000);
                	for(int x=0; x<FILA; x++){
            			for(int y=0; y<COLUMNA; y++){
            				bloques[x][y].setImage(bloques,x,y,0);//Se muestra cada boton
            				sleep(60);
            			}
            		}
                	sleep(2000); //Tiempo de espera
                	
                	int lugar=3; //Necesidad
                    for(int x=0; x<FILA; x++){
            			for(int y=0; y<COLUMNA; y++){
            				bloques[x][y].setImage(bloques,x,y,lugar); //Se tapan los botones
            				sleep(60);
            			}
            		}
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        hilo.start();
	}
	
	/**
	 * Cambiar imagen.
	 *
	 * @param bloques the bloques
	 * @param f the f
	 * @param c the c
	 */
	public void cambiarImagen(Bloque[][] bloques,int f, int c){
                    bloques[f][c].setImage(bloques,f,c,0);     
	}
	
	
	/**
	 * Juzgar.
	 *
	 * @param bloques the bloques
	 * @param f the f
	 * @param c the c
	 * @param contador the contador
	 */
	public void juzgar(Bloque[][] bloques,int f, int c,int contador){
			
		if(contador%2 !=0){   //Se guardan las posiciones del boton presionado en caso de ser turno impar
			f1= f;
			c1= c;	
		}
		else{
			if(!(f1==f&&c1==c)){ //De lo contrario se comparan las imagenes,la del actual boton y la del boton con posiciones guardadas en el turno impar
								//Para esto se verifica que no sea el mismo boton presionado 2 veces.
				
				// Si tienen la misma imagen se deshabilitan los 2 botones y se agrega 1 al contador
					if(bloques[f1][c1].getImagen().equals(bloques[f][c].getImagen())){	
						bloques[f][c].setImageSinHilo(bloques,f,c);
						bloques[f1][c1].setEnabled(false);
						bloques[f][c].setEnabled(false);
						estadoJuego+=1;  //Contador que se usa para determinar juego.
						
					}
					else{		   //Si las imagenes son diferentes se tapan.									
						bloques[f][c].setImage(bloques,f,c,0);     //Segundo boton presionado. 
		                int lugar=2;     //Necesidad
		                bloques[f1][c1].setImage(bloques,f1,c1,lugar);   //Primer boton presionado.
		                bloques[f][c].setImage(bloques,f,c,lugar);      //Segundo boton presionado.
					}
			}else{
				//Si el boton es el mismo presionado 2 veces se tapa el boton
				int lugar=3; //Necesidad
				bloques[f1][c1].setImage(bloques,f1,c1,lugar);
			}
		}
		
	} 

	/**
	 * Reinicio.
	 *
	 * @param bloques the bloques
	 * @param decision the decision
	 * @param contador the contador
	 */
	public void reinicio(Bloque[][] bloques,int decision,int contador){
			
			if(decision == 0){
				contador=0;
				setFin();
				Vector<String> imagenes = new Vector<String>(20);
				for(int i=1; i<11; i++){
					imagenes.add(""+i);
					imagenes.add(""+i);
				}
				Random aleatorio= new Random();
				for(int x=0; x<FILA; x++){
					for(int y=0; y<COLUMNA; y++){
						int pos=aleatorio.nextInt(imagenes.size());
						bloques[x][y].setImagen(imagenes.elementAt(pos));
						imagenes.removeElementAt(pos);
						bloques[x][y].setEnabled(true);
					}
				}
				
				mostrarImagenesInicio(bloques);
			}
			else{
				System.exit(-1);
			}
		}
}
