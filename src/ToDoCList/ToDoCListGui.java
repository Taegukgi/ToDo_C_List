package ToDoCList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToDoCListGui extends JFrame implements ActionListener {
    private JPanel taskPanel, taskComponentPanel;

    public ToDoCListGui() {
        super("To Do List AppLication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JLabel bannerLabel = new JLabel("To do List");
        bannerLabel.setFont(createFont("resources/lemon_milk/LemonMilk.otf", 36f));
        bannerLabel.setBounds(
                (CommonConstants.GUI_SIZE.width - bannerLabel.getPreferredSize().width) / 2,
                15,
                CommonConstants.BANNER_SIZE.width,
                CommonConstants.BANNER_SIZE.height
        );

        taskPanel = new JPanel();

        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel);

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8,70, CommonConstants.TASKPANEL_SIZE.width, CommonConstants.TASKPANEL_SIZE.height);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setMaximumSize(CommonConstants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);

        // Task 추가 버튼
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(createFont("resources/lemon_milk/LemonMilk.otf", 18f));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setBounds(-5, CommonConstants.GUI_SIZE.height - 88 ,
                CommonConstants.ADDTASK_BUTTON_SIZE.width, CommonConstants.ADDTASK_BUTTON_SIZE.height);
        addTaskButton.addActionListener(this);

        // Task 저장 버튼
        JButton addSaveButton = new JButton("Save Task");
        addSaveButton.setFont(createFont("resources/lemon_milk/LemonMilk.otf", 18f));
        addSaveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addSaveButton.setBounds(-5, CommonConstants.GUI_SIZE.height - 88 ,
                CommonConstants.SAVETASK_BUTTON_SIZE.width, CommonConstants.SAVETASK_BUTTON_SIZE.height);
        addSaveButton.addActionListener(this);

        this.getContentPane().add(bannerLabel);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(addTaskButton);
        this.getContentPane().add(addSaveButton);
    }

    private Font createFont(String resource, float size) {
        String filePath = getClass().getClassLoader().getResource(resource).getPath();

        if(filePath.contains("%20")) {
            filePath = getClass().getClassLoader().getResource(resource).getPath()
                    .replaceAll("%20", " ");
        }

        try {
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
            return customFont;
        }catch ( Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equalsIgnoreCase("Add Task")){
            TaskComponent taskComponent = new TaskComponent(taskComponentPanel);
            taskComponentPanel.add(taskComponent);

            if(taskComponentPanel.getComponentCount() > 1) {
                TaskComponent previousTask = (TaskComponent) taskComponentPanel.getComponent(
                        taskComponentPanel.getComponentCount() - 2 );
                previousTask.getTaskField().setBackground(null);
            }

            taskComponent.getTaskField().requestFocus();
            repaint();
            revalidate();
        }
    }
}
