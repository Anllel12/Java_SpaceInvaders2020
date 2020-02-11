/*
*
*Clase del Disparo
*
*/
package codigo;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Angel Esquinas
 */
public class Disparo {
    
    Image imagen=null;
    
    public int posX=0;
    public int posY=0;
    
    
    public Disparo(){
        
        try{
            imagen=ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
        }
        catch(Exception e){
            System.out.println("No es capaz de leer la imagen");
        }
    }
    
    public void mueve(){
        posY-=5;
    }
    
    public void posDisparo(Nave _nave){
        
        posX=_nave.posX+_nave.imagen.getWidth(null)/2-imagen.getWidth(null)/2;
        posY=_nave.posY-imagen.getHeight(null)/2;
    }
}