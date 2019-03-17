//ОБРАБОТКА ФЛАЖКА
ch_capital.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent arg0) {
		if (ch_capital.isSelected()) //без капитализации вывод недоступен (т.к ничего не меняет)
		{с_type_proc.setEnabled(true);}else
		{с_type_proc.setEnabled(false);}
	}
});