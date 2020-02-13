/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Angel Esquinas
 */
public class Explosion {
    
    Image imagen1=null;
    Image imagen2=null;
    
    public int posX=0;
    public int posY=0;   
    public int explosion=20;//tiempo de la explosion
    
    Clip sonidoExplosion;
    
    public Explosion(){
        
        try {
            sonidoExplosion=AudioSystem.getClip();          
            sonidoExplosion.open(AudioSystem.getAudioInputStream(getClass().getResource("/sonidos/explosion.wav")));//cargamos el sonido
            
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
}
