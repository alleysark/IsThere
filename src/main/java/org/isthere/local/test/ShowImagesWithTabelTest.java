package org.isthere.local.test;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for testing display images using JTable.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 30
 *        Time: 오후 5:02
 */
public class ShowImagesWithTabelTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    createAndShowGUi();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

    public static void createAndShowGUi() throws IOException {
        JFrame frame = new JFrame("Image show in table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ResultTable resultTable = new ResultTable(800, 600);
        resultTable.setOpaque(true);
        frame.setContentPane(resultTable);

        frame.pack();
        frame.setVisible(true);

    }

    static class ResultTable extends JPanel {
        private JScrollPane tableScrollPane;
        private ArrayList<String> imgPathes;
        private int result_width;
        private int result_height;

        public ResultTable(int width, int height) throws IOException {
            this.setSize(width, height);
            this.setVisible(true);

            result_width = 150;
            result_height = 150;

            imgPathes = new ArrayList<String>();

            imgPathes.add("D:\\Importants\\Test_images\\1.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\2.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\3.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\4.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\5.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\6.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\3.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\4.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\5.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\6.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\7.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\8.jpg");
            imgPathes.add("D:\\Importants\\Test_images\\9.jpg");

            ImageTableModel tableModel = new ImageTableModel(imgPathes, result_width, result_height);
            JTable resultTable = new JTable(tableModel);
            resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                protected void setValue(Object value) {
                    if (value instanceof ImageIcon) {
                        setIcon((ImageIcon) value);
                        setText("");
                    } else {
                        setIcon(null);
                        super.setValue(value);
                    }
                }
            });
            resultTable.setRowHeight(result_height);
            resultTable.setPreferredScrollableViewportSize(new Dimension(width, height));
            resultTable.setFillsViewportHeight(true);
            tableScrollPane = new JScrollPane(resultTable);

            add(tableScrollPane);
        }
    }

    public static class ImageTableModel extends AbstractTableModel {
        private ArrayList<ImageIcon> imageIconList;
        int rowCount;
        int columnCount;

        public ImageTableModel(ArrayList<String> imgPathes, int width, int height) {

            columnCount = 3;
            if (imgPathes.size() % columnCount == 0)
                rowCount = imgPathes.size() / columnCount;
            else
                rowCount = imgPathes.size() / columnCount + 1;
            imageIconList = new ArrayList<ImageIcon>(imgPathes.size());
            for (String aPath : imgPathes) {
                imageIconList.add(resize(width, height, new ImageIcon(aPath)));
            }
        }

        @Override
        public int getRowCount() {
            return rowCount;
        }

        @Override
        public int getColumnCount() {
            return columnCount;
        }

        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Search Result";
                case 1:
                    return "Search Result";
                case 2:
                    return "Search Result";
            }
            return "getColumnName error";
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex * rowCount + columnIndex > imageIconList.size()) return "";
            return imageIconList.get(rowIndex * rowCount + columnIndex);
        }

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
