/*
*
*Clase Marciano
*
*/
package codigo;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Angel Esquinas
 */
public class Marciano {
    
    public Image imagen1=null;//creo imagenes
    public Image imagen2=null;
    
    public int posX=0;
    public int posY=0;
    
    public int vida=50;
    
    public int _velocidad=1;
    
    private int anchoPantalla;
    
    
    
    public Marciano(int _anchoPantalla){
        
        anchoPantalla=_anchoPantalla;
              
    }
    
    public void mueve(boolean _direccionMarciano){
        
        if(_direccionMarciano){
            posX+=_velocidad;
        }
        else{
            posX-=_velocidad;
        }
    }      
}
