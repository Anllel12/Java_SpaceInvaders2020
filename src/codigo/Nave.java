/*
*
*Clase Nave
*
*/
package codigo;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author Angel Esquinas
 */
public class Nave {
    
    Image imagen=null;
    
    public int posX=0;
    public int posY=0;
    
    private boolean pulsarIzq=false;
    private boolean pulsarDrech=false;
    
    public Nave(){
        
        
    }
    
    public void mueve(){
        
        if(pulsarIzq && posX>0){
            posX-=3;
        }
        if(pulsarDrech && posX<VentanaJuego.ANCHOPANTALLA-imagen.getWidth(null)){
            posX+=3;
        }
    }
    
    public boolean isPulsarIzq() {
        return pulsarIzq;
    }
    
    public void setPulsarIzq(boolean pulsarIzq) {
        
        this.pulsarIzq = pulsarIzq;
        this.pulsarDrech = false;
    }
    
    public boolean isPulsarDrech() {
        return pulsarDrech;
    }
    
    public void setPulsarDrech(boolean pulsarDrech) {
        
        this.pulsarDrech = pulsarDrech;
        this.pulsarIzq = false;
    }
}
