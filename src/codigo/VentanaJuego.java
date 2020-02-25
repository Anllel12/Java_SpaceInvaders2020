/*
*
*Ventana principal del juego SpaceInvaders
*
*/
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Angel Esquinas
 */
public class VentanaJuego extends javax.swing.JFrame {
    
    static int ANCHOPANTALLA=800;
    static int ALTOPANTALLA=600;
    
    int filasMarcianos=5;
    int columnasMarcianos=10;
    
    int velocidad=1;//velodidad de los marcinos
    
    boolean direccionMarcianos=true;
    boolean tiro=true;
    
    BufferedImage buffer=null;
    BufferedImage plantilla=null;//buffer para guardar las imagenes de los marcianos
    
    Timer temporizador=new Timer(10, new ActionListener() {//bucle de animacion del juego. refresca el contenido de la pantalla
        @Override
        public void actionPerformed(ActionEvent ae) {
            //TODO: codigo de animacion
            bucleJuego();
        }
    });
    
    Marciano marciano=new Marciano(ANCHOPANTALLA);
    Nave nave =new Nave();
    Disparo disparo=new Disparo();
    
    ArrayList<Disparo> listaDisparos=new ArrayList();//creo el ArrayList de Disparos
    ArrayList<Explosion> listaExplosiones=new ArrayList();//creo el ArrayList de Explosiones
    ArrayList<Marciano> listaMarcianos=new ArrayList();//creo el ArrayList de Marcianos
    
    Image[] imagenes=new Image[30];//array para guardar las imagenes de los marcianos
    
    /**
     * Creates new form VentanaJuego
     */
    public VentanaJuego() {
        
        initComponents();
        
        try {
            plantilla=ImageIO.read(getClass().getResource("/imagenes/invaders2.png"));
        }
        catch (IOException ex) {
            System.out.println("No es capaz de leer la imagen");
        }
        
        for(int i=0; i<5; i++){//cargo las 30 imagenes del sprite sheet en el array de BuferedImage
            for (int j=0; j<4; j++) {
                imagenes[i*4+j]=plantilla.getSubimage(j*64, i*64, 64, 64).getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            }
        }
        
        imagenes[20]=plantilla.getSubimage(0, 320, 66, 32);//sprite nave extraña
        imagenes[21]=plantilla.getSubimage(66, 320, 64, 32);//sprite nave
        imagenes[22]=plantilla.getSubimage(255, 320, 32, 32);//sprite explosion marciano 1
        imagenes[23]=plantilla.getSubimage(255, 289, 32, 32);//sprite explosion marciano 2
        imagenes[24]=plantilla.getSubimage(132, 320, 64, 32);//sprite explosion nave
        
        setSize(ANCHOPANTALLA, ALTOPANTALLA);
        
        buffer=(BufferedImage) jPanel1.createImage(ANCHOPANTALLA, ALTOPANTALLA);//inicializo el buffer
        buffer.createGraphics();
        
        temporizador.start();//arranco el temporizador
        
        nave.imagen=imagenes[21];//pongo el sprite de la nave en la imagen
        nave.posX=ANCHOPANTALLA/2-nave.imagen.getWidth(this)/2;//coloco la nave en la pantalla
        nave.posY=ALTOPANTALLA-100;
        
        for (int i = 0; i < filasMarcianos; i++) {
            for (int j = 0; j < columnasMarcianos; j++) {
                
                Marciano m=new Marciano(ANCHOPANTALLA);
                
                m.imagen1 = imagenes[2 * i];
                m.imagen2 = imagenes[2 * i + 1];
                m.posX = j * (15 + m.imagen1.getWidth(null));
                m.posY = i * (10 + m.imagen1.getHeight(null));
                listaMarcianos.add(m);
            }
        }
    }
    
    private void pintaDisparos(Graphics2D _g2){
        
        Disparo disparoAux;
        
        for (int i=0; i<listaDisparos.size(); i++) {
            
            disparoAux=listaDisparos.get(i);
            
            disparoAux.mueve();
            
            if (disparoAux.posY<0){//si sale por la pantalla lo elimino
                listaDisparos.remove(i);
            }
            else{
                _g2.drawImage(disparoAux.imagen, disparoAux.posX, disparoAux.posY, null);//dibujo el disparo
            }
        }
    }
    
    private void pintaExplosiones(Graphics2D _g2){
        
        Explosion explosionAux;
        
        for (int i=0; i<listaExplosiones.size(); i++) {
            
            explosionAux=listaExplosiones.get(i);
            explosionAux.explosion--;
            
            if (explosionAux.explosion>10){
                _g2.drawImage(explosionAux.imagen1, explosionAux.posX, explosionAux.posY, null);//dibujo la explosion1
            }
            else{
                _g2.drawImage(explosionAux.imagen2, explosionAux.posX, explosionAux.posY, null);//dibujo la explosion2
            }
            
            if (explosionAux.explosion<=0){
                listaExplosiones.remove(i);//elimino la explosion;
            }
        }
    }
    
    private void pintaMarcianos(Graphics2D _g2){
        
        Marciano marcianoAux;
        
        boolean cambia=false;
        
        velocidad();
        
        for (int i = 0; i < listaMarcianos.size(); i++) {
            
            marcianoAux=listaMarcianos.get(i);
            
            marcianoAux._velocidad=velocidad;//cambia la velocidad
            marcianoAux.mueve(direccionMarcianos);
            
            if((marcianoAux.posX+marcianoAux.imagen1.getWidth(this))>=ANCHOPANTALLA || marcianoAux.posX<=0){
                cambia=true;
            }
            
            marcianoAux.vida--;
            
            if (marcianoAux.vida>25){
                _g2.drawImage(marcianoAux.imagen1, marcianoAux.posX, marcianoAux.posY, null);//dibujo la explosion1
            }
            else{
                _g2.drawImage(marcianoAux.imagen2, marcianoAux.posX, marcianoAux.posY, null);//dibujo la explosion2
            }
        }
        
        if (cambia) {
            direccionMarcianos=!direccionMarcianos;
            for (int i = 0; i < listaMarcianos.size(); i++) {
                
                marcianoAux=listaMarcianos.get(i);
                marcianoAux.posY+=marcianoAux.imagen1.getHeight(this);
            }
        }
    }
    
    private void fin(Graphics2D _g2){
        for (int i = 0; i < listaMarcianos.size(); i++) {
            
            if ((listaMarcianos.get(i).posY + listaMarcianos.get(i).imagen1.getHeight(null)) > nave.posY) {
                temporizador.stop();
                nave.imagen=imagenes[24];
                _g2.drawImage(nave.imagen, nave.posX, nave.posY, null);//dibujo la explosion de la nave
            }
        }
    }
    
    private void velocidad(){//Varíamos la velocidad de los marcianos en función de los que quedan        
        if (listaMarcianos.size() == 1) {//Si queda un marciano
            velocidad = 8;
        } else if (listaMarcianos.size() <= 13) {//Si quedan menos de 13 marcianos
            velocidad = 3;
        } else if (listaMarcianos.size() <= 40) {//Si quedan menos de 13 marcianos
            velocidad = 2;
        } else {//Si quedan más de dos tercios de marcianos
            velocidad = 1;
        }
    }
    
    private void chequeaColision(){//cheque si un marciano y un disparo colisionan
        
        Rectangle2D.Double rectanguloMarciano=new Rectangle2D.Double();
        Rectangle2D.Double rectanguloDisparo=new Rectangle2D.Double();
        
        for (int k=0; k<listaDisparos.size(); k++){
            
            rectanguloDisparo.setFrame(listaDisparos.get(k).posX, listaDisparos.get(k).posY, listaDisparos.get(k).imagen.getWidth(null), listaDisparos.get(k).imagen.getHeight(null));//calculo el rectangulo que contiene al disparo
            
            for (int i = 0; i < listaMarcianos.size(); i++) {
                
                rectanguloMarciano.setFrame(listaMarcianos.get(i).posX, listaMarcianos.get(i).posY, listaMarcianos.get(i).imagen1.getWidth(null), listaMarcianos.get(i).imagen1.getHeight(null));//calculo el rectangulo que contiene a cada marciano
                
                if(rectanguloDisparo.intersects(rectanguloMarciano)){//si entra aqui es porque ha chocado un disparo y un marciano
                    
                    Explosion e=new Explosion();
                    e.posX=listaMarcianos.get(i).posX;
                    e.posY=listaMarcianos.get(i).posY;
                    e.imagen1=imagenes[23];//pongo la imagen de la explosion
                    e.imagen2=imagenes[22];
                    listaExplosiones.add(e);
                    e.sonidoExplosion.start();//suena el audio
                    
                    listaMarcianos.remove(i);
                    
                    listaDisparos.remove(k);//elimino el disparo que ha dado a un marciano
                }
            }
            
        }
    }
    
    private void bucleJuego() {//redibuja los objetos en el jPanel1
        
        Graphics2D g2=(Graphics2D) buffer.getGraphics();//borro todo lo que ahi en el buffer
        
        g2.setColor(Color.BLACK);//doy el color negro a la pantalla
        g2.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA);
        
        pintaMarcianos(g2);
        pintaDisparos(g2);
        pintaExplosiones(g2);
        
        g2.drawImage(nave.imagen, nave.posX, nave.posY, null);//dibujo la nave
        nave.mueve();
        
        chequeaColision();//chequeo la colision del disparo con el marciano y tambien si un marciano esta a la altura de la nave
        fin(g2);
        
        g2=(Graphics2D) jPanel1.getGraphics();//dibujo de golpe el buffer sobre el jPanel
        g2.drawImage(buffer, 0, 0, null);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        
        switch(evt.getKeyCode()){//detecta que tecla se esta presionando
            case KeyEvent.VK_LEFT:
                nave.setPulsarIzq(true);
                break;
            case KeyEvent.VK_RIGHT:
                nave.setPulsarDrech(true);
                break;
            case KeyEvent.VK_SPACE:
                if(tiro){
                    Disparo d=new Disparo();
                    d.posDisparo(nave);
                    d.sonidoDisparo.start();//añado el sonido
                    listaDisparos.add(d);//agregamos el disparo a la lista de disparos
                    tiro=false;
                }
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        
        switch(evt.getKeyCode()){//detecta que tecla se esta pulsando
            case KeyEvent.VK_LEFT:
                nave.setPulsarIzq(false);
                break;
            case KeyEvent.VK_RIGHT:
                nave.setPulsarDrech(false);
                break;
            case KeyEvent.VK_SPACE:
                tiro=true;
                break;
        }
    }//GEN-LAST:event_formKeyReleased
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
