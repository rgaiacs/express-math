/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.ime.faguilar.segmentation.HypothesisTree;

import br.usp.ime.faguilar.conversion.InkMLInput;
import br.usp.ime.faguilar.conversion.InkmlReader;
import br.usp.ime.faguilar.data.DMathExpression;
import br.usp.ime.faguilar.data.DStroke;
import br.usp.ime.faguilar.data.DSymbol;
import br.usp.ime.faguilar.directories.MathRecognitionFiles;
import br.usp.ime.faguilar.feature_extraction.PreprocessingAlgorithms;
import static br.usp.ime.faguilar.segmentation.HypothesisTree.SymbolHypothesis.BINARY_REPRESENTATION_LENGHT;
import br.usp.ime.faguilar.segmentation.OrderedStroke;
import br.usp.ime.faguilar.util.FilesUtil;
import java.util.ArrayList;

/**
 *
 * @author Frank Aguilar
 */
public class PartitionTreeEvaluator {
    private static int correctSegmentations;
    private static int correctLabels;
    
    public static void testSymbolHypothesis(){
        InkmlReader reader = new InkmlReader();
        String fileContent = FilesUtil.getContentAsString(
                MathRecognitionFiles.TEST_FILES_CROHME);
        String[] filesToTest = fileContent.split("\n");
        DMathExpression mathExpression;
//        InkMLInput inkMlInput = new InkMLInput();
        
        PartitionTreeGenerator partitionGenerator = new PartitionTreeGenerator();
        HypothesisGenerator hypothesisGenerator = new NearestNeighborGraphHypothesisGenerator();
        ClassificationFilter filter = new ClassificationFilter();
        ((NearestNeighborGraphHypothesisGenerator) hypothesisGenerator).setFilter(filter);
        partitionGenerator.setHypothesisGenerator(hypothesisGenerator);
        
        
        int totalCorrectSegmentations = 0;
        int totalSymbols = 0;
        int totalCorrectLbels = 0;
        for (String aFile : filesToTest) {
            correctSegmentations = 0;
            correctLabels = 0;
            reader.read(MathRecognitionFiles.INKML_CROHME_2012_TEST_DIR + aFile);
            mathExpression = reader.getMathExpression().asDMathExpression();
            mathExpression = PreprocessingAlgorithms.preprocessDMathExpressionWithOrderedStrokes(mathExpression);
            ArrayList strokes = extractStrokes(mathExpression);
//            PARTITION TREE DOES NOT FINISHES FOR SECOND EXPRESSION OF CROME2012_TEST 
//            partitionGenerator.generateTree(strokes);
//            ArrayList<SymbolHypothesis> hypothesis = partitionGenerator.getHypothesis();
            hypothesisGenerator.generateHypothesis(strokes);
            ArrayList<SymbolHypothesis> hypothesis = hypothesisGenerator.getAllHypothesis();
            totalSymbols += mathExpression.size();
            countCorrectSegmentations(hypothesis, mathExpression);
            System.out.println(aFile + "\t" + mathExpression.size() + "\t" + 
                    correctLabels + "\t" +
                    + strokes.size() + "\t" + correctSegmentations + "\t" + hypothesis.size());
            totalCorrectSegmentations += correctSegmentations;
            totalCorrectLbels += correctLabels;
        }
        System.out.println("total correct segmentations: " + (float) totalCorrectSegmentations * 100 / totalSymbols +
                "\t" + (float) totalCorrectLbels * 100 / totalCorrectSegmentations );
    }
    
    public static ArrayList extractStrokes(DMathExpression expression){
        ArrayList strokes = new ArrayList();
        for (DSymbol dSymbol : expression) {
            for (DStroke dStroke : dSymbol) {
                strokes.add(dStroke);
            }
        }
        return strokes;
    }
    
    public static void countCorrectSegmentations(ArrayList<SymbolHypothesis> hypothesis, 
            DMathExpression mathExpression){
        correctSegmentations = 0;
        correctLabels = 0;
        for (DSymbol dSymbol : mathExpression) {
            for (SymbolHypothesis symbolHypothesis : hypothesis) {
                if(isHypothesisOfSymbol(symbolHypothesis, dSymbol)){
                    correctSegmentations++;
                    if(symbolHypothesis.hasLabel(dSymbol.getLabel()))
                        correctLabels++;
                    break;
                }
            }
        }
    }
    
    public static boolean isHypothesisOfSymbol(SymbolHypothesis hypothesis, DSymbol symbol){
        SymbolHypothesis newHypothesis = new SymbolHypothesis(hypothesis.getBinaryRepresentation().length);
        newHypothesis.setSymbol(symbol);
        return hypothesis.hasSameStrokes(newHypothesis);
//        for (DStroke dStroke : symbol) {
//             if(!hypothesis.getBinaryRepresentation()[((OrderedStroke) dStroke).getIndex()])
//                 return false;
//        }
//        return true;
    }
    
    public static void testSegmentationTree(){
        InkMLInput inkMlInput = new InkMLInput();
        ArrayList strokes = inkMlInput.extractStrokesFromInkMLFile(
                MathRecognitionFiles.INKML_CROHME_2012_TEST_DIR + "crohme_f042-eq067.inkml"); //"002-equation004.inkml" "formulaire054-equation056.inkml"  "KME2G3_1_sub_63.inkml"); //"KME2G3_11_sub_95.inkml");
        PartitionTreeGenerator partitionGenerator = new PartitionTreeGenerator();
        HypothesisGenerator hypothesisGenerator = new NearestNeighborGraphHypothesisGenerator();
        ClassificationFilter filter = new ClassificationFilter();
        
//        TO USE SHAPE CONTEXT CLASSIFIER
//        ClassifierTest test = new ClassifierTest();
//        test.setClassifier(new ShapeContextClassifier());
//        test.readData();
//        test.prepareClassifier();
//        filter.setClassifier( test.getClassifier());
//        END - TO USE SHAPE CONTEXT CLASSIFIER        
        
        
        ((NearestNeighborGraphHypothesisGenerator) hypothesisGenerator).setFilter(filter);
        partitionGenerator.setHypothesisGenerator(hypothesisGenerator);
        partitionGenerator.generateTree(strokes);
        ArrayList<Partition> partitionsInIncreasingCost = partitionGenerator.getPartitionsInIncreasingCost();
        System.out.println("num part: " + partitionsInIncreasingCost.size());
        ArrayList<SymbolHypothesis> hypothesis = partitionGenerator.getHypothesis();
        System.out.println("number of strokes: " + strokes.size());
        System.out.println("number of symbol hypothesis: " + hypothesis.size());
        for (SymbolHypothesis symbolHypothesis : hypothesis) {
            System.out.println(symbolHypothesis.getLabels().get(0) + " ");
        }
        
//        Partition aPartition;
//        for (int i = 0; i < partitionsInIncreasingCost.size(); i++) {
//            System.out.println("Partition: " );
//            aPartition = partitionsInIncreasingCost.get(i);
//            System.out.println("Cost: " + aPartition.getCost());
//            for (SymbolHypothesis symbolHypothesis : aPartition.getSymbols()) {
//                System.out.println(symbolHypothesis.getLabels().get(0) + " size: " 
//                        + symbolHypothesis.getSymbol().size() + " cost: " + symbolHypothesis.getCost());
//            }
//            System.out.println("\n");
//        }
    }
    
    
}