package org.isthere.local.test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Test class for displaying images on list with JPanel
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 30
 *        Time: 오후 3:52
 */
public class ShowImagesWithListTest {
    public static void main(String[] args) throws IOException {
        ResultFrame resultFrame = new ResultFrame();
        resultFrame.setVisible(true);
        resultFrame.setDefaultCloseOperation(resultFrame.EXIT_ON_CLOSE);
    }

    static class ResultFrame extends JFrame {
        private JPanel listPanel;
        private JList list;
        private DefaultListModel listModel;
        private final String imageThumbnailString = "Thumbnail";
        private JScrollPane listScrollPane;

        public ResultFrame() throws IOException {
            super("Search Result");
            this.setSize(800, 600);
            this.setVisible(true);

            listPanel = new JPanel(new BorderLayout());

            setListModel();
            createList();

            listPanel.add(listScrollPane);
            listPanel.setVisible(true);
            add(listPanel);
        }

        private void createList() {
            list = new JList(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setVisibleRowCount(5);
            listScrollPane = new JScrollPane(list);
        }

        private void setListModel() throws IOException {
            int width = 150;
            int height = 150;

            listModel = new DefaultListModel();
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\1.jpg")));
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\2.jpg")));
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\3.jpg")));
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\4.jpg")));
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\5.jpg")));
            listModel.addElement(resize(width, height, new ImageIcon("D:\\Importants\\Test_images\\6.jpg")));
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

