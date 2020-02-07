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
    
    private int anchoPantalla;
    
    public int vida=50;
    
    public Marciano(int _anchoPantalla){
        
        anchoPantalla=_anchoPantalla;
        
        try{
            imagen1=ImageIO.read(getClass().getResource("/imagenes/marcianito1.png"));
            imagen2=ImageIO.read(getClass().getResource("/imagenes/marcianito2.png"));
        }
        catch(Exception e){
            System.out.println("No es capaz de leer la imagen");
        }
    }
    
    public void mueve(boolean _direccionMarciano){
        
        if(_direccionMarciano){
            posX++;
        }
        else{
            posX--;
        }
    }      
}
