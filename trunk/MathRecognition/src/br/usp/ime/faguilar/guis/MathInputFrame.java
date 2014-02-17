/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MathInputFrame.java
 *
 * Created on Jan 3, 2013, 8:07:04 PM
 */

package br.usp.ime.faguilar.guis;

import br.usp.ime.faguilar.classification.Classifible;
import br.usp.ime.faguilar.classification.Classifier;
import br.usp.ime.faguilar.classification.ShapeContextClassifier;
import br.usp.ime.faguilar.conversion.MathExpressionGraph;
import br.usp.ime.faguilar.data.DStroke;
import br.usp.ime.faguilar.export.InkMLExpression;
import br.usp.ime.faguilar.export.MathExpressionSample;
import br.usp.ime.faguilar.feature_extraction.PreprocessingAlgorithms;
import br.usp.ime.faguilar.graphics.GMathExpression;
import br.usp.ime.faguilar.graphics.GraphicalStrokeKruskalMST;
import br.usp.ime.faguilar.segmentation.Segmentation;
import br.usp.ime.faguilar.segmentation.TreeSearchSegmentation;
import br.usp.ime.faguilar.util.SymbolUtil;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
import java.util.ArrayList;
import javax.swing.JCheckBox;

/**
 *
 * @author frank
 */
public class MathInputFrame extends javax.swing.JFrame {
    private Segmentation segmentation;
    private Classifier classifier;

    /** Creates new form MathInputFrame */
    public MathInputFrame() {
        initComponents();
        
        ArrayList<Classifible> classifibles = SymbolUtil.readTemplates();
        classifier = new ShapeContextClassifier();
        classifier.setTrainingData(classifibles);
        classifier.train();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mathInputPanel1 = new br.usp.ime.faguilar.guis.MathInputPanel();
        jToolBar1 = new javax.swing.JToolBar();
        runSegmentation = new javax.swing.JButton();
        distanceFilter = new javax.swing.JCheckBox();
        mstFilter = new javax.swing.JCheckBox();
        showMST = new javax.swing.JCheckBox();
        useTree = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.layout.GroupLayout mathInputPanel1Layout = new org.jdesktop.layout.GroupLayout(mathInputPanel1);
        mathInputPanel1.setLayout(mathInputPanel1Layout);
        mathInputPanel1Layout.setHorizontalGroup(
            mathInputPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 650, Short.MAX_VALUE)
        );
        mathInputPanel1Layout.setVerticalGroup(
            mathInputPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 364, Short.MAX_VALUE)
        );

        getContentPane().add(mathInputPanel1, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        runSegmentation.setText("run");
        runSegmentation.setFocusable(false);
        runSegmentation.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        runSegmentation.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        runSegmentation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                runSegmentationMouseClicked(evt);
            }
        });
        jToolBar1.add(runSegmentation);

        distanceFilter.setText("Distance filter");
        distanceFilter.setFocusable(false);
        distanceFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        distanceFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(distanceFilter);

        mstFilter.setText("MST filter");
        mstFilter.setFocusable(false);
        mstFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mstFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(mstFilter);

        showMST.setText("Show MST");
        showMST.setFocusable(false);
        showMST.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showMST.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showMST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showMSTMouseClicked(evt);
            }
        });
        jToolBar1.add(showMST);

        useTree.setText("Use Tree");
        useTree.setFocusable(false);
        useTree.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        useTree.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(useTree);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runSegmentationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_runSegmentationMouseClicked
        // TODO add your handling code here:
        ArrayList<DStroke> strokesOriginal = mathInputPanel1.getDrawingObject().getStrokes();
        ArrayList<DStroke> processedStrokes = PreprocessingAlgorithms.preprocessStrokes(strokesOriginal);
        EdgeWeightedGraph StrokeSetToEdgeWeightedGraph = MathExpressionGraph.StrokeSetToEdgeWeightedGraph(processedStrokes);
        KruskalMST mst = new KruskalMST(StrokeSetToEdgeWeightedGraph);
        mathInputPanel1.getDrawingObject().setMst(mst);
        mathInputPanel1.getDrawingObject().setStrokes(processedStrokes);
        mathInputPanel1.getDrawingObject().setDrawType(GraphicalStrokeKruskalMST.DRAW_TYPE_STROKES);

        double meandistance = 0;
        double filterMaxDistance;
        double alpha = 0.8;
        double beta = 0.6;
        for(Edge e: mst.edges())
            meandistance += e.weight();
        meandistance = meandistance / (StrokeSetToEdgeWeightedGraph.V() - 1);
        filterMaxDistance = alpha * meandistance;
        double mindistance = beta * meandistance;
        if(useTree.isSelected()){
            segmentation =  new TreeSearchSegmentation();
            ((TreeSearchSegmentation) segmentation).setMinDist(mindistance);
        }
        else
            segmentation =  new Segmentation();
        segmentation.setClassifier(classifier);
        boolean filterByDistance = distanceFilter.isSelected();
        segmentation.setTruncateByDistance(filterByDistance);
        segmentation.setMaxDistanceBetweenStrokes(filterMaxDistance);
        boolean filterByMST = mstFilter.isSelected();
        segmentation.setTruncateByMST(filterByMST);
        segmentation.setMst(mst);
        segmentation.part(processedStrokes);

        GMathExpression expression = (GMathExpression) segmentation.getPartitionAsDMathExpression();

        expression.setDrawnWithBBox(true);
        expression.setDrawnWithLabels(true);
        mathInputPanel1.getDrawingObject().setMathExpression(expression);
        mathInputPanel1.getDrawingObject().setDrawType(GraphicalStrokeKruskalMST.DRAW_TYPE_EXPRESSION);
        mathInputPanel1.repaint();
    }//GEN-LAST:event_runSegmentationMouseClicked

    private void showMSTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showMSTMouseClicked
        // TODO add your handling code here:
//        if(((JCheckBox)evt.getSource()).isSelected()){
            mathInputPanel1.getDrawingObject().setDrawGraph(((JCheckBox)evt.getSource()).isSelected());
            mathInputPanel1.repaint();
//        }
}//GEN-LAST:event_showMSTMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MathInputFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox distanceFilter;
    private javax.swing.JToolBar jToolBar1;
    private br.usp.ime.faguilar.guis.MathInputPanel mathInputPanel1;
    private javax.swing.JCheckBox mstFilter;
    private javax.swing.JButton runSegmentation;
    private javax.swing.JCheckBox showMST;
    private javax.swing.JCheckBox useTree;
    // End of variables declaration//GEN-END:variables

}