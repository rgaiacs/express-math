/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.ime.faguilar.main;

import br.usp.ime.faguilar.classification.ShapeContextClassifier;
import br.usp.ime.faguilar.conversion.InkMLInput;
import br.usp.ime.faguilar.conversion.MathExpressionGraph;
import br.usp.ime.faguilar.data.DStroke;
import br.usp.ime.faguilar.graphics.GraphicalStrokeKruskalMST;
import br.usp.ime.faguilar.guis.DrawingArea;
import br.usp.ime.faguilar.guis.EvaluationView;
import br.usp.ime.faguilar.evaluation.ClassifierTest;
import br.usp.ime.faguilar.evaluation.SymbolTestData;
import br.usp.ime.faguilar.classification.Classifible;
import br.usp.ime.faguilar.classification.SymbolLabels;
import br.usp.ime.faguilar.classification.neuralNetwork.ClassifiactionDataSet;
import br.usp.ime.faguilar.classification.neuralNetwork.NeuralNetworkClassifier;
import br.usp.ime.faguilar.conversion.DsymbolToGraphConversor;
import br.usp.ime.faguilar.conversion.InkmlReader;
import br.usp.ime.faguilar.cost.CostShapeContextInside;
import br.usp.ime.faguilar.data.DMathExpression;
import br.usp.ime.faguilar.data.DSymbol;
import br.usp.ime.faguilar.directories.MathRecognitionFiles;
import br.usp.ime.faguilar.evaluation.SegmentationAndClassificationEvaluator;
import br.usp.ime.faguilar.evaluation.ShapeContextStatistics;
import br.usp.ime.faguilar.evaluation.TrainingAndTestDataPartitioner;
import br.usp.ime.faguilar.feature_extraction.PreprocessingAlgorithms;
import br.usp.ime.faguilar.graph.Graph;
import br.usp.ime.faguilar.matching.GraphDeformationCalculator;
import br.usp.ime.faguilar.matching.GraphMatching;
import br.usp.ime.faguilar.matching.Matching;
import br.usp.ime.faguilar.matching.symbol_matching.DSymbolMatching;
import br.usp.ime.faguilar.matching.symbol_matching.UserSymbol;
import br.usp.ime.faguilar.outOfProject.ExtractionOFFeaturesToFile.NeuralNetworkFeaturesExtractor;
import br.usp.ime.faguilar.outOfProject.shapeContextDistanceCalculator.ShapeContextDistanceCalculator;
import br.usp.ime.faguilar.segmentation.HypothesisTree.ClassificationFilter;
import br.usp.ime.faguilar.segmentation.HypothesisTree.HypothesisFilter;
import br.usp.ime.faguilar.segmentation.HypothesisTree.HypothesisGenerator;
import br.usp.ime.faguilar.segmentation.HypothesisTree.NearestNeighborGraphHypothesisGenerator;
import br.usp.ime.faguilar.segmentation.HypothesisTree.Partition;
import br.usp.ime.faguilar.segmentation.HypothesisTree.PartitionTreeGenerator;
import br.usp.ime.faguilar.segmentation.HypothesisTree.SymbolHypothesis;
import br.usp.ime.faguilar.segmentation.SegmentationParameters;
import br.usp.ime.faguilar.util.FilesUtil;
import br.usp.ime.faguilar.util.SymbolUtil;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.KruskalMST;
//import edu.princeton.cs.introcs.StdOut;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;


public class Main {

    private Bag<Integer>[] adj;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        showEvaluationView();

//        TO CALCULATE COST FOR MARCELO
//        ShapeContextDistanceCalculator calculator = new ShapeContextDistanceCalculator();
//        FilesUtil.append("custos.txt", String.valueOf(calculator.calculateDistance(args)
//                + "\n"));
//        System.out.println(calculator.calculateDistance(args));
//        END TO CALCULATE COSTS FOR MARCELO
        
//        testClassifier();
        
//        testSegmentationTree();
        
//        extractFeaturesToTrainNeuralNetwork();
        
//        ClassifiactionDataSet.testDataSet();
//        NeuralNetworkClassifier.testNeuralNetworkWithIVCFiles();
        NeuralNetworkClassifier.testNeuralNetworkWithInkml();
//        calculateSymbolStatistics();
//        testSegmentationAndClassification();
//        recognizeExpressions(args[0]);

//        To recognize an individual expression
//        args = new String[]{"formulaire046-equation062.inkml", "formulaire046-equation062.lg"};
//        recognizeExpression(args);
        
//        executeFileNamesPartitionFromPath();
//        executeFileNamesPartitionFromFile();
//        SymbolUtil.partSymbolsInSeveralFiles(EvaluationView.TEMPLATES_FILE);
        //TO TEST READING OF TEMPLATES FROM INKML FILES
//        testInkmlReader();
    }
    
    public static void extractFeaturesToTrainNeuralNetwork(){
          NeuralNetworkFeaturesExtractor.exportFeaturesToAFile();
//          NeuralNetworkFeaturesExtractor.partData();
//        NeuralNetworkFeaturesExtractor.putLabelsOnUNPSymbols();
        
//        NeuralNetworkFeaturesExtractor.mergeFeatureFiles("D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
//                + "\\train-test\\scontext\\train-30points-8shape_contexts.data" , "D:\\frank_temp"
//                        + "\\NeededFilesForInterface_learningAnMLP\\train-test\\trainOnLineFeatures.data", 
//                "D:\\frank_temp\\NeededFilesForInterface_learningAnMLP\\train-test\\merged_train.data");
////        
//        NeuralNetworkFeaturesExtractor.mergeFeatureFiles("D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
//                + "\\train-test\\scontext\\test-30points-8shape_contexts.data" , "D:\\frank_temp"
//                        + "\\NeededFilesForInterface_learningAnMLP\\train-test\\validationOnLineFeatures.data", 
//                "D:\\frank_temp\\NeededFilesForInterface_learningAnMLP\\train-test\\merged_validation.data");
////        
        
//        TO EXTRACT A SET OF COLUMNS FROM THE COMPLETE SHAPE CONTEXT DATA
//        int[] columns = {1, 15, 30}; 
//        int[] columns = {1, 10, 20, 30}; 
//        int[] columns = {1, 8, 15, 22, 30};
//        int[] columns = {1, 6, 12, 18, 24, 30};
//        int[] columns = {1, 5, 10, 15, 20, 25, 30};
//        int[] columns = {1, 5, 9, 13, 17, 21, 25, 30};
////        int[] columns = {1, 5, 8, 12, 16, 20, 23, 27, 30};
//        
////        int[] columns = {1, 4, 7, 10, 13, 17, 20, 23, 26, 30};
//        int numberOFFeaturesPerPoint = 32;
////        NeuralNetworkFeaturesExtractor.extractColumns("D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
////                + "\\train-test\\scontext\\generalized-shape-train.data", 
////                "D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
////                + "\\train-test\\scontext\\generalized-shape-train-8SContexts.data", "\t", 
////                columns, numberOFFeaturesPerPoint);
//        
//        NeuralNetworkFeaturesExtractor.extractColumns("shape-contexts.txt"
////                "D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
////                + "\\train-test\\scontext\\generalized-shape-validation.data"
//                , 
//                "D:\\frank_temp\\NeededFilesForInterface_learningAnMLP"
//                + "\\train-test\\scontext\\generalized-shape-test-8SContexts.data", "\t", 
//                columns, numberOFFeaturesPerPoint);
    }
    
    public static void testSegmentationTree(){
        InkMLInput inkMlInput = new InkMLInput();
        ArrayList strokes = inkMlInput.extractStrokesFromInkMLFile(
                MathRecognitionFiles.INKML_CROHME_2012_TEST_DIR + "crohme_f042-eq067.inkml"); //"002-equation004.inkml" "formulaire054-equation056.inkml"  "KME2G3_1_sub_63.inkml"); //"KME2G3_11_sub_95.inkml");
        PartitionTreeGenerator partitionGenerator = new PartitionTreeGenerator();
        HypothesisGenerator hypothesisGenerator = new NearestNeighborGraphHypothesisGenerator();
        
        ClassifierTest test = new ClassifierTest();
        test.setClassifier(new ShapeContextClassifier());
        test.readData();
        test.prepareClassifier();
        
        ClassificationFilter filter = new ClassificationFilter();
        filter.setClassifier((ShapeContextClassifier) test.getClassifier());
        ((NearestNeighborGraphHypothesisGenerator) hypothesisGenerator).setFilter(filter);
        partitionGenerator.setHypothesisGenerator(hypothesisGenerator);
        partitionGenerator.generateTree(strokes);
        ArrayList<Partition> partitionsInIncreasingCost = partitionGenerator.getPartitionsInIncreasingCost();
        System.out.println("num part: " + partitionsInIncreasingCost.size());
        Partition aPartition;
        for (int i = 0; i < partitionsInIncreasingCost.size(); i++) {
            System.out.println("Partition: " );
            aPartition = partitionsInIncreasingCost.get(i);
            System.out.println("Cost: " + aPartition.getCost());
            for (SymbolHypothesis symbolHypothesis : aPartition.getSymbols()) {
                System.out.println(symbolHypothesis.getLabels().get(0) + " size: " 
                        + symbolHypothesis.getSymbol().size() + " cost: " + symbolHypothesis.getCost());
            }
            System.out.println("\n");
        }
    }

    public static void recognizeExpression(String[] inputAndOutputFiles){
            SegmentationAndClassificationEvaluator evaluator = new SegmentationAndClassificationEvaluator(
                    "data/train_crohme2013", false);
            evaluator.setaFileToTest(inputAndOutputFiles[0]);
            evaluator.setOutputFile(inputAndOutputFiles[1]);
            String parametersFile = "data/alpha1_distance0.7.txt";//in.readLine();
            ArrayList<SegmentationParameters> params = SegmentationParameters.readParametersWithDistanceFactor(parametersFile);
            evaluator.setParameters(params.get(0));
            String fileName = "fileNames";
            evaluator.recognizeAExpression();
    }
    
    public static void testInkmlReader(){
        //        String fileName = EvaluationView.INKML_DIR + "65_alfonso.inkml";
        //        InkmlReader inkmlReader = new InkmlReader();
        //        inkmlReader.read(fileName);
        //        System.out.println(inkmlReader.getMathExpression().asDMathExpression());
        ArrayList<Classifible> readTemplatesFromInkmlFiles =
                SymbolUtil.readTemplatesFromInkmlFiles(
                MathRecognitionFiles.TRAINING_FILES_CROHME,                
                MathRecognitionFiles.INKML_CROHME_2012_TRAIN_DIR);
        List<Classifible> selected = readTemplatesFromInkmlFiles.subList(0, 5000);

        double cost = 0;
        double minCost;
        DSymbol symbol_i, symbol_j = null;
        int errors = 0;
        int best_j = -1;
        int notEvaluated = 0;
        for (int i = 0; i < selected.size(); i++) {
            symbol_i = selected.get(i).getSymbol();
            minCost = Double.MAX_VALUE;
            best_j = -1;
            if(symbol_i.size() > 1){
                for (int j = 0; j < selected.size(); j++) {
                    if(i != j){
                        symbol_j = selected.get(j).getSymbol();
                        if(symbol_j.size() > 1 && (symbol_j.size() == symbol_i.size())){
//                            TO TEST GRAPH DEFORMATION
//                            Graph inputGraph = DsymbolToGraphConversor.convert(symbol_j);
//                            Graph templateGraph = DsymbolToGraphConversor.convert(symbol_i);
//                            GraphDeformationCalculator calculator = GraphDeformationCalculator.
//                                    newInstanceFromTemplateAndIputGraphs(templateGraph, inputGraph);
//                            cost = calculator.getCost();

//                            TO TEST SYMBOL MATCHING WITHOUT GRAPH DEFORMATION
                            DSymbolMatching matching = DSymbolMatching.newInstanceFromSymbolsToMatch(
                                    symbol_i, symbol_j);
                            cost = matching.getCost();
                            if(cost < minCost){
                                best_j = j;
                                minCost = cost;
                            }
                        }
                    }
                }
                if(best_j >= 0){
                    symbol_j = selected.get(best_j).getSymbol();
                    if(!symbol_i.getLabel().equals(symbol_j.getLabel()))
                        errors++;
                    System.out.printf("cost of matching between %s and %s (#strokes: %d):", symbol_i.getLabel() ,
                                symbol_j.getLabel(), symbol_i.size());
                    System.out.println(minCost);
                }
            }
            if(best_j == -1)
                notEvaluated++;
        }
        System.out.println("percentage error: " + (float)errors/(selected.size() - notEvaluated));
    }

    public static void executeFileNamesPartitionFromPath(){
//        ArrayList<String> inkFiles = FilesUtil.getNotHiddenFileNames(EvaluationView.INKML_DIR);
        ArrayList<String> inkFiles = FilesUtil.getNotHiddenFileNames(MathRecognitionFiles.INKML_CHROME_2013_DIR);
        executeFileNamesPartition(inkFiles);
    }

    public static void executeFileNamesPartition(ArrayList<String> inkFiles){
        TrainingAndTestDataPartitioner partirioner = new TrainingAndTestDataPartitioner();
//        partirioner.partFileNamesRandomlyByModels(inkFiles);
        partirioner.setTrainingPercentage(0.7);
        partirioner.partFileNamesRandomly(inkFiles);
        ArrayList<String> testExpressions = partirioner.getTestExpressions();
        ArrayList<String> trainingExpressions = partirioner.getTrainingExpressions();

        String trainingFileName = "part1.txt";
        String trainingFileContent = "";
        String testFileName = "part2.txt";
        String testFileContent = "";
        System.out.println("number of train expre: " + trainingExpressions.size());
        System.out.println("number of test expre: " + testExpressions.size());

        for (String expression : trainingExpressions) {
            trainingFileContent += (expression + "\n");
        }
        for (String expression : testExpressions) {
            testFileContent += (expression + "\n");
        }
        System.out.println("test files:");
        System.out.println(testFileContent);
        System.out.println("training: ");
        System.out.println(trainingFileContent);
        FilesUtil.write(testFileName, testFileContent);
        FilesUtil.write(trainingFileName, trainingFileContent);
    }

    public static void executeFileNamesPartitionFromFile(){
        String fileContent = FilesUtil.getContentAsString(MathRecognitionFiles.TRAINING_FILES);
        String[] fileNames = fileContent.split("\n");
        ArrayList<String> inkFiles = new  ArrayList();
        inkFiles.addAll(Arrays.asList(fileNames));
        executeFileNamesPartition(inkFiles);
    }
    
     private static void calculateSymbolStatistics() {
//         ArrayList<Point2D> points = new ArrayList<Point2D>();
//         points.add(new Point2D.Double(67.0, 23.));
//         points.add(new Point2D.Double(68.0, 23.));
//         points.add(new Point2D.Double(68.0, 23.));
//         points = PreprocessingAlgorithms.getNotDuplicatedPoints(points);
//         for (Point2D point2D : points) {
//             System.out.println(point2D);
//         }


        ShapeContextStatistics statistics = new ShapeContextStatistics();
//        statistics.calculateStatistics();
//        statistics.calculateShapeComplexityStatistics();
        statistics.calculateShapeContextStatistics();
//        statistics.calculateSrokeDistanceStatistics();
        String completeStatisticsAsString = statistics.getCompleteStatisticsAsString2();
        String statisticsAsString = statistics.getResumeStatisticsAsString();
        
        statistics.saveSelectedTemplates();
//        System.out.println(statisticsAsString);
        String Results = statisticsAsString + "---------------\n" + completeStatisticsAsString;
         System.out.println(Results);
//        FilesUtil.write("results scontext.txt", Results);

    }


     public static void testSegmentationAndClassification(){
         //TO PRINT FREQUENCIES OF SYMBOLS
//         System.out.println(SymbolTestData.getTrainingFrequencies());

         //         InkmlReader reader = new InkmlReader();
//         reader.read(EvaluationView.INKML_CROHME_2012_TEST_DIR + "TestData1_0_sub_4.inkml");
         //to create file names with names of expressions of crohme (BEGIN)
//        ArrayList<String> notHiddenFileNames = FilesUtil.getNotHiddenFileNames(EvaluationView.INKML_CROHME_2012_TEST_DIR);
//        String namesToFile = "";
//         for (String string : notHiddenFileNames) {
//             namesToFile += string + "\n";
//         }
//         FilesUtil.write("test_crohme", namesToFile);
//         (END)
        try {
            
//            SegmentationAndClassificationEvaluator evaluator = new SegmentationAndClassificationEvaluator(EvaluationView.TRAINING_FILES_CROHME, false);
            SegmentationAndClassificationEvaluator evaluator = new SegmentationAndClassificationEvaluator(
//                    "data/train_crohme2013", false);
            "data/train_crohme_2012", false);
                    //new SegmentationAndClassificationEvaluator(EvaluationView.TRAINING_FILES);
            evaluator.setFilesToTest(MathRecognitionFiles.TEST_FILES_CROHME);
//            evaluator.setFilesToTest(EvaluationView.INKML_CROHME_2013_TEST_FILES);
//            evaluator.setFilesToTest(EvaluationView.TEST_FILES);
            evaluator.chargeFileNames();
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            System.out.println("parameters file:");
            String parametersFile = in.readLine();
            ArrayList<SegmentationParameters> params = SegmentationParameters.readParametersWithDistanceFactor(parametersFile);
            String fileName = "fileNames";

            int count = 1;
            for (SegmentationParameters aSetOfParameters : params) {
                evaluator.setParameters(aSetOfParameters);
                evaluator.setFileNames("test_segmentation_complete_chrome_2013" + aSetOfParameters.getDistanceFactor() + ".txt");
//                evaluator.setFileNames("test_scontext_coords" + "_" + "theta_" + aSetOfParameters.getAlpha() + "distance_" + aSetOfParameters.getDistanceFactor() + ".txt");
                evaluator.runEvaluation();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public static void recognizeExpressions(String parametersFile){
         SymbolUtil.DIR_COMPLEMENT = "";
        SegmentationAndClassificationEvaluator evaluator = new SegmentationAndClassificationEvaluator(
                "train-files.txt", false);
        evaluator.setFilesToTest(parametersFile);
        evaluator.chargeFileNames();
        evaluator.setFileNames("results.txt");
        evaluator.recognizeExpressions();
     }

    public static void showEvaluationView(){
//                EVALUATION VIEW
        EvaluationView evaluationView = new EvaluationView();
        
        JFrame dialog = new JFrame();
        dialog.setContentPane(evaluationView);
        Dimension defaultDimension = new Dimension(800, 600);
//        dialog.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
        dialog.setSize(defaultDimension);
//        dialog.pack();
        dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialog.setVisible(true);
//        END VIEW EVALUTION

//        TEST SYMBOL CLASSIFICATION
//        ArrayList<Classifible> symbols = SymbolUtil.readSymbolData("model-symbols.txt");
//        for (Classifible classifible : symbols) {
//            System.out.println(classifible);
//        }
    }
    /**
     * Craete a toy graph based on String objects.
     *
     * @return a graph based on String objects.
     */
//    private static SimpleWeightedGraph<String, DefaultEdge> createStringGraph()
//    {
//        SimpleWeightedGraph<String, DefaultEdge> g =
//            new SimpleWeightedGraph<String, DefaultEdge>(DefaultEdge.class);
//
//        String v1 = "v1";
//        String v2 = "v2";
//        String v3 = "v3";
//        String v4 = "v4";
//
//        // add the vertices
//        g.addVertex(v1);
//        g.addVertex(v2);
//        g.addVertex(v3);
//        g.addVertex(v4);
//
//        // add edges to create a circuit
//        g.addEdge(v1, v2);
//        g.addEdge(v2, v3);
//        g.addEdge(v3, v4);
//        g.addEdge(v4, v1);
//
//        g.setEdgeWeight(g.getEdge(v1, v2), 100);
//
//        return g;
//    }


    public static void testClassifier(){
        try {
            //print frecuencies
            //        System.out.println(SymbolTestData.getFrequencies());
            double trainningPercentage = 1;
            //        WITH 15 FIRST CLASSES FOR DATA OF WILLIAN RESULTS ARE, with only trainningPercentage = 0.3;
            //number of good results: 413
            //number of bad results: 5
            //        WITH 15 FIRST CLASSES FOR DATA OF WILLIAN RESULTS ARE, with only trainningPercentage = 0.3;
            //number of good results: 6551
            //number of bad results: 250
            //       percentage error =  3.8162113 %
            ClassifierTest classifierTest = new ClassifierTest();
            classifierTest.readData();
            classifierTest.setTrainningPercent(trainningPercentage);
            ShapeContextClassifier shapeContextClassifier = new ShapeContextClassifier();
            classifierTest.setClassifier(shapeContextClassifier);
            classifierTest.prepareClassifier();
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            System.out.println("parameters file:");
            String parametersFile = in.readLine();
            String frequencies = SymbolTestData.getFrequencies(classifierTest.getSymbolData().getAllSymbolsAsArrayList());//SymbolTestData.getTrainingFrequencies();
            System.out.println(frequencies);
            ArrayList<SegmentationParameters> params = SegmentationParameters.readParameters(parametersFile);

            ArrayList<Classifible> classifibles = SymbolUtil.readTemplatesFromInkmlFiles(
                    MathRecognitionFiles.TEST_FILES_CROHME,
                    MathRecognitionFiles.INKML_CROHME_2012_TEST_DIR);
            classifierTest.setTestData(classifibles);

            for (SegmentationParameters aSetOfParameters : params) {
                classifierTest.setAlpha(aSetOfParameters.getAlpha());
//                classifierTest.setAlpha((float)1.0);
//                GraphMatching.ANGLE_WEIGHT = aSetOfParameters.getAlpha();
                classifierTest.setBeta(aSetOfParameters.getBeta());
                classifierTest.setGama(aSetOfParameters.getGama());
                classifierTest.setParametersToClassifier();
                classifierTest.testClassifier();
                
                classifierTest.resetResults();
            }
            // test hashmap
            //        HashMap<UserSymbol, Integer> map = new HashMap<UserSymbol, Integer>();
            //        UserSymbol us1 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "a");
            //        UserSymbol us2 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "a");
            //        UserSymbol us3 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "b");
            //        UserSymbol us4 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "c");
            //
            //        map.put(us1, 1);
            //        map.put(us2, 2);
            //        map.put(us3, 1);
            //        map.put(us4, 1);
            //
            //        for (UserSymbol us : map.keySet()) {
            //            System.out.println(us.getUserNickName() + "-" + us.getSymbolLabel());
            //        }
            // TO TEST SHAPE CONTEXT WITH INVERESE PARAMETERS
            //        Point2D.Double[] templatePoints = new Point2D.Double[]{new Point2D.Double(2.3, 2),
            //            new Point2D.Double(2.3, 2), new Point2D.Double(2.3, 2)};
            //          Point2D.Double[]  classifiblePoints = new Point2D.Double[]{new Point2D.Double(21, 21),
            //            new Point2D.Double(2.3, 1211), new Point2D.Double(2, 2)};
            //            double[][] shapeContextTemplate = CostShapeContextInside.calculateShapeContextFromPoints2D(templatePoints);
            //            double[][] shapeContextClassifible = CostShapeContextInside.calculateShapeContextFromPoints2D(classifiblePoints);
            //            double diference = CostShapeContextInside.getCost(shapeContextTemplate, shapeContextClassifible);
            //            System.out.println("dif1: " + diference);
            //
            //            diference = CostShapeContextInside.getCost(shapeContextClassifible, shapeContextTemplate);
            //            System.out.println("dif1: " + diference);
            //        System.out.println(SymbolTestData.getFrequencies());
            // test hashmap
            //        HashMap<UserSymbol, Integer> map = new HashMap<UserSymbol, Integer>();
            //        UserSymbol us1 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "a");
            //        UserSymbol us2 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "a");
            //        UserSymbol us3 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "b");
            //        UserSymbol us4 = UserSymbol.newInstanceFromUserNickNameAndSymbolLabel("frank", "c");
            //
            //        map.put(us1, 1);
            //        map.put(us2, 2);
            //        map.put(us3, 1);
            //        map.put(us4, 1);
            //
            //        for (UserSymbol us : map.keySet()) {
            //            System.out.println(us.getUserNickName() + "-" + us.getSymbolLabel());
            //        }
            // TO TEST SHAPE CONTEXT WITH INVERESE PARAMETERS
            //        Point2D.Double[] templatePoints = new Point2D.Double[]{new Point2D.Double(2.3, 2),
            //            new Point2D.Double(2.3, 2), new Point2D.Double(2.3, 2)};
            //          Point2D.Double[]  classifiblePoints = new Point2D.Double[]{new Point2D.Double(21, 21),
            //            new Point2D.Double(2.3, 1211), new Point2D.Double(2, 2)};
            //            double[][] shapeContextTemplate = CostShapeContextInside.calculateShapeContextFromPoints2D(templatePoints);
            //            double[][] shapeContextClassifible = CostShapeContextInside.calculateShapeContextFromPoints2D(classifiblePoints);
            //            double diference = CostShapeContextInside.getCost(shapeContextTemplate, shapeContextClassifible);
            //            System.out.println("dif1: " + diference);
            //
            //            diference = CostShapeContextInside.getCost(shapeContextClassifible, shapeContextTemplate);
            //            System.out.println("dif1: " + diference);
            //        System.out.println(SymbolTestData.getFrequencies());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
