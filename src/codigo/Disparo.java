/*
*
*Clase del Disparo
*
*/
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Angel Esquinas
 */
public class Disparo {
    
    Image imagen=null;
    
    public int posX=0;
    public int posY=0;
    
    Clip sonidoDisparo;
    
    
        
        
    
    public Disparo(){
        
        try{
            imagen=ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
        }
        catch(Exception e){
            System.out.println("No es capaz de leer la imagen");
        }
        
        try {
            sonidoDisparo=AudioSystem.getClip();          
            sonidoDisparo.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/laser.wav")));//cargamos el sonido
            
        }
        catch (LineUnavailableException ex) {
            System.out.println("No es capaz de leer la carpeta");
        }
        catch (UnsupportedAudioFileException ex) {
            System.out.println("No es capaz de cargar el audio");
        } 
        catch (IOException ex) {
            System.out.println("No es capaz de leer el audio");
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