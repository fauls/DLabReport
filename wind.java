package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.time.LocalDate;
import java.time.Month;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class wind extends JFrame {

	private JPanel contentPane;
	private JTextField t_sum;
	private JTextField t_day;
	private JTextField t_year;
	private JTextField t_month;
	private JLabel l_time_vklad;
	private JTextField t_time_vklad;
	private JLabel label;
	private JLabel l_proc;
	private JTextField t_proc;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JButton b_go;
	private JCheckBox ch_capital;
	private JTable table;
	private JTextPane t_prok_out;
	private JTextPane t_ost_vklad;
	private JComboBox с_type_proc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					wind frame = new wind();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public wind() {//главная функция
		initComp();//создание компонентов
		create_event();//обработка событий
	}
	
	//
	//ОБРАБОТКА СОБЫТИЙ
	//
	private void create_event()
	{
		
		//ОБРАБОТКА ФЛАЖКА
		ch_capital.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ch_capital.isSelected()) //без капитализации вывод недоступен (т.к ничего не меняет)
				{с_type_proc.setEnabled(true);}else
				{с_type_proc.setEnabled(false);}
			}
		});
		
		//ОБРАБОТКА КНОПКИ
		b_go.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {//нажатие на кнопку "Рассчитать"
				Deposit deposit = new Deposit(); //создаём новый класс депозита
				//ввод значений и проверка
				
				//
				//ОБРАБОТКА ВВОДА
				//
				
				try{//сумма вклада, переменная deposit
					  deposit.deposit = Integer.parseInt(t_sum.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Неверно указана сумма вклада");
					}
				
				try{//день
					  int day = Integer.parseInt(t_day.getText());
					  
					  if (day<1 || day>31) {throw new NumberFormatException();}else//генерируем исключение если выходим за пределы
					  {deposit.day = day;}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Введите день числом от 1 до 31");
					}
				try{//месяц
					  int month = Integer.parseInt(t_month.getText());
					  
					  if (month<1 || month>12) {throw new NumberFormatException();}else 
					  {deposit.month = month;}
					  
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Введите месяц числом от 1 до 12");
					}
				try{//год
					  int year = Integer.parseInt(t_year.getText());
					  
					  if (year<0) {throw new NumberFormatException();}else
					  {deposit.year = year;}
					  
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Введите год числом больше 0");
					}
				
				try{//срок вклада
					  int term = Integer.parseInt(t_time_vklad.getText());
					  
					  if (term<0) {throw new NumberFormatException();}else
					  {deposit.term = term;}
					
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Введите срок числом больше 0");
					}
				try{//процентная ставка
					  int interest = Integer.parseInt(t_proc.getText());
					  
					  if (interest<0) {throw new NumberFormatException();}else
					  {deposit.interest = interest;}
					  
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Введите число без знака процента");
					}
				
				deposit.capitaliz = ch_capital.isSelected(); //капитализация
				deposit.proc = с_type_proc.getSelectedIndex();//капитализация, 0 - ежемесячно,1 - ежеквартально, 2 - ежегодно
				
				//
				//ВЫЧИСЛЕНИЯ
				//
				deposit.calculate();//вычисления
				
				//
				//ВЫВОД ИНФОРМАЦИИ
				//
				//текстбоксы
				t_prok_out.setText(String.format("%.2f",deposit.finalproc));//форматный вывод
				t_ost_vklad.setText(String.format("%.2f", deposit.deposit));
				//таблица
						table.setModel(new DefaultTableModel(//инициализация таблицы
				deposit.table,//двухмерным массивом из deposit
				new String[] {
					"Дата", "Проценты", "Остаток" //и заголовком
				}
			));
			deposit = null;//очистка класса, на всякий случай
			}
		
		});
	}
	
	
//
//ИНИЦИАЛИЗАЦИЯ
//(по большей части код из конструктора)

	private void initComp()
	{
		setTitle("\u0414\u0435\u043F\u043E\u0437\u0438\u0442\u043D\u044B\u0439 \u043A\u0430\u043B\u044C\u043A\u0443\u043B\u044F\u0442\u043E\u0440");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel l_sum = new JLabel("\u0421\u0443\u043C\u043C\u0430 \u0432\u043A\u043B\u0430\u0434\u0430:");
		
		t_sum = new JTextField();
		t_sum.setToolTipText("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u0441\u0443\u043C\u043C\u0443");
		t_sum.setColumns(10);
		
		JLabel l_date = new JLabel("\u0414\u0430\u0442\u0430 \u043E\u0442\u043A\u0440\u044B\u0442\u0438\u044F:");
		
		//представление даты переменными
        LocalDate date = LocalDate.now();

        int year = date.getYear();
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
		
        //ввод текущей даты в текстбоксы
		t_day = new JTextField();
		t_day.setText(""+day);
		t_day.setColumns(10);
		
		t_year = new JTextField();
		t_year.setText(""+year);
		t_year.setColumns(10);
		
		t_month = new JTextField();
		t_month.setText(""+(month.ordinal()+1));
		t_month.setColumns(10);
		
		//автогенерация
		
		//Hic sunt dracones
		l_time_vklad = new JLabel("\u0421\u0440\u043E\u043A \u0432\u043A\u043B\u0430\u0434\u0430:");
		
		t_time_vklad = new JTextField();
		t_time_vklad.setToolTipText("\u0412 \u0434\u043D\u044F\u0445");
		t_time_vklad.setText("365");
		t_time_vklad.setColumns(10);
		
		label = new JLabel("\u0434\u043D\u0435\u0439.");
		
		l_proc = new JLabel("\u041F\u0440\u043E\u0446\u0435\u043D\u0442\u043D\u0430\u044F \u0441\u0442\u0430\u0432\u043A\u0430:");
		
		t_proc = new JTextField();
		t_proc.setToolTipText("\u0412 \u043F\u0440\u043E\u0446\u0435\u043D\u0442\u0430\u0445");
		t_proc.setText("10");
		t_proc.setColumns(10);
		
		label_2 = new JLabel("%");
		
		ch_capital = new JCheckBox("\u041A\u0430\u043F\u0438\u0442\u0430\u043B\u0438\u0446\u0430\u0446\u0438\u044F \u043F\u0440\u043E\u0446\u0435\u043D\u0442\u043E\u0432");

		ch_capital.setToolTipText("\u041F\u0440\u043E\u0446\u0435\u043D\u0442\u044B \u0441 \u0443\u0441\u0442\u0430\u043D\u043E\u0432\u043B\u0435\u043D\u043D\u043E\u0439 \u043F\u0435\u0440\u0435\u043E\u0434\u0438\u0447\u043D\u043E\u0441\u0442\u044C\u044E \u0434\u043E\u0431\u0430\u0432\u043B\u044F\u044E\u0442\u0441\u044F \u043A \u0432\u043A\u043B\u0430\u0434\u0443");
		
		JLabel label_1 = new JLabel("Частота капитализации:");
		
		с_type_proc = new JComboBox();
		с_type_proc.setToolTipText("\u0412\u044B\u043F\u043B\u0430\u0442\u0430 \u0438\u043C\u0435\u0435\u0442 \u0437\u043D\u0430\u0447\u0435\u043D\u0438\u0435 \u0442\u043E\u043B\u044C\u043A\u043E \u043F\u0440\u0438 \u043A\u0430\u043F\u0438\u0442\u0430\u043B\u0438\u0437\u0430\u0446\u0438\u0438 \u043F\u0440\u043E\u0446\u0435\u043D\u0442\u043E\u0432");
		с_type_proc.setEnabled(false);
		с_type_proc.setModel(new DefaultComboBoxModel(Proc.values()));
		
		b_go = new JButton("\u0420\u0430\u0441\u0441\u0447\u0438\u0442\u0430\u0442\u044C");

		
		label_3 = new JLabel("Итоговая сумма:");
		
		label_4 = new JLabel("\u0412\u044B\u043F\u043B\u0430\u0447\u0435\u043D\u043E \u043F\u0440\u043E\u0446\u0435\u043D\u0442\u043E\u0432:");
		
		t_ost_vklad = new JTextPane();
		t_ost_vklad.setEditable(false);
		
		t_prok_out = new JTextPane();
		t_prok_out.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(l_time_vklad, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(8)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(l_sum)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(t_sum, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(l_date)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(t_day, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(t_month, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
											.addComponent(t_time_vklad, 0, 0, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(label)
											.addComponent(t_year, GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)))))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(l_proc)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(t_proc, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(label_2))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(ch_capital))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addContainerGap()
								.addComponent(label_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(с_type_proc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(b_go, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
							.addGap(18)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(t_ost_vklad, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(t_prok_out, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(l_sum)
							.addComponent(t_sum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_3))
						.addComponent(t_ost_vklad, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(l_date)
							.addComponent(t_day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(t_year, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(t_month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_4))
						.addComponent(t_prok_out, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(l_time_vklad)
										.addComponent(t_time_vklad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(l_proc)
										.addComponent(t_proc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_2)))
								.addComponent(label))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ch_capital)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1)
								.addComponent(с_type_proc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(b_go))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
							.addContainerGap())))
		);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setShowVerticalLines(true);
		table.setRowSelectionAllowed(false);
		contentPane.setLayout(gl_contentPane);
	}
}
