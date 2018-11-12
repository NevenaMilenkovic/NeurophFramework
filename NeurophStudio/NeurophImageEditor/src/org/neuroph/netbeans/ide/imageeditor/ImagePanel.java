package org.neuroph.netbeans.ide.imageeditor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;

/*
 * ImagePanel.java
 *
 * Created on Jan 6, 2012, 2:02:49 PM
 */
public class ImagePanel extends javax.swing.JPanel implements MouseMotionListener, MouseListener {

    Image offscreenImage;
    Graphics offscreenGraphics;
    
    Image image; // image to show
    int imageWidth, imageHeight;
    
    int cropX1, cropX2, cropY1, cropY2; // selection points
      
    boolean isCropSelected = false;
    boolean selectionStarted = false;
    Rectangle cropRect;
    
    /** Creates new form ImagePanel */
    public ImagePanel() {
        initComponents();
        addMouseMotionListener(this);
        addMouseListener(this);                     
    }

    public void setImage(Image image) {
        this.image = image;
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        repaint();
    }
    
    private void resetCropSelection() {
        isCropSelected = false;
        cropRect = null;
        cropX1 = cropX2= cropY1 = cropY2 = 0;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void mousePressed(MouseEvent e) {              
        resetCropSelection();
        
        cropX1 = e.getX();
        cropY1 = e.getY();      
        cropX2 = cropX1;
        cropY2 = cropY1;
               
        if ((cropX1 > imageWidth-1) || (cropX1 < 0)) {
            resetCropSelection();
            return;
        }     
        
        if ((cropY2 > imageHeight-1) || (cropY2 < 0)) {
            resetCropSelection();
            return;
        }
        
        selectionStarted = true;
        repaint();
    }    
    
   @Override
    public void mouseDragged(MouseEvent e) {       
        cropX2 = e.getX();
        cropY2 = e.getY();
        
        if (cropX2 > imageWidth-1) cropX2 = imageWidth-1;
        if (cropX2 < 0) cropX2 = 2;        
        if (cropY2 > imageHeight-1) cropY2 = imageHeight-1;
        if (cropY2 < 0) cropY2 = 2;
                
        repaint();
    }    

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (selectionStarted) {
        cropX2 = e.getX();
        cropY2 = e.getY();
        
        if (cropX2 > imageWidth-1) cropX2 = imageWidth-1;
        if (cropX2 < 0) cropX2 = 2;    
        if (cropY2 > imageHeight-1) cropY2 = imageHeight-1;
        if (cropY2 < 0) cropY2 = 2;        
        
        cropRect = getSelectionRectangle();                   
        this.isCropSelected = true;
        this.selectionStarted = false;
        repaint();
        }
    }   
    
    @Override
    public void mouseMoved(MouseEvent e) {    }    
    
    @Override
    public void mouseEntered(MouseEvent e) {   }

    @Override
    public void mouseExited(MouseEvent e) {    }    
    
    @Override
    public void mouseClicked(MouseEvent e) {   }    
   
    @Override
    public void paintComponent(Graphics g) {
       offscreenImage = createImage(this.getWidth(), this.getHeight());
       offscreenGraphics = offscreenImage.getGraphics();   
       
       if (image!=null)
            offscreenGraphics.drawImage(image, 0, 0, null); 
        
        // Prvo nacrta sliku ,da bi rec bio nacrtan preko slike ne ispod!
        Rectangle newRectangle = getSelectionRectangle();
        offscreenGraphics.drawImage(offscreenImage, 0, 0, null);


        if (!isCropSelected && selectionStarted) {
            offscreenGraphics.drawRect(newRectangle.x, newRectangle.y, newRectangle.width, newRectangle.height);
        }
        if (cropRect != null) {
            offscreenGraphics.drawRect(cropRect.x, cropRect.y, cropRect.width, cropRect.height);
        }      
      
        g.drawImage(offscreenImage, 0, 0, this);        
    }
    
   private Rectangle getSelectionRectangle() {
        int width = this.cropX1 - this.cropX2;
        int height = this.cropY1 - this.cropY2;
        Rectangle rectangle = new Rectangle(width < 0 ? this.cropX1
                : this.cropX2, height < 0 ? this.cropY1
                : this.cropY2, Math.abs(width), Math.abs(height));

        return rectangle;
    }
  
    public int getCropX1() {
        return cropX1;
    }

    public int getCropX2() {
        return cropX2;
    }

    public int getCropY1() {
        return cropY1;
    }

    public int getCropY2() {
        return cropY2;
    }

    public boolean isCropSelected() {
        return isCropSelected;
    }
    
    
    
    


}
