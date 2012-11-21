package isthere.local.test;

import isthere.IndexCreatorThread;
import net.semanticmetadata.lire.*;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 알고리즘의 테스트를 위한 GUI 클래스.
 * 간단하게 구현하였으며, 튼튼하게 만들어야 할 필요가 있다.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 16
 *        Time: 오전 11:26
 */

public class IsThereFrame extends JFrame {
    //사용될 객체들
    //패널
    private JPanel algorithmPanel;
    private JPanel buttonPanel;
    private JPanel progressPanel;

    //체크박스
    private JCheckBox checkbox_autoColorCorrelogram;
    private JCheckBox checkbox_CEDD;
    private JCheckBox checkbox_SimpleColorHistogram;
    private JCheckBox checkbox_ColorLayout;
    private JCheckBox checkbox_EdgeHistogram;
    private JCheckBox checkbox_FCTH;
    private JCheckBox checkbox_Gabor;
    private JCheckBox checkbox_JCD;
    private JCheckBox checkbox_jepgCoefficientHistogram;
    private JCheckBox checkbox_ScalableColor;
    private JCheckBox checkbox_Tamura;
    private JCheckBox checkbox_SIFT;

    private JCheckBox checkbox_UseAlreadyExistIndex;


    //SlideBar
    private JSlider slider_autoColorCorrelogram;
    private JSlider slider_CEDD;
    private JSlider slider_SimpleColorHistogram;
    private JSlider slider_ColorLayout;
    private JSlider slider_Edgehistogram;
    private JSlider slider_FCTH;
    private JSlider slider_Gabor;
    private JSlider slider_JCD;
    private JSlider slider_jpegCoefficientHistogram;
    private JSlider slider_ScalableColor;
    private JSlider slider_Tamura;
    private JSlider slider_SIFT;


    //버튼
    private JButton button_startIndexing;
    private JButton button_startSearching;

    //Progress Bar
    public JProgressBar progressBar;

    //변수
    private ArrayList<DescriptorWeight> selected;
    private String targetImage;
    public String trainPath;
    public String indexPath;
    private ImageSearcher searcher;
    IndexCreatorThread indexCreatorThread;
    private IndexReader reader;

    //Size 변수
    private int window_width = 800;
    private int window_height = 750;
    private int algorithmPanel_width = 400;
    private int algorithmPanel_height = 750;


    public IsThereFrame(int width, int height) {
        //가장 바깥 프레임 초기화
        super("Lire Test Program");
        //크기 설정

        this.setSize(width, height);
        setBounds(0, 0, window_width, window_height);
        getContentPane().setLayout(null);

        //변수 초기화
        InitVariables();

        //패널 초기화
        InitPanel();

        //버튼 추가
        AddComponents();
        //버튼에 액션 추가
        AddAction();

    }

    private void InitVariables() {
        //변수 초기화
        selected = new ArrayList<DescriptorWeight>();
        targetImage = "";
        indexPath = "./index/";
        indexCreatorThread = null;
        reader = null;

        //Progress Bar 초기화
        progressBar = new JProgressBar();
        progressBar.setFocusable(false);
        progressBar.setName("progressBar");
        progressBar.setString("Indexing Progress");
        progressBar.setStringPainted(true);
        progressBar.setVisible(true);

    }

    private void InitPanel() {


        //패널 초기화
        algorithmPanel = new JPanel();
        algorithmPanel.setBorder(new TitledBorder(null, "Algorithms", TitledBorder.LEADING, TitledBorder.TOP));
        algorithmPanel.setToolTipText("");
        algorithmPanel.setBounds(0, 0, algorithmPanel_width, algorithmPanel_height);
        getContentPane().add(algorithmPanel);

        buttonPanel = new JPanel();
        buttonPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP));
        buttonPanel.setToolTipText("");
        buttonPanel.setBounds(algorithmPanel_width, 10, window_width - algorithmPanel_width, 50);
        getContentPane().add(buttonPanel);


        progressPanel = new JPanel();
        progressPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP));
        progressPanel.setToolTipText("");
        progressPanel.setBounds(algorithmPanel_width, 50, window_width - algorithmPanel_width, 30);
    }

    private void AddComponents() {
        //버튼 달기
        buttonPanel.add(checkbox_UseAlreadyExistIndex = new JCheckBox("Use exist index"));

        buttonPanel.add(button_startIndexing = new JButton("Indexing"));
        button_startIndexing.setToolTipText("Index Start");

        buttonPanel.add(button_startSearching = new JButton("Searching"));
        button_startSearching.setToolTipText("Search Start");

        //ProgressBar 달기
        progressPanel.add(progressBar = new JProgressBar());
        add(progressPanel);


        //CheckBox & Slider 달기
        //AutoColorCorrelogram
        JPanel panel_autoColorCorrelogram = new JPanel();
        GridBagConstraints a = new GridBagConstraints();
        a.weightx = 0.4;
        a.fill = GridBagConstraints.HORIZONTAL;
        checkbox_autoColorCorrelogram = new JCheckBox("AutoColorCorrelogram");
        slider_autoColorCorrelogram = new JSlider(0, 10);
        slider_autoColorCorrelogram.setMajorTickSpacing(1);
        slider_autoColorCorrelogram.setPaintTicks(true);
        slider_autoColorCorrelogram.setPaintLabels(true);
        slider_autoColorCorrelogram.setAutoscrolls(true);
        slider_autoColorCorrelogram.setSnapToTicks(true);
        panel_autoColorCorrelogram.add(checkbox_autoColorCorrelogram, a);
        a.weightx = 1 - a.weightx;
        panel_autoColorCorrelogram.add(slider_autoColorCorrelogram, a);
        algorithmPanel.add(panel_autoColorCorrelogram);

        //CEDD
        JPanel panel_CEDD = new JPanel();
        GridBagConstraints b = new GridBagConstraints();
        b.weightx = 0.4;
        b.fill = GridBagConstraints.HORIZONTAL;
        checkbox_CEDD = new JCheckBox("CEDD");
        panel_CEDD.add(checkbox_CEDD, b);
        slider_CEDD = new JSlider(0, 10);
        slider_CEDD.setMajorTickSpacing(1);
        slider_CEDD.setPaintTicks(true);
        slider_CEDD.setPaintLabels(true);
        slider_CEDD.setAutoscrolls(true);
        slider_CEDD.setSnapToTicks(true);
        b.weightx = 1 - b.weightx;
        panel_CEDD.add(slider_CEDD, b);
        algorithmPanel.add(panel_CEDD);

        //ColorHistogram
        JPanel panel_SimpleColorHistogram = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.4;
        c.fill = GridBagConstraints.HORIZONTAL;
        checkbox_SimpleColorHistogram = new JCheckBox("SimpleColorHistogram");
        panel_SimpleColorHistogram.add(checkbox_SimpleColorHistogram, c);
        slider_SimpleColorHistogram = new JSlider(0, 10);
        slider_SimpleColorHistogram.setMajorTickSpacing(1);
        slider_SimpleColorHistogram.setPaintTicks(true);
        slider_SimpleColorHistogram.setPaintLabels(true);
        slider_SimpleColorHistogram.setAutoscrolls(true);
        slider_SimpleColorHistogram.setSnapToTicks(true);
        c.weightx = 1 - c.weightx;
        panel_SimpleColorHistogram.add(slider_SimpleColorHistogram, c);
        algorithmPanel.add(panel_SimpleColorHistogram);

        //ColorLayout
        JPanel panel_ColorLayout = new JPanel();
        GridBagConstraints d = new GridBagConstraints();
        d.weightx = 0.4;
        d.fill = GridBagConstraints.HORIZONTAL;
        checkbox_ColorLayout = new JCheckBox("ColorLayout");
        panel_ColorLayout.add(checkbox_ColorLayout, d);
        slider_ColorLayout = new JSlider(0, 10);
        slider_ColorLayout.setMajorTickSpacing(1);
        slider_ColorLayout.setPaintTicks(true);
        slider_ColorLayout.setPaintLabels(true);
        slider_ColorLayout.setAutoscrolls(true);
        slider_ColorLayout.setSnapToTicks(true);
        d.weightx = 1 - d.weightx;
        panel_ColorLayout.add(slider_ColorLayout, d);
        algorithmPanel.add(panel_ColorLayout);

        //EdgeHistogram
        JPanel panel_Edgehistogram = new JPanel();
        GridBagConstraints e = new GridBagConstraints();
        e.weightx = 0.4;
        e.fill = GridBagConstraints.HORIZONTAL;
        checkbox_EdgeHistogram = new JCheckBox("EdgeHistogram");
        panel_Edgehistogram.add(checkbox_EdgeHistogram, e);
        slider_Edgehistogram = new JSlider(0, 10);
        slider_Edgehistogram.setMajorTickSpacing(1);
        slider_Edgehistogram.setPaintTicks(true);
        slider_Edgehistogram.setPaintLabels(true);
        slider_Edgehistogram.setAutoscrolls(true);
        slider_Edgehistogram.setSnapToTicks(true);
        e.weightx = 1 - e.weightx;
        panel_Edgehistogram.add(slider_Edgehistogram, e);
        algorithmPanel.add(panel_Edgehistogram);

        //FCTH
        JPanel panel_FCTH = new JPanel();
        GridBagConstraints f = new GridBagConstraints();
        f.weightx = 0.4;
        f.fill = GridBagConstraints.HORIZONTAL;
        checkbox_FCTH = new JCheckBox("FCTH");
        panel_FCTH.add(checkbox_FCTH, f);
        slider_FCTH = new JSlider(0, 10);
        slider_FCTH.setMajorTickSpacing(1);
        slider_FCTH.setPaintTicks(true);
        slider_FCTH.setPaintLabels(true);
        slider_FCTH.setAutoscrolls(true);
        slider_FCTH.setSnapToTicks(true);
        f.weightx = 1 - f.weightx;
        panel_FCTH.add(slider_FCTH, f);
        algorithmPanel.add(panel_FCTH);

        //Gabor
        JPanel panel_Gabor = new JPanel();
        GridBagConstraints g = new GridBagConstraints();
        g.weightx = 0.4;
        g.fill = GridBagConstraints.HORIZONTAL;
        checkbox_Gabor = new JCheckBox("Gabor");
        panel_Gabor.add(checkbox_Gabor, g);
        slider_Gabor = new JSlider(0, 10);
        slider_Gabor.setMajorTickSpacing(1);
        slider_Gabor.setPaintTicks(true);
        slider_Gabor.setPaintLabels(true);
        slider_Gabor.setAutoscrolls(true);
        slider_Gabor.setSnapToTicks(true);
        g.weightx = 1 - g.weightx;
        panel_Gabor.add(slider_Gabor, g);
        algorithmPanel.add(panel_Gabor);

        //JCD
        JPanel panel_JCD = new JPanel();
        GridBagConstraints h = new GridBagConstraints();
        h.weightx = 0.4;
        h.fill = GridBagConstraints.HORIZONTAL;
        checkbox_JCD = new JCheckBox("JCD");
        panel_JCD.add(checkbox_JCD, h);
        slider_JCD = new JSlider(0, 10);
        slider_JCD.setMajorTickSpacing(1);
        slider_JCD.setPaintTicks(true);
        slider_JCD.setPaintLabels(true);
        slider_JCD.setAutoscrolls(true);
        slider_JCD.setSnapToTicks(true);
        h.weightx = 1 - h.weightx;
        panel_JCD.add(slider_JCD, h);
        algorithmPanel.add(panel_JCD);

        //JPEG CoefficientHistogram
        JPanel panel_JpegCoefficientHistogram = new JPanel();
        GridBagConstraints i = new GridBagConstraints();
        i.weightx = 0.4;
        i.fill = GridBagConstraints.HORIZONTAL;
        checkbox_jepgCoefficientHistogram = new JCheckBox("JpegCoefficientHistogram");
        panel_JpegCoefficientHistogram.add(checkbox_jepgCoefficientHistogram, i);
        slider_jpegCoefficientHistogram = new JSlider(0, 10);
        slider_jpegCoefficientHistogram.setMajorTickSpacing(1);
        slider_jpegCoefficientHistogram.setPaintTicks(true);
        slider_jpegCoefficientHistogram.setPaintLabels(true);
        slider_jpegCoefficientHistogram.setAutoscrolls(true);
        slider_jpegCoefficientHistogram.setSnapToTicks(true);
        i.weightx = 1 - i.weightx;
        panel_JpegCoefficientHistogram.add(slider_jpegCoefficientHistogram, i);
        algorithmPanel.add(panel_JpegCoefficientHistogram);

        //Scalable Color
        JPanel panel_ScalableColor = new JPanel();
        GridBagConstraints j = new GridBagConstraints();
        j.weightx = 0.4;
        j.fill = GridBagConstraints.HORIZONTAL;
        checkbox_ScalableColor = new JCheckBox("Scaleble Color");
        panel_ScalableColor.add(checkbox_ScalableColor, j);
        slider_ScalableColor = new JSlider(0, 10);
        slider_ScalableColor.setMajorTickSpacing(1);
        slider_ScalableColor.setPaintTicks(true);
        slider_ScalableColor.setPaintLabels(true);
        slider_ScalableColor.setAutoscrolls(true);
        slider_ScalableColor.setSnapToTicks(true);
        j.weightx = 1 - j.weightx;
        panel_ScalableColor.add(slider_ScalableColor, j);
        algorithmPanel.add(panel_ScalableColor);

        //Tamura
        JPanel panel_Tamura = new JPanel();
        GridBagConstraints k = new GridBagConstraints();
        k.weightx = 0.4;
        k.fill = GridBagConstraints.HORIZONTAL;
        checkbox_Tamura = new JCheckBox("Tamura");
        panel_Tamura.add(checkbox_Tamura, j);
        slider_Tamura = new JSlider(0, 10);
        slider_Tamura.setMajorTickSpacing(1);
        slider_Tamura.setPaintTicks(true);
        slider_Tamura.setPaintLabels(true);
        slider_Tamura.setAutoscrolls(true);
        slider_Tamura.setSnapToTicks(true);
        k.weightx = 1 - k.weightx;
        panel_Tamura.add(slider_Tamura, j);
        algorithmPanel.add(panel_Tamura);

        //SIFT : Don't use
        JPanel panel_SIFT = new JPanel();
        GridBagConstraints l = new GridBagConstraints();
        l.weightx = 0.4;
        l.fill = GridBagConstraints.HORIZONTAL;
        checkbox_SIFT = new JCheckBox("SIFT");
        checkbox_SIFT.setEnabled(false);
        panel_SIFT.add(checkbox_SIFT, j);
        slider_SIFT = new JSlider(0, 10);
        slider_SIFT.setMajorTickSpacing(1);
        slider_SIFT.setPaintTicks(true);
        slider_SIFT.setPaintLabels(true);
        slider_SIFT.setAutoscrolls(true);
        slider_SIFT.setSnapToTicks(true);
        slider_SIFT.setEnabled(false);
        l.weightx = 1 - l.weightx;
        panel_SIFT.add(slider_SIFT, j);
        algorithmPanel.add(panel_SIFT);
    }


    private void AddAction() {
        // Use exist index 체크박스 액션 추가
        checkbox_UseAlreadyExistIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UseExistIndexFiles();
            }
        });

        // Index 버튼 액션 추가
        button_startIndexing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartIndexingAction();
            }
        });

        // Start 버튼 액션 추가
        button_startSearching.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartSearchingAction();
            }
        });
    }

    private void UseExistIndexFiles() {
        if (checkbox_UseAlreadyExistIndex.isSelected()) {
            button_startIndexing.setEnabled(false);
        } else {
            button_startIndexing.setEnabled(true);
        }
    }

    private void StartIndexingAction() {
        JFileChooser jfc = new JFileChooser(".");
        jfc.setDialogTitle("Select directory to index ...");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jfc.showOpenDialog(buttonPanel) == JFileChooser.APPROVE_OPTION) {
            try {
                trainPath = jfc.getSelectedFile().getCanonicalPath();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //확인창 출력
            int reply = JOptionPane.showConfirmDialog(null, "trainPath : " + trainPath + "\n" + "indexPath : " + indexPath + "\n is right?");
            if (reply != JOptionPane.YES_OPTION) return;
        }
        //경로가 입력 되었아면 indexing 시작
        if (!StringUtils.isEmpty(trainPath)) {
            indexCreatorThread = new IndexCreatorThread(this);
            button_startIndexing.setEnabled(false);
            indexCreatorThread.start();
            button_startIndexing.setEnabled(true);
        }
    }

    private void StartSearchingAction() {
        try {
            reader = IndexReader.open(FSDirectory.open(new File(indexPath)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (indexCreatorThread == null && checkbox_UseAlreadyExistIndex.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Create Index First!(indexCreater is null)");
            return;
        }
        if (reader == null) {
            JOptionPane.showMessageDialog(null, "Create Index First!(reader is null)");
            return;
        }
        selected.clear();
        targetImage = "";

        //이미지 파일 불러오기
        JFileChooser jfc = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG", "jpg");
        jfc.setFileFilter(filter);
        if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(buttonPanel)) {
            try {
                targetImage = jfc.getSelectedFile().getCanonicalPath();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            //확인 창 출력
            int reply = JOptionPane.showConfirmDialog(null, "target : " + targetImage + "\n" + "\"targetImage\" is right?");
            if (reply != JOptionPane.YES_OPTION) return;
        }

        //이미지가 제대로 선택 되었을때만 수행
        if (targetImage.length() > 4) {
            //선택한 알고리즘 목록 생성
            //비율을 정하고 싶으면 1.0f 부분을 수정
            if (checkbox_autoColorCorrelogram.isSelected()) {
                //                        selected.add(new DescriptorWeight("AutoColorCorrelogram"));
                selected.add(new DescriptorWeight("AutoColorCorrelogram", (float) slider_autoColorCorrelogram.getValue()));
            }
            if (checkbox_CEDD.isSelected()) {
                //                        selected.add(new DescriptorWeight("CEDD"));
                selected.add(new DescriptorWeight("CEDD", (float) slider_CEDD.getValue()));
            }
            if (checkbox_SimpleColorHistogram.isSelected()) {
                //                        selected.add(new DescriptorWeight("SimpleColorHistogram"));
                selected.add(new DescriptorWeight("SimpleColorHistogram", (float) slider_SimpleColorHistogram.getValue()));
            }
            if (checkbox_ColorLayout.isSelected()) {
                //                        selected.add(new DescriptorWeight("ColorLayout"));
                selected.add(new DescriptorWeight("ColorLayout", (float) slider_ColorLayout.getValue()));
            }
            if (checkbox_EdgeHistogram.isSelected()) {
                //                        selected.add(new DescriptorWeight("EdgeHistogram"));
                selected.add(new DescriptorWeight("EdgeHistogram", (float) slider_Edgehistogram.getValue()));
            }
            if (checkbox_FCTH.isSelected()) {
                //                        selected.add(new DescriptorWeight("FCTH"));
                selected.add(new DescriptorWeight("FCTH", (float) slider_FCTH.getValue()));
            }
            if (checkbox_Gabor.isSelected()) {
                //                        selected.add(new DescriptorWeight("Gabor"));
                selected.add(new DescriptorWeight("Gabor", (float) slider_Gabor.getValue()));
            }
            if (checkbox_JCD.isSelected()) {
                //                        selected.add(new DescriptorWeight("JCD"));
                selected.add(new DescriptorWeight("JCD", (float) slider_JCD.getValue()));
            }
            if (checkbox_jepgCoefficientHistogram.isSelected()) {
                //                        selected.add(new DescriptorWeight("JpegCoefficientHistogram"));
                selected.add(new DescriptorWeight("JpegCoefficientHistogram", (float) slider_jpegCoefficientHistogram.getValue()));
            }
            if (checkbox_ScalableColor.isSelected()) {
                //                        selected.add(new DescriptorWeight("ScalableColor"));
                selected.add(new DescriptorWeight("ScalableColor", (float) slider_ScalableColor.getValue()));
            }
            if (checkbox_Tamura.isSelected()) {
                //                        selected.add(new DescriptorWeight("Tamura"));
                selected.add(new DescriptorWeight("Tamura", (float) slider_Tamura.getValue()));
            }
            if (checkbox_SIFT.isSelected()) {
                //                        selected.add(new DescriptorWeight("SIFT"));
                selected.add(new DescriptorWeight("SIFT", (float) slider_SIFT.getValue()));
            }

            //선택한 알고리즘이 있을 때 검색 수행
            if (selected.size() != 0)
                searcher = ImageSearcherFactory.createChainedSearcher(Integer.MAX_VALUE, selected);
            else //선택한 알고리즘이 없다면 검색 수행 안하고 경고창 띄움
                JOptionPane.showMessageDialog(null, "Select algorithm");

        } else {
            JOptionPane.showMessageDialog(null, "Please select an image");
        }

        if(StringUtils.isEmpty(targetImage)) {
            return;
        }
        try {
            //선택한 이미지 불러오기
            FileInputStream targetImageStream = new FileInputStream(targetImage);
            BufferedImage timg = ImageIO.read(targetImageStream);
            ImageSearchHits hits;
            //index에서 검색
            hits = searcher.search(timg, reader);
            //검색 결과 새 창으로 띄우기
            ResultFrame resultFrame = new ResultFrame(hits);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}

/**
 * 검색 결과를 출력 할 프레임.
 * 검색결과의 가장 마지막 부분에서 에러가 발생한다.
 */
class ResultFrame extends JFrame {
    private ResultTable resultTable;    //이미지가 출력될 Table
    private ArrayList<String> resultPathes; //검색 결과 이미지 파일 경로(파일이름 포함)

    public ResultFrame(ImageSearchHits searchHits) {
        super("Search Result"); //창 설정
        resultPathes = new ArrayList<String>(searchHits.length()); //객체 초기화
        getResultPathes(searchHits); //검색 결과로부터 이미지 파일 경로 추출
        try {
            resultTable = new ResultTable(resultPathes, 800, 600); //Table 생성
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultTable.setOpaque(true);
        this.setContentPane(resultTable); //Table 붙이기
        this.pack();
        this.setVisible(true); //출력
    }

    /**
     * 검색 결과로부터 이미지 파일경로를 추출. 반환보다는 전역변수에 바로 대입.
     *
     * @param searchHits 검색 결과. ImageSearchHits
     */
    private void getResultPathes(ImageSearchHits searchHits) {
        System.out.println("searchHit.length : "+searchHits.length());
        for (int i = 0; i < searchHits.length(); i++) {
            resultPathes.add(searchHits.doc(i).getFieldable(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue());
        }
    }

    /**
     * 이미지가 출력 될 Table 클래스
     */
    class ResultTable extends JPanel {
        private JScrollPane tableScrollPane; //스크롤 바
        private ArrayList<String> resultPathes; //검색 결과 이미지 경로
        private int result_width; //출력될 이미지의 너비
        private int result_height; //출력될 이미지의 높이

        public ResultTable(ArrayList<String> resultArray, int width, int height) throws IOException {
            this.setSize(width, height); //전체 크기 설정
            this.setVisible(true);      //출력

            result_width = 150;          //출력될 이미지의 너비 설정
            result_height = 150;         //출력될 이미지의 높이 설정

            resultPathes = new ArrayList<String>(); //변수 초기화
            resultPathes.addAll(resultArray);       //출력할 이미지 경로 설정


            ImageTableModel tableModel = new ImageTableModel(resultPathes, result_width, result_height); //Table의 모델 지정
            JTable resultTable = new JTable(tableModel); //Table 생성
            resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() { //출력 방식 설정.
                protected void setValue(Object value) {
                    if (value instanceof ImageIcon) { //받아오는 객체가 이미지라면
                        setIcon((ImageIcon) value);     //이미지 출력
                        setText("");
                    } else {    //이미지가 아니라면
                        System.out.println("----");
                        setIcon(null);  //출력 안함
                        super.setValue(value);
                    }
                }
            });
            resultTable.setRowHeight(result_height);    //Table의 각 row의 높이 설정
            resultTable.setPreferredScrollableViewportSize(new Dimension(width, height)); //viewport 설정 - 잘 모름 인터넷에서 가져온 부분
            resultTable.setFillsViewportHeight(true);
            tableScrollPane = new JScrollPane(resultTable); //스크롤바에 붙이기

            add(tableScrollPane); //Frame 에 붙이기
        }
    }

    /**
     * Table 의 속성을 결정시켜줄 Table Model 클래스.
     * 출력할 열 갯수는 임의로 지정해주어야 한다.
     */
    private class ImageTableModel extends AbstractTableModel {
        private ArrayList<ImageIcon> imageIconList; //Table에 출력될 데이터 - ImageIcon 클래스
        int rowCount;   //전체 행 갯수
        int columnCount;    //전체 열 갯수

        /**
         * ImageTableModel 생성자. 이미지의 경로와, 출력할 이미지 썸네일의 크기를 입력받는다.
         *
         * @param imgPathes 출력할 이미지의 경로가 포함되어있는 ArrayList
         * @param width     출력할 이미지 썸네일의 너비
         * @param height    출력할 이미지 썸네일의 높이
         */
        public ImageTableModel(ArrayList<String> imgPathes, int width, int height) {

            columnCount = 3;    //출력할 열 갯수는 임의로 지정
            if (imgPathes.size() % columnCount == 0) //열 갯수에 따라 행 갯수 결정
                rowCount = imgPathes.size() / columnCount;
            else
                rowCount = imgPathes.size() / columnCount + 1;

            imageIconList = new ArrayList<ImageIcon>(imgPathes.size()); //배열 초기화
            //각 이미지를 작게 줄여서 Table의 데이터에 저장.
            for (String aPath : imgPathes) {
                imageIconList.add(resize(width, height, new ImageIcon(aPath)));
            }
        }

        @Override
        //전체 행 갯수 반환
        public int getRowCount() {
            return rowCount;
        }

        @Override
        //전체 열 갯수 반환
        public int getColumnCount() {
            return columnCount;
        }

        //Table의 각 열의 이름 지정
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                case 1:
                case 2:
                    return "Search Result";
            }
            return "getColumnName error";
        }

        @Override
        //Table의 데이터에 (행, 열)로 접근할 때 어떤 데이터에 접근할 것인지 지정
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex * columnCount + columnIndex > imageIconList.size()) { //남는 칸에는 아무것도 출력 안함
                System.out.println("Return \"\" : rowIndex=" + rowIndex + " columnIndex=" + columnIndex + "imageIconList.size()=" + imageIconList.size());
                return "";
            }
            return imageIconList.get(rowIndex * columnCount + columnIndex);
        }

        /**
         * 파라메터로 전달된 이미지를 resize 해서 반환
         *
         * @param width  반환할 이미지의 너비
         * @param height 반환할 이미지의 높이
         * @param img    resize 할 대상
         * @return resize 된 이미지
         */
        private ImageIcon resize(int width, int height, ImageIcon img) {
            Image resized = img.getImage();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            //            BufferedImage bi = new BufferedImage(resized.getWidth(null), resized.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(resized, 0, 0, width, height, null);
            return new ImageIcon(bi);
        }
    }
}