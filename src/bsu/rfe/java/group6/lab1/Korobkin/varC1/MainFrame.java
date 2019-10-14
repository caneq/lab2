package bsu.rfe.java.group6.lab1.Korobkin.varC1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainFrame extends JFrame{
    private static final Dimension size = new Dimension(750,600);
    private double mem1 ;
    private double mem2;
    private double mem3;
    JTextField textFieldM1;
    JTextField textFieldM2;
    JTextField textFieldM3;
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    private JTextField textFieldRes;
    private ButtonGroup funcRadioButtons = new ButtonGroup();
    private ButtonGroup memRadioButtons = new ButtonGroup();
    private ImageIcon iconFunc1 = new ImageIcon("src/bsu/rfe/java/group6/lab1/Korobkin/varC1/f1.jpg");
    private ImageIcon iconFunc2 = new ImageIcon("src/bsu/rfe/java/group6/lab1/Korobkin/varC1/f2.jpg");
    private JLabel labelImg = new JLabel();

    private double func1(double x, double y, double z) throws ArithmeticException{
        if (y <= 0) throw new ArithmeticException("логарифм от неположительного числа");
        double tmp = x*x + Math.sin(z) + Math.exp(Math.cos(x));
        if (tmp < 0) throw new ArithmeticException("корень из отрицательного числа");
        return Math.sin(Math.log(y) + Math.sin(Math.PI * y *y))*Math.pow(tmp, (double)1/4);
    }

    private double func2(double x, double y, double z) throws ArithmeticException{
        if (y <= 1) throw new ArithmeticException("логарифм от неположительного числа");
        if(x == 0) throw new ArithmeticException("деление на 0");
        if(x < 0) throw new ArithmeticException("корень из отрицательного числа");
        double tmp = Math.exp(Math.cos(x)) + Math.pow(Math.sin(Math.PI * z), 2);
        if(tmp < 0) throw new ArithmeticException("корень из отрицательного числа");
        return Math.pow((Math.cos(Math.exp(x)) + Math.log((1 + y) * (1 + y)) + Math.sqrt(tmp)
                + Math.sqrt(1 / x) + Math.cos(y * y)), Math.sin(z));
    }

    private Box hboxFormulaType = Box.createHorizontalBox();
    private Box hboxMemType = Box.createHorizontalBox();
    private int funcId = 1;
    private int memId = 1;

    private void addFuncRadioButton(String buttonName, final int funcId){
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.funcId = funcId;
                MainFrame.this.labelImg.setIcon((funcId == 1) ? iconFunc1 : iconFunc2);
            }
        });
        funcRadioButtons.add(button);
        hboxFormulaType.add(button);
    }

    private void addMemRadioButton(String buttonName, final int memId){
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.memId = memId;
            }
        });
        memRadioButtons.add(button);
        hboxMemType.add(button);
    }

    public MainFrame(){
        super("Вычисление формулы");
        setSize(size);
        setLocation((getToolkit().getScreenSize().width - size.width)/2,(getToolkit().getScreenSize().height - size.height)/2);
        hboxFormulaType.add(Box.createHorizontalGlue());
        addFuncRadioButton("Формула 1", 1);
        addFuncRadioButton("Формула 2", 2);
        funcRadioButtons.setSelected(funcRadioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());

        labelImg.setIcon(iconFunc1);
        Box hboxImg = Box.createHorizontalBox();
        hboxImg.add(Box.createHorizontalGlue());
        hboxImg.add(labelImg);
        //hboxImg.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        hboxImg.add(Box.createHorizontalGlue());

        JLabel labelX = new JLabel("X:");
        JLabel labelY = new JLabel("Y:");
        JLabel labelZ = new JLabel("Z:");
        textFieldX = new JTextField("0", 15);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        textFieldY = new JTextField("0", 15);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        textFieldZ = new JTextField("0", 15);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(labelY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(labelZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        JLabel labelForRes = new JLabel("Результат:");
        textFieldRes = new JTextField("0", 15);
        textFieldRes.setMaximumSize(textFieldRes.getPreferredSize());
        textFieldRes.setEditable(false);
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForRes);
        hboxResult.add(textFieldRes);
        hboxResult.add(Box.createHorizontalGlue());

        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    double x = Double.parseDouble(textFieldX.getText());
                    double y = Double.parseDouble(textFieldY.getText());
                    double z = Double.parseDouble(textFieldZ.getText());
                    double res = (funcId == 1) ? func1(x,y,z) : func2(x,y,z);
                    textFieldRes.setText(String.valueOf(res));
                }
                catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.ERROR_MESSAGE);
                }
                catch (ArithmeticException ex){
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Арифметическая ошибка: " + ex.getMessage(),
                            "Арифметическая ошибка" , JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldRes.setText("0");
            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(5));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());

        addMemRadioButton("Переменная1", 1);
        addMemRadioButton("Переменная2", 2);
        addMemRadioButton("Переменная3", 3);
        memRadioButtons.setSelected(memRadioButtons.getElements().nextElement().getModel(), true);

        JButton buttonMemPlus = new JButton("M+");
        buttonMemPlus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (memId == 1) {
                        mem1 += Double.parseDouble(textFieldRes.getText());
                        textFieldM1.setText(String.valueOf(mem1));
                    } else if (memId == 2) {
                        mem2 += Double.parseDouble(textFieldRes.getText());
                        textFieldM2.setText(String.valueOf(mem2));
                    } else if (memId == 3) {
                        mem3 += Double.parseDouble(textFieldRes.getText());
                        textFieldM3.setText(String.valueOf(mem3));
                    }
                }
                catch (NumberFormatException err){
                    JOptionPane.showMessageDialog(MainFrame.this,"Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        JButton buttonMemC = new JButton("MC");
        buttonMemC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (memId == 1){
                    mem1 = 0.0;
                    textFieldM1.setText("0");
                }
                else if (memId == 2){
                    mem2 = 0.0;
                    textFieldM2.setText("0");
                }
                else if (memId == 3){
                    mem3 = 0.0;
                    textFieldM3.setText("0");
                }
            }

        });

        textFieldM1 = new JTextField("0", 15);
        textFieldM1.setMaximumSize(textFieldM1.getPreferredSize());
        textFieldM2 = new JTextField("0", 15);
        textFieldM2.setMaximumSize(textFieldM1.getPreferredSize());
        textFieldM3 = new JTextField("0", 15);
        textFieldM3.setMaximumSize(textFieldM1.getPreferredSize());
        textFieldM1.setEditable(false);
        textFieldM2.setEditable(false);
        textFieldM3.setEditable(false);

        JLabel labelMem1= new JLabel("Mem1:");
        JLabel labelMem2= new JLabel("Mem2:");
        JLabel labelMem3= new JLabel("Mem3:");

        Box hboxMem = Box.createHorizontalBox();
        hboxMem.add(labelMem1);
        hboxMem.add(textFieldM1);
        hboxMem.add(labelMem2);
        hboxMem.add(textFieldM2);
        hboxMem.add(labelMem3);
        hboxMem.add(textFieldM3);

        Box hboxMemButtons = Box.createHorizontalBox();
        hboxMemButtons.add(buttonMemPlus);
        hboxMemButtons.add(Box.createHorizontalStrut(5));
        hboxMemButtons.add(buttonMemC);


        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxImg);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxFormulaType);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxVariables);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxResult);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxButtons);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxMemType);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxMemButtons);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(hboxMem);
        contentBox.add(Box.createVerticalStrut(5));
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
